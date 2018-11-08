package com.pengyu.base.toast.style


import android.view.Gravity
import com.pengyu.base.toast.IToastStyle


/**
 * author : HJQ
 * github : https://github.com/getActivity/ToastUtils
 * time   : 2018/09/01
 * desc   : 默认黑色样式实现
 */
open class ToastBlackStyle : IToastStyle {

    override fun getGravity(): Int {
        return Gravity.CENTER
    }

    override fun getXOffset(): Int {
        return 0
    }

    override fun getYOffset(): Int {
        return 0
    }

    override fun getZ(): Int {
        return 30
    }

    override fun getCornerRadius(): Int {
        return 6
    }

    override fun getBackgroundColor(): Int {
        return -0x78000000
    }

    override fun getTextColor(): Int {
        return -0x11000001
    }

    override fun getTextSize(): Float {
        return 14f
    }

    override fun getMaxLines(): Int {
        return 3
    }

    override fun getPaddingLeft(): Int {
        return 24
    }

    override fun getPaddingTop(): Int {
        return 16
    }

    override fun getPaddingRight(): Int {
        return getPaddingLeft()
    }

    override fun getPaddingBottom(): Int {
        return getPaddingTop()
    }
}
