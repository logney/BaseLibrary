package com.pengyu.base.utils

/**
 * Created by PengYu on 2018/3/1.
 */

import android.os.CountDownTimer
import android.widget.TextView
import com.allen.library.SuperTextView

class TimeCount : CountDownTimer {

    private var stateButton: SuperTextView? = null
    private var textView: TextView? = null

    constructor(stateButton: SuperTextView, millisInFuture: Long, countDownInterval: Long) : super(millisInFuture, countDownInterval) {
        this.stateButton = stateButton
    }// 参数依次为总时长,和计时的时间间隔

    constructor(textView: TextView, millisInFuture: Long, countDownInterval: Long) : super(millisInFuture, countDownInterval) {
        this.textView = textView
    }// 参数依次为总时长,和计时的时间间隔

    override fun onTick(millisUntilFinished: Long) {
        if (textView != null) {
            textView!!.isEnabled = false
            textView!!.text = (millisUntilFinished / 1000).toString() + "秒后重新获取"
            return
        }
        stateButton!!.isEnabled = false
        stateButton!!.setCenterString((millisUntilFinished / 1000).toString() + "秒后重新获取")
    }

    override fun onFinish() {
        if (textView != null) {
            textView!!.text = "重新获取"
            textView!!.isEnabled = true
            return
        }
        stateButton!!.setCenterString("重新获取")
        stateButton!!.isEnabled = true
    }

}

