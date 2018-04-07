package com.lai.news.newsdetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.lai.news.data.Article
import com.lai.news.data.source.NewsDataSource
import com.lai.news.data.source.NewsRepository

class NewsDetailViewModel(
        private val context: Application,
        private val newsRepository: NewsRepository,
        private val article: Article
) : AndroidViewModel(context) {

    val dataLoading = ObservableBoolean(false)
    val error = ObservableBoolean(false)
    val empty = ObservableBoolean(false)
    var title = ObservableField<String>()
    var body = ObservableField<String>()
    var time = ObservableField<String>()
    var image = ObservableField<String>()

    fun start() {
        loadArticle()
    }

    private fun loadArticle() {
        error.set(false)
        empty.set(false)
        dataLoading.set(true)

        newsRepository.getArticle(context, article.source!!.id!!, object : NewsDataSource.GetArticleCallback {
            override fun onArticleLoaded(article: Article) {
                title.set(article.title)
                body.set(article.body)
                time.set(article.getDateFormatted())
                image.set(article.urlToImage)
                dataLoading.set(false)
            }

            override fun onDataNotAvailable() {
                dataLoading.set(false)
                empty.set(true)
            }

            override fun noInternet() {
                dataLoading.set(false)
                error.set(true)
            }

            override fun onError() {
                dataLoading.set(false)
                error.set(true)
            }
        })
    }
}