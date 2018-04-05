package com.lai.news

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.github.kittinunf.fuel.Fuel
import com.lai.news.data.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.news_detail.view.*
import java.text.SimpleDateFormat
import java.util.*

class NewsDetailFactory {
    fun getInstance(news: Article): NewsDetailFragment {
        var fragment = NewsDetailFragment().apply {
            id = "1234"
            title = news.title
            imageUrl = news.urlToImage
            publishedAt = news.publishedAt
        }
        return fragment
    }
}

class NewsDetailFragment : Fragment() {

    val sdf = SimpleDateFormat("MMMM dd, HH:mm" , Locale.getDefault())
    lateinit var id: String
    lateinit var title: String
    var publishedAt: Date? = null
    var imageUrl: String? = null
    lateinit var fragmentLayout: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("NewsDetailFragment->onCreateView")
        var view = inflater!!.inflate(R.layout.news_detail, container, false)
        view.tvTitle.text = title
        view.tvDescription.text = getText(R.string.loading)
        view.tvUpdatedTime.text = String.format(context!!.getString(R.string.updated_time), sdf.format(publishedAt))
        Picasso.with(context).load(imageUrl)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.icon)
                .into(view!!.appCompatImageView)
        fragmentLayout = view
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getNewsDetail()
    }

    private fun getNewsDetail() {
        var url = NewsUrlProvider.genNewsDetailUrl(id)
        Fuel.get(url).responseObject(com.lai.news.data.News.Deserializer()) { req, res, result ->
            //result is of type Result<News, Exception>
            val (news, err) = result

            println("status:" + news!!.status)
            println("totalResults:" + news.totalResults)

            //view!!.tvTitle.text = news!!.articles!![0].title
            view!!.tvDescription.text = news.articles!![0].body
        }
    }

    override fun onResume() {
        super.onResume()
        activity.ivBack.visibility = VISIBLE
    }

    override fun onStop() {
        super.onStop()
        activity.ivBack.visibility = INVISIBLE
    }
}