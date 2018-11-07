package com.pengyu.base.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.pengyu.base.R
import java.text.SimpleDateFormat
import java.util.*

object MyDatePick {
    fun GetYearMoth(activity: Activity, mOnTimeSelectListener: OnTimeSelectListener) {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        val listBoolean = booleanArrayOf(true, true, false, false, false, false)

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013, 0, 1)
        endDate.set(2020, 11, 31)
        //时间选择器
        TimePickerBuilder(activity, mOnTimeSelectListener).setType(listBoolean).setRangDate(startDate, endDate)
                .isCyclic(true)
                .setTitleText("请选择日期")
                .setTitleColor(Color.WHITE)//标题文字颜色
                .setSubmitColor(ContextCompat.getColor(activity, R.color.labe3))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(activity, R.color.labe3))//取消按钮文字颜色
                .setTitleBgColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark))
                .isDialog(false)
                .build().show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(date: Date): String {//可根据需要自行截取数据显示
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(date)
    }
}