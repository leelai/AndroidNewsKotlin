package com.lai.news.newsdetail

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.lai.news.MainActivity
import com.lai.news.R
import com.lai.news.data.Article
import com.lai.news.databinding.NewsDetailFragBinding
import kotlinx.android.synthetic.main.activity_main.*

class NewsDetailFactory {
    fun getInstance(news: Article): NewsDetailFragment {
        return NewsDetailFragment().apply {
            article = news
            println("article id:" + article.source!!.id)
        }
    }
}

class NewsDetailFragment : Fragment() {

    lateinit var article: Article

    private lateinit var viewDataBinding: NewsDetailFragBinding
    private lateinit var newsDetailViewModel: NewsDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = NewsDetailFragBinding.inflate(inflater, container, false)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsDetailViewModel = (activity as MainActivity).obtainViewModel(article)
        viewDataBinding.data = newsDetailViewModel
    }

    override fun onResume() {
        super.onResume()
        activity.ivBack.visibility = VISIBLE
        newsDetailViewModel.start()
    }

    override fun onStop() {
        super.onStop()
        activity.ivBack.visibility = INVISIBLE
    }
}