package com.lai.news

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.lai.news.data.Article
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*
import java.util.ArrayList

class NewsFragment : Fragment(), MyInterface {

    private var keywords:String = ""
    private var listNews = ArrayList<Article>()
    private var adapter: NewsAdapter? = null
    private var listView: ListView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_news,
                container, false)
        view!!.etSearchKeywords.also {
            it.afterTextChanged {
                if (!it.isEmpty() && it != keywords) {
                    //originListNews.clear()
                    keywords = it
                    getNews(1, true)
                } else if (it.isEmpty()) {
                    keywords = it
                    getNews(1, true)
                }
            }
        }

        listView = view!!.lvNews.also {
            it.setOnScrollListener(object:EndlessScrollListener(2, 0) {
                override fun onLoadMore(page: Int, totalItemsCount: Int): Boolean {
                    getNews(page)
                    return true
                }
            })
            adapter = NewsAdapter(this, listNews)
            it.adapter = adapter
            it.isTextFilterEnabled = true
        }

        swipeRefreshLayout = view!!.swipeRefresh.also {
            it.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener {
                override fun onRefresh() {
                    keywords = ""
                    adapter!!.filter.filter("")
                    listNews.clear()
                    getNews(1)
                }
            })
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getNews(1)
    }

    private fun getNews(page: Int, useClient: Boolean = false) {
        if (useClient || BuildConfig.DEBUG && !keywords.isEmpty()) {
            adapter!!.filter.filter(keywords)
        } else if(Utils.isOnline(context)) {
            var url = NewsUrlProvider.generateUrl(keywords, page)
            //todo: need to wrap feul
            Fuel.get(url).responseObject(com.lai.news.data.News.Deserializer()) { req, res, result ->
                //result is of type Result<News, Exception>
                val (news, err) = result
                //todo: deal with error

                news!!.articles!!.forEach {
                    listNews.add(it)
                }

                swipeRefresh.isRefreshing = false
                adapter!!.notifyDataSetChanged()
            }
        } else {
            swipeRefreshLayout!!.isRefreshing = false
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(item: Article) {
        (activity as MainActivity).run {
            var detailFragment = NewsDetailFactory().getInstance(item)
            this.addFragment(detailFragment)
        }
    }

    override fun getContext1(): Context {
        return context
    }

    override fun getFilter(): String {
        return keywords
    }
}