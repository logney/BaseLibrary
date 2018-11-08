package com.pengyu.base.toast

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.pengyu.base.R

class XToast(context: Context) : Toast(context), Runnable {

    private val mHandler = Handler(Looper.getMainLooper()) // 吐司处理消息线程

    private var mTextView: TextView? = null // 吐司消息View
    private var mContent: CharSequence? = null // 吐司显示的文本

    override fun setView(view: View) {
        super.setView(view)
        mTextView = view.findViewById(R.id.toast_main_text_view_id)
        if (mTextView != null) {
            return
        } else if (view is TextView) {
            mTextView = view
            return
        } else if (view is ViewGroup) {
            this.mTextView = getTextView(view)
            if (this.mTextView != null) return
        }
        // 如果设置的布局没有包含一个TextView则抛出异常，必须要包含一个TextView作为Message对象
        throw IllegalArgumentException("The layout must contain a TextView")
    }

    override fun setText(s: CharSequence) {
        // 记录本次吐司欲显示的文本
        mContent = s
    }

    override fun show() {
        // 移除之前显示吐司的任务
        mHandler.removeCallbacks(this)
        super.cancel()
        // 添加一个显示吐司的任务
        mHandler.postDelayed(this, 300)
    }

    /**
     * [Runnable]
     */
    override fun run() {
        // 设置吐司文本
        mTextView!!.text = mContent
        // 显示吐司
        super.show()
    }

    override fun cancel() {
        // 移除之前显示吐司的任务
        mHandler.removeCallbacks(this)
        // 取消显示
        super.cancel()
    }

    /**
     * 递归获取ViewGroup中的TextView对象
     */
    private fun getTextView(group: ViewGroup): TextView? {
        for (i in 0 until group.childCount) {
            val view = group.getChildAt(i)
            if (view is TextView) {
                return view
            } else if (view is ViewGroup) {
                val textView = getTextView(view)
                if (textView != null) return textView
            }
        }
        return null
    }
}
