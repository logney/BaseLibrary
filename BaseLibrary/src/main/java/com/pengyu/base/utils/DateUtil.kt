package com.pengyu.base.utils

import java.util.*

/**
 * Created by PengYu on 2018/3/27.
 */
object DateUtil {

    fun getTime(d: Date): String {
        val delta = (Date().time - d.time) / 1000
        if (delta <= 0) return "刚刚"
        if (delta / (60 * 60 * 24 * 365) > 0) return (delta / (60 * 60 * 24 * 365)).toString() + "年前"
        if (delta / (60 * 60 * 24 * 30) > 0) return (delta / (60 * 60 * 24 * 30)).toString() + "个月前"
        if (delta / (60 * 60 * 24 * 7) > 0) return (delta / (60 * 60 * 24 * 7)).toString() + "周前"
        if (delta / (60 * 60 * 24) > 0) return (delta / (60 * 60 * 24)).toString() + "天前"
        if (delta / (60 * 60) > 0) return (delta / (60 * 60)).toString() + "小时前"
        return if (delta / 60 > 0) (delta / 60).toString() + "分钟前" else "刚刚"
    }
}