package com.lai.news.data

import android.databinding.BaseObservable
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

//News Model
data class News (
        var status: String? = null,
        var totalResults: Int? = null,
        var articles:  List<Article>? = null ){
    class Deserializer : ResponseDeserializable<News> {
        override fun deserialize(content: String) = Gson().fromJson(content, News::class.java)
    }
}

//Source Model
data class Source(
        var id: String? = null,
        var name: String? = null
)

//Article Model
data class Article (
        var source: Source? = null,
        var author: String? = null,
        var title: String = "",
        var description: String = "",
        var url: String = "",
        var urlToImage: String = "",
        var publishedAt: Date? = null,
        var body: String = ""
) : BaseObservable(){
    fun getDateFormatted(): String{
        val sdf = SimpleDateFormat("MMMM dd, HH:mm" , Locale.getDefault())
        return String.format("Updated: %s", sdf.format(publishedAt))
    }
}