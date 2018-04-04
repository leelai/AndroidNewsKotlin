package com.lai.news

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by lailee on 04/03/2018.
 */
class MainActivity : AppCompatActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        var newsFragment = NewsFragment()
        fragmentTrans(newsFragment)
    }

    fun fragmentTrans(frag: Fragment){
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentHolder, frag)
                .commit()
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentHolder, fragment)
                .addToBackStack("test")
                .commit()
    }
}