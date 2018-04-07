package com.lai.news

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.lai.news.data.Article
import com.lai.news.data.source.NewsRepository
import com.lai.news.news.NewsFragment
import com.lai.news.news.NewsViewModel
import com.lai.news.newsdetail.NewsDetailViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by lailee on 04/03/2018.
 */
class MainActivity : AppCompatActivity() {

    private var mNewsRepository: NewsRepository = NewsRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivBack.setOnClickListener({ _ -> onBackPressed() })

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

    fun obtainNewsViewModel(): NewsViewModel {
        return NewsViewModel(this.application, mNewsRepository)
    }

    fun obtainViewModel(article: Article): NewsDetailViewModel {
        return NewsDetailViewModel(this.application, mNewsRepository, article)
    }
}