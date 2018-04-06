package com.lai.news.newsdetail

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.lai.news.NewsUrlProvider.Companion.genNewsDetailUrl
import com.lai.news.R
import com.squareup.picasso.Picasso

object ImageBindingAdapter {

    @BindingAdapter("android:src")
    @JvmStatic fun loadImage(view: ImageView, url:String) {
        Picasso.with(view.context).load(url)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.icon)
                .into(view)
    }

    @BindingAdapter("app:loadArticle")
    @JvmStatic fun loadArtical(view: TextView, id:String) {
        //todo: cache data
        Fuel.get(genNewsDetailUrl(id)).responseObject(com.lai.news.data.News.Deserializer()) { req, res, result ->
            //result is of type Result<News, Exception>
            val (news, err) = result

            println("status:" + news!!.status)
            println("totalResults:" + news.totalResults)
            view.text = news.articles!![0].body
        }
    }
}