package com.pengyu.base.widgets

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.widget.TextView
import com.pengyu.base.R

/**
 * Created by PengYu on 2018/1/26.
 */

class MProDailog private constructor(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    companion object {
        private lateinit var mProDailog: MProDailog
        private lateinit var messageTV: TextView

        fun create(context: Context): MProDailog {
            mProDailog = MProDailog(context, R.style.MyDialog)
            mProDailog.setContentView(R.layout.mdailog)
            mProDailog.setCancelable(false)
            mProDailog.setCanceledOnTouchOutside(false)
            val attributes = mProDailog.window?.attributes
            attributes?.gravity = Gravity.CENTER
            attributes?.dimAmount = 0.2F
            mProDailog.window?.attributes = attributes
            messageTV = mProDailog.findViewById<TextView>(R.id.messageTV)
            return mProDailog
        }
    }

    fun showProDialog(msg: String) {
        messageTV.setText(msg)
        show()
    }



}
