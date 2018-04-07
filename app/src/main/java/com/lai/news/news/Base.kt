package com.lai.news.news

import android.content.Context
import com.lai.news.data.Article

interface MyInterface{
    fun getContext1(): Context
    fun getFilter(): String
    fun onClick(item: Article)
}