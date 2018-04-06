package com.lai.news.data.source

import com.github.kittinunf.fuel.Fuel
import com.lai.news.NewsUrlProvider
import com.lai.news.data.Article
import java.util.LinkedHashMap


/**
 * Concrete implementation to load tasks from the data sources into a cache.
 *
 *
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */

class ArticleRepository : ArticleDataSource {

    var cachedArticles: LinkedHashMap<String, Article> = LinkedHashMap()

    override fun getArticle(articleId: String, callback: ArticleDataSource.GetArticleCallback) {
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

    private fun getArticleWithId(id: String) = cachedArticles[id]
}