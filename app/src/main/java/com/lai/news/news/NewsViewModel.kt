package com.lai.news.news

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import com.lai.news.data.Article
import com.lai.news.data.source.NewsDataSource
import com.lai.news.data.source.NewsRepository
import java.util.ArrayList

class NewsViewModel(
        private val context: Application,
        private val newsRepository: NewsRepository
) : AndroidViewModel(context) {

    val dataLoading = ObservableBoolean(false)
    val error = ObservableBoolean(false)
    val empty = ObservableBoolean(false)
    val items: ObservableList<Article> = ObservableArrayList()

    fun loadNews(page: Int) {
        error.set(false)
        empty.set(false)
        dataLoading.set(true)
        if (page == 1) newsRepository.clearNews()

        newsRepository.getNews(context, page, object : NewsDataSource.GetNewsCallback {

            override fun onNewsLoaded(news: ArrayList<Article>) {
                with(items) {
                    clear()
                    addAll(news)
                    empty.set(isEmpty())
                }
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
        } , "" )
    }
}