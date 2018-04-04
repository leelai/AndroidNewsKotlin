package com.lai.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.news_detail_content_scrolling.view.*

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
}