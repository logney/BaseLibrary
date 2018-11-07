package com.pengyu.base.utils

import android.text.TextUtils

/**
 * Created by PengYu on 2018/1/24.
 */

object StringUtils {
    fun StringXH(phone: String, start: Int, end: Int): String {
        val sb = StringBuilder()
        if (!TextUtils.isEmpty(phone) && phone.length >= end) {
            for (i in 0 until phone.length) {
                val c = phone[i]
                if (i in start..end) {
                    sb.append('*')
                } else {
                    sb.append(c)
                }
            }
        } else {
            return "00000000000"
        }
        return sb.toString()
    }
}
