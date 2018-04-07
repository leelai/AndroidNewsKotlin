package com.lai.news.news

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.lai.news.*
import com.lai.news.data.Article
import com.lai.news.databinding.NewsFragBinding
import com.lai.news.newsdetail.NewsDetailFactory
import kotlinx.android.synthetic.main.news_frag.view.*

class NewsFragment : Fragment(), MyInterface {

    private lateinit var viewDataBinding: NewsFragBinding

    private var keywords:String = ""
    private var adapter: NewsAdapter? = null
    private var listView: ListView? = null
    private var editTextWithClear: EditTextWithClear? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewDataBinding = NewsFragBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as MainActivity).obtainNewsViewModel()
        }

        var view = viewDataBinding.root

        editTextWithClear = view.etSearchKeywords.apply {
            afterTextChanged {
                if (it.isEmpty() ||
                        (!it.isEmpty() && it != keywords)) {
                    //originListNews.clear()
                    keywords = it
                    getNews(1)
                }
            }
        }

        listView = view.lvNews.also {
            it.setOnScrollListener(object: EndlessScrollListener(2, 0) {
                override fun onLoadMore(page: Int, totalItemsCount: Int): Boolean {
                    println("onLoadMore:" + page + ", total items count:" + totalItemsCount)
                    getNews(page)
                    return true
                }
            })
            adapter = NewsAdapter(this)
            it.adapter = adapter
            it.isTextFilterEnabled = true
        }

        view.swipeRefresh!!.setOnRefreshListener {
            adapter = NewsAdapter(this)
            listView!!.adapter = adapter
            editTextWithClear!!.setText("", TextView.BufferType.EDITABLE)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getNews(1)
    }

    private fun getNews(page: Int) {
        if (!viewDataBinding.etSearchKeywords.text.isEmpty()) {
            adapter!!.filter.filter(keywords)
        } else {
            viewDataBinding.viewmodel?.loadNews(page)
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