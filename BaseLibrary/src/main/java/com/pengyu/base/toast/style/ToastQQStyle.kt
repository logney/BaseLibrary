package com.pengyu.base.toast.style

import android.view.Gravity
import com.pengyu.base.toast.IToastStyle

class ToastQQStyle : IToastStyle {

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
        return 0
    }

    override fun getCornerRadius(): Int {
        return 4
    }

    override fun getBackgroundColor(): Int {
        return -0xcccccd
    }

    override fun getTextColor(): Int {
        return -0x1c1c1d
    }

    override fun getTextSize(): Float {
        return 12f
    }

    override fun getMaxLines(): Int {
        return 3
    }

    override fun getPaddingLeft(): Int {
        return 16
    }

    override fun getPaddingTop(): Int {
        return 14
    }

    override fun getPaddingRight(): Int {
        return getPaddingLeft()
    }

    override fun getPaddingBottom(): Int {
        return getPaddingTop()
    }
}
