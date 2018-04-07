package com.lai.news.data.source

import android.content.Context
import com.github.kittinunf.fuel.Fuel
import com.lai.news.util.NewsUrlProvider
import com.lai.news.data.Article
import com.lai.news.util.Utils
import java.util.ArrayList
import java.util.LinkedHashMap


/**
 * Concrete implementation to load tasks from the data sources into a cache.
 *
 *
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */

class NewsRepository : NewsDataSource {

    var cachedNews = ArrayList<Article>()
    private var cachedArticles: LinkedHashMap<String, Article> = LinkedHashMap()

    override fun getNews(context: Context, page: Int, callback: NewsDataSource.GetNewsCallback, keywords: String) {

        if(Utils.isOnline(context)) {

            var url = NewsUrlProvider.generateUrl(page, keywords)
            Fuel.get(url).responseObject(com.lai.news.data.News.Deserializer()) { req, res, result ->
                //result is of type Result<News, Exception>
                val (news, err) = result
                //todo: deal with error

                if (news!!.articles!!.isEmpty()) {
                    println("no more data, articles is empty")
                    callback.onDataNotAvailable()
                } else {
                    news.articles!!.forEach {
                        cachedNews.add(it)
                    }
                    callback.onNewsLoaded(cachedNews)
                }
            }
        } else {
            callback.noInternet()
        }
    }

    override fun getArticle(articleId: String, callback: NewsDataSource.GetArticleCallback) {
        val taskInCache = getArticleWithId(articleId)

        // Respond immediately with cache if available
        if (taskInCache != null) {
            callback.onArticleLoaded(taskInCache)
            return
        }

        Fuel.get(NewsUrlProvider.genNewsDetailUrl(articleId)).responseObject(com.lai.news.data.News.Deserializer()) { req, res, result ->
            //result is of type Result<News, Exception>
            val (news, err) = result
            cachedArticles.put(articleId, news!!.articles!![0])
            println("status:" + news!!.status)
            println("totalResults:" + news.totalResults)

            callback.onArticleLoaded(getArticleWithId(articleId)!!)
            //todo: error handle , call onDataNotAvailable
        }
    }

    override fun clearNews() {
        cachedNews.clear()
    }

    private fun getArticleWithId(id: String) = cachedArticles[id]
}