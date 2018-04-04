package com.lai.news

import java.net.URLEncoder
import java.util.*

class NewsUrlProvider {
    var urls = arrayOf("https://api.myjson.com/bins/155imz",
            "https://api.myjson.com/bins/186ttn",
            "https://api.myjson.com/bins/12txdn",
            "https://api.myjson.com/bins/riovv"
            )
    companion object {

        fun genNewsDetailUrl(uuid: String): String {
            return "https://api.myjson.com/bins/kezmz" //return only one news with body
        }

        fun generateUrl(keywords: String, page: Int): String {
            when (BuildConfig.DEBUG) {
                true -> return NewsUrlProvider().urls[page]
                false -> return newsApi(keywords, page)
            }
        }

        private fun newsApi(keywords: String, page: Int): String {
            var newKeywords = keywords
            if (newKeywords!!.isEmpty()) {
                newKeywords = BuildConfig.DEFAULT_KEYWORDS
            }
            newKeywords = URLEncoder.encode(newKeywords, "UTF-8")
            return BuildConfig.NEWSAPI_BASE_URL + "/v2/everything?q=" + newKeywords + "&sortBy=publishedAt&language=en&page=" + page + "&apiKey=" +  BuildConfig.NEWSAPI_API_KEY
        }
    }
}