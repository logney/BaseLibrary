package com.pengyu.base.widgets

import android.content.Context

/**
 * Created by PengYu on 2017/11/9.
 */

object Toasty {
    fun init(context: Context?) {
        ToastUtils.init(context)
    }

    fun showToast(mesage: String?) {
        ToastUtils.show(mesage)
    }

    fun showToastTip(mesage: String?) {
        ToastUtils.show(mesage)
    }

    fun showToastError(mesage: String?) {
        ToastUtils.show(mesage)
    }
}

