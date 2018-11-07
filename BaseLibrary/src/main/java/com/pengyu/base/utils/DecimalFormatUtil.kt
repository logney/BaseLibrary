package com.pengyu.base.utils

import java.text.DecimalFormat

/**
 * Created by PengYu on 2018/1/24.
 */
object DecimalFormatUtil {

    val df = DecimalFormat("######0.00")

    fun to2(price: Double): String {
        return df.format(price)
    }
}
