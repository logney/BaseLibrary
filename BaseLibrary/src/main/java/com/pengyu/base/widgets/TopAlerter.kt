package com.pengyu.base.widgets

import android.app.Activity
import com.tapadoo.alerter.Alerter
import com.pengyu.base.R

object TopAlerter {
    fun showTopAlerter(activity: Activity) {
        if (Alerter.isShowing) {
            Alerter.hide()
        }
        Alerter.create(activity)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .setBackgroundColorRes(R.color.colorPrimaryDark)
                .show()
    }
}