package com.lai.news

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.lai.news.data.Article
import com.lai.news.data.source.ArticleRepository
import com.lai.news.newsdetail.NewsDetailViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by lailee on 04/03/2018.
 */
class MainActivity : AppCompatActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var mArticleRepository: ArticleRepository = ArticleRepository()
    private lateinit var newsDetailViewModel: NewsDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivBack.setOnClickListener({ _ -> onBackPressed() })
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        var newsFragment = NewsFragment()
        fragmentTrans(newsFragment)
    }

    fun fragmentTrans(frag: Fragment){
        supportFragmentManager.beginTransaction()
                .add(R.id.contentFrame, frag)
                .commit()
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(R.id.contentFrame, fragment)
                .addToBackStack("test")
                .commit()
    }

    fun obtainViewModel(article: Article): NewsDetailViewModel {
        return NewsDetailViewModel(this.application, mArticleRepository, article)
    }
    //todo: dependency of mock
//    fun obtainViewModel(): NewsDetailViewModel = obtainViewModel(NewsDetailViewModel::class.java)
    //todo: need to implement first page viewmodel
}