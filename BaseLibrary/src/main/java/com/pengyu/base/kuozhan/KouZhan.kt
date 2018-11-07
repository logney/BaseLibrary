package com.pengyu.base.kuozhan

import android.support.v4.view.ViewPager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.pengyu.base.kouzhanimp.EditValueListener
import com.pengyu.base.kouzhanimp.OnClickListener
import com.pengyu.base.kouzhanimp.PageChangeListener

fun EditText.setValueListener(mValueListener: EditValueListener) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(mEditable: Editable?) {
            var value = mEditable.toString()
            if (mEditable != null) {
                mValueListener.afterText(value)
            } else {
                mValueListener.afterText("")
            }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}

fun ViewPager.setPageChangeListener(mPageChangeListener: PageChangeListener) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            mPageChangeListener.pageChange(position)
        }

    })
}

fun View.setCustomOnClickListener(mOnClickListener: OnClickListener) {
    this.setOnClickListener {
        if (isFastClick()) {
            mOnClickListener.onClick()
        }
    }
}


// 两次点击按钮之间的点击间隔不能少于1000毫秒
private val MIN_CLICK_DELAY_TIME = 1000
private var lastClickTime: Long = 0

fun isFastClick(): Boolean {
    var flag = false
    val curClickTime = System.currentTimeMillis()
    if (curClickTime - lastClickTime >= MIN_CLICK_DELAY_TIME) {
        flag = true
    }
    lastClickTime = curClickTime
    return flag
}