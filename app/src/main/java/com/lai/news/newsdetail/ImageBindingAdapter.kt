package com.lai.news.newsdetail

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.lai.news.R
import com.squareup.picasso.Picasso

object ImageBindingAdapter {

    @BindingAdapter("android:src")
    @JvmStatic fun loadImage(view: ImageView, url:String?) {
        if (url == null) {
            return
        } else {
            Picasso.with(view.context).load(url)
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.icon)
                    .into(view)
        }
    }
}