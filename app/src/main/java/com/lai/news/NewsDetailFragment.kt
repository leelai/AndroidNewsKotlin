package com.lai.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import kotlinx.android.synthetic.main.news_detail_content_scrolling.view.*
import java.util.*

class NewsDetailFactory {
    fun getInstance(news: News): NewsDetailFragment {
        var fragment = NewsDetailFragment().apply {
            title = news.title
            description = news.description
        }
        return fragment
    }
}

class NewsDetailFragment : Fragment() {

    lateinit var title: String
    lateinit var description: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.news_detail_scrolling, container, false)
        view.tvTitle.text = title
        view.tvDescription.text = description
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getNewsDetail()
    }

    private fun getNewsDetail() {
        var url = NewsUrlProvider.genNewsDetailUrl("1234")
        Fuel.get(url).responseString { request, response, result ->
            //do something with response
            result.fold({ d ->
                //do something with data
                println("lai_test: d=" + d)
            }, { err ->
                //do something with error
                println("lai_test: err=" + err)
            })
        }

        Fuel.get(url).responseObject(News.Deserializer()) { req, res, result ->
            //result is of type Result<User, Exception>
            val (news, err) = result

            println("status:" + news!!.status)
            println("totalResults:" + news.totalResults)

        }
    }

    //News Model
    data class News (
            var status: String? = null,
            var totalResults: Int? = null,
            var articles:  List<Article>? = null ){
        class Deserializer : ResponseDeserializable<News> {
            override fun deserialize(content: String) = Gson().fromJson(content, News::class.java)
        }
    }

    //Source Model
    data class Source(
        var id: String? = null,
        var name: String? = null
    )

    //Article Model
    data class Article (
        var source: Source? = null,
        var author: String? = null,
        var title: String? = null,
        var description: String? = null,
        var url: String? = null,
        var urlToImage: String? = null,
        var publishedAt: Date? = null,
        var body: String? = null
    )
}