package com.lai.news

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by lailee on 04/03/2018.
 *
 * https://github.com/google-developer-training/android-advanced/blob/master/CustomEditText/app/src/main/java/com/example/android/customedittext/EditTextWithClear.java
 * https://github.com/angitha-das/ClearableEdittext
 */
class EditTextWithClear : AppCompatEditText {

    private lateinit var mClearButtonImage: Drawable
    private lateinit var mSearchButtonImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setPadding(30,20, 30,20)
    }

    private fun init() {

        background = ResourcesCompat.getDrawable(resources,
                R.drawable.search_bg, null)

        mClearButtonImage = ResourcesCompat.getDrawable(resources,
                R.drawable.ic_clear_opaque_24dp, null)!!

        mClearButtonImage!!.setBounds(0, 0,
                mClearButtonImage!!.intrinsicWidth, mClearButtonImage!!.intrinsicHeight)

        mSearchButtonImage = ResourcesCompat.getDrawable(resources,
                R.drawable.ic_search, null)!!

        setCompoundDrawablesRelativeWithIntrinsicBounds(mSearchButtonImage, null, mClearButtonImage, null)

        addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s?.isEmpty()!!){
                    setCompoundDrawablesRelativeWithIntrinsicBounds(mSearchButtonImage, null, null, null)
                }else{
                    mClearButtonImage = ResourcesCompat.getDrawable(resources,
                            R.drawable.ic_clear_black_24dp, null)!!
                    setCompoundDrawablesRelativeWithIntrinsicBounds(mSearchButtonImage, null, mClearButtonImage, null)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        compoundDrawables[2]?.run {
            if (event!!.x > width - paddingRight - mClearButtonImage!!.intrinsicWidth) {
                text = null
            }
        }

        return super.onTouchEvent(event)
    }

    fun afterTextChanged(afterTextChanged: (String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}