package com.pengyu.base.comment

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.google.gson.GsonBuilder
import com.gzsm.activityloading.LoadingAndRetryManager
import com.pengyu.base.R
import com.tencent.mmkv.MMKV
import com.pengyu.base.utils.Location
import com.pengyu.base.utils.gsontool.DoubleDefault0Adapter
import com.pengyu.base.utils.gsontool.IntegerDefault0Adapter
import com.pengyu.base.utils.gsontool.LongDefault0Adapter
import com.pengyu.base.widgets.Toasty
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.cookie.CookieManger
import com.zhouyou.http.model.HttpParams

/**
 * Created by PengYu on 2018/1/23.
 */
open abstract class BaseApplication : Application() {


    companion object {
        val gson = GsonBuilder()
            .registerTypeAdapter(Int::class.java, IntegerDefault0Adapter())
            .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerDefault0Adapter())
            .registerTypeAdapter(Double::class.java, DoubleDefault0Adapter())
            .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleDefault0Adapter())
            .registerTypeAdapter(Long::class.java, LongDefault0Adapter())
            .registerTypeAdapter(Long::class.javaPrimitiveType, LongDefault0Adapter())
            .create()
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty
        Toasty.init(this)
        Utils.init(this)
        MMKV.initialize(this);
        Location.init()
    }

    fun initHttp(baseUrl: String, tag: String = "MyAppLog--->", isDeBug: Boolean = false) {
        EasyHttp.init(this)
        //全局设置请求参数
        val params = HttpParams()
        params.put("OS", "ANDROID")
        EasyHttp.getInstance()
            .debug(tag, isDeBug)
            .setBaseUrl(baseUrl)
            .setCookieStore(CookieManger(this))
            .setRetryIncreaseDelay(500)//每次延时叠加500ms
            .addCommonParams(params)//设置全局公共参数
            .setCertificates()
    }

}