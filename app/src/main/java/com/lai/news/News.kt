package com.lai.news

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by lailee on 04/03/2018.
 */
class News {
    var source:String? = null
    var author:String? = null
    var title:String = ""
    var description:String = ""
    var URL:String? = null
    var imageURL:String? = null
    var publishedAt:Date? = null
    var viewType:String? = null
    constructor(source:String, author:String, title:String, description:String, URL:String, imageURL:String, publishedAt:Date, viewType:String) {
        this.source = source
        this.author = author
        this.title = title
        this.description = description
        this.URL = URL
        this.imageURL = imageURL
        this.publishedAt = publishedAt
        this.viewType = viewType
    }
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null
    @SerializedName("articles")
    @Expose
    var articles: List<Article>? = null
}

class Article {
    @SerializedName("source")
    @Expose
    var source: Source? = null
    @SerializedName("author")
    @Expose
    var author: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String? = null
    @SerializedName("publishedAt")
    @Expose
    var publishedAt: Date? = null
}

class Source {
    @SerializedName("id")
    @Expose
    var id: String? = "12345"
    @SerializedName("name")
    @Expose
    var name: String? = null
}