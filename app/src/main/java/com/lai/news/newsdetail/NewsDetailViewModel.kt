package com.lai.news.newsdetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.lai.news.data.Article
import com.lai.news.data.source.ArticleDataSource
import com.lai.news.data.source.ArticleRepository

class NewsDetailViewModel(
        private val context: Application,
        private val articleRepository: ArticleRepository,
        private val article: Article
) : AndroidViewModel(context) {

    val dataLoading = ObservableBoolean(false)
    val error = ObservableBoolean(false)
    var title = ObservableField<String>()
    var body = ObservableField<String>()
    var time = ObservableField<String>()
    var image = ObservableField<String>()

    fun start() {
        loadArticle()
    }

    private fun loadArticle() {
        dataLoading.set(true)

        articleRepository.getArticle(article.source!!.id!!, object : ArticleDataSource.GetArticleCallback {
            override fun onArticleLoaded(article: Article) {
                error.set(false)
                title.set(article.title)
                body.set(article.body)
                time.set(article.getDateFormatted())
                image.set(article.urlToImage)
                dataLoading.set(false)
            }

            override fun onDataNotAvailable() {
                error.set(true)
                dataLoading.set(false)
            }
        })
    }
}