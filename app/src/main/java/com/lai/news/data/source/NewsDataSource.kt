package com.lai.news.data.source

import android.content.Context
import com.lai.news.data.Article
import com.lai.news.data.News
import java.util.ArrayList


/**
 * Main entry point for accessing tasks data.
 *
 *
 * For simplicity, only getTasks() and getTask() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 * For example, when a new task is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */

interface NewsDataSource {

    interface GetNewsCallback {

        fun onNewsLoaded(news: ArrayList<Article>)

        fun onDataNotAvailable()

        fun noInternet()
    }

    interface GetArticleCallback {

        fun onArticleLoaded(article: Article)

        fun onDataNotAvailable()
    }

    fun getNews(context: Context, page: Int, callback: GetNewsCallback, keywords: String = "")

    fun clearNews()

    fun getArticle(articleId: String, callback: GetArticleCallback)
}