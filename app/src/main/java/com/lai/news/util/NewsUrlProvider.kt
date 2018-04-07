package com.lai.news.util

import com.lai.news.BuildConfig
import java.net.URLEncoder
import java.util.*

class NewsUrlProvider {

    var urls = arrayOf(
//            "https://api.myjson.com/bins/dl0p7",
//            "https://api.myjson.com/bins/1f3bt7",
//            "https://api.myjson.com/bins/1axakr",
            "https://api.myjson.com/bins/j02az",
            "https://api.myjson.com/bins/8vp0r",
            "https://api.myjson.com/bins/riovv"
            )

    var detailUrls = arrayOf(
            "https://api.myjson.com/bins/xy9az",
            "https://api.myjson.com/bins/kezmz",
            "https://api.myjson.com/bins/61117",
            "https://api.myjson.com/bins/6mgmz",
            "https://api.myjson.com/bins/v18fv",
            "https://api.myjson.com/bins/jpzy3"
    )

    companion object {

        fun genNewsDetailUrl(uuid: String): String {
            var i = (0..NewsUrlProvider().detailUrls.size).random()
            return NewsUrlProvider().detailUrls[i] //return only one news with body
        }

        fun generateUrl(page: Int, keywords: String): String {
            println("generateUrl :" + page)
            if (BuildConfig.USE_NEWSAPI) {
                return newsApi(keywords, page)
            } else {
                when (page >= NewsUrlProvider().urls.size) {
                    true -> return NewsUrlProvider().urls.last()
                    false -> return NewsUrlProvider().urls[page - 1]
                }
            }
        }

        private fun newsApi(keywords: String, page: Int): String {
            var newKeywords = keywords
            if (newKeywords!!.isEmpty()) {
                newKeywords = BuildConfig.DEFAULT_KEYWORDS
            }
            newKeywords = URLEncoder.encode(newKeywords, "UTF-8")
            return BuildConfig.NEWSAPI_BASE_URL + "/v2/everything?q=" + newKeywords + "&sortBy=publishedAt&language=en&page=" + page + "&apiKey=" + BuildConfig.NEWSAPI_API_KEY
        }

        fun ClosedRange<Int>.random() =
                Random().nextInt(endInclusive - start) +  start
    }
}