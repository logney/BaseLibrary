package com.pengyu.pylib

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pengyu.base.widgets.Toasty

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toasty.init(this)
        Toasty.showToast("Test Library !")
    }
}
