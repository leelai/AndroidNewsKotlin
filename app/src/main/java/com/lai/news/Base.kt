package com.lai.news

import android.content.Context
import com.lai.news.data.Article

interface MyInterface{
    fun getContext1(): Context
    fun getFilter(): String
    fun onClick(item: Article)
}

interface MyInterface1<T : MyInterface1<T>> {
    fun getFragment(): T
}

interface MyInterface2<T : MyInterface2<T>>{
    fun getContext2(): Context
    fun onClick(item: T)
}