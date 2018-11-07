@file:Suppress("DEPRECATION")

package com.pengyu.base.utils

import android.annotation.SuppressLint
import android.app.FragmentManager
import android.util.Log
import com.allen.library.SuperTextView
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.util.*

@SuppressLint("StaticFieldLeak")
object SelectTimeUtils : DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var End = 0                         //0、1是否设置的是结束时间 2只选择设置时分
    private var isOnce = true                   //是否第一次开启dialog
    private var dpd: DatePickerDialog? = null   //dialog变量
    private var YEAR = 0
    private var MONTH = 0
    private var DAY_OF_MONTH = 0
    private var isright = true

    private lateinit var fragmentManager: FragmentManager
    private var startTime: SuperTextView? = null
    private var endTime: SuperTextView? = null
    private var stvTime: SuperTextView? = null
    private var setDateListener: SetDateListener? = null

    fun init(fragmentManager: FragmentManager, startTime: SuperTextView, endTime: SuperTextView) {
        this.fragmentManager = fragmentManager
        this.startTime = startTime
        this.endTime = endTime
    }

    fun init(fragmentManager: FragmentManager, startTime: SuperTextView, endTime: SuperTextView, isright: Boolean) {
        this.fragmentManager = fragmentManager
        this.startTime = startTime
        this.endTime = endTime
        this.isright = isright
    }

    fun init(fragmentManager: FragmentManager, stvTime: SuperTextView) {
        this.fragmentManager = fragmentManager
        this.stvTime = stvTime
    }

    fun init(fragmentManager: FragmentManager, startTime: SuperTextView, endTime: SuperTextView, setDateListener: SetDateListener) {
        this.fragmentManager = fragmentManager
        this.startTime = startTime
        this.endTime = endTime
        this.setDateListener = setDateListener
    }

    //显示时间dialog
    fun showDate(End: Int) {
        this.End = End
        if (isOnce) {
            val now = Calendar.getInstance()
            YEAR = now.get(Calendar.YEAR)
            MONTH = now.get(Calendar.MONTH)
            DAY_OF_MONTH = now.get(Calendar.DAY_OF_MONTH)
            isOnce = false
        }

        if (dpd == null) {
            dpd = DatePickerDialog.newInstance(
                    this,
                    YEAR,
                    MONTH,
                    DAY_OF_MONTH
            )
        } else {
            dpd!!.initialize(
                    this,
                    YEAR,
                    MONTH,
                    DAY_OF_MONTH
            )
        }
        if (End == 2) {

        } else {
            val days = arrayOfNulls<Calendar>(360 * 2)
            for (i in 0 until 360 * 2) {
                val day = Calendar.getInstance()
                day.add(Calendar.DAY_OF_MONTH, i)
                days[i] = day
            }
            dpd!!.selectableDays = days
        }
        dpd!!.version = DatePickerDialog.Version.VERSION_2
        dpd!!.show(fragmentManager, "")
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        YEAR = year
        MONTH = monthOfYear
        DAY_OF_MONTH = dayOfMonth
        if (End == 2) {
            val month = MONTH + 1
            val monthStr: String = if (month < 10) {
                "0$month"
            } else {
                month.toString()
            }
            stvTime?.setLeftString("$YEAR.$monthStr.$DAY_OF_MONTH")
        } else {
            showTime(fragmentManager)
        }
    }

    private var tpd: TimePickerDialog? = null
    private fun showTime(fragmentManager: FragmentManager) {
        val now = Calendar.getInstance()
        val currentDay = now.get(Calendar.HOUR_OF_DAY)
        val currentMinute = now.get(Calendar.MINUTE)
        tpd = TimePickerDialog.newInstance(
                this, currentDay, currentMinute,
                true
        )
        tpd!!.setOnCancelListener { Log.d("TimePicker", "Dialog was cancelled") }
        tpd!!.show(fragmentManager, "Timepickerdialog")
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        val hourString = if (hourOfDay < 10) "0$hourOfDay" else "" + hourOfDay
        val minuteString = if (minute < 10) "0$minute" else "" + minute
        val month = MONTH + 1
        val monthStr: String
        monthStr = if (month < 10) {
            "0$month"
        } else {
            month.toString()
        }
        if (End == 0) {
            if (isright) {
                startTime?.setRightString("$YEAR-$monthStr-$DAY_OF_MONTH $hourString:$minuteString")
            } else {
                startTime?.setLeftString("$YEAR-$monthStr-$DAY_OF_MONTH $hourString:$minuteString")
            }
            setDateListener?.setDate()
        } else if (End == 1) {
            if (isright) {
                endTime?.setRightString("$YEAR-$monthStr-$DAY_OF_MONTH $hourString:$minuteString")
            } else {
                endTime?.setLeftString("$YEAR-$monthStr-$DAY_OF_MONTH $hourString:$minuteString")
            }
            setDateListener?.setDate()
        }
    }

    interface SetDateListener {
        fun setDate()
    }
}