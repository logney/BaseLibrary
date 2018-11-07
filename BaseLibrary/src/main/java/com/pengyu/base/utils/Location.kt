package com.pengyu.base.utils

import android.annotation.SuppressLint
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.pengyu.base.comment.BaseApplication
import com.pengyu.base.utils.impl.LocationListener
import com.pengyu.base.widgets.Toasty


object Location {

    @SuppressLint("StaticFieldLeak")
    private val mLocationClient = AMapLocationClient(BaseApplication.application)
    private var mLocationOption: AMapLocationClientOption = AMapLocationClientOption()
    private var locationListener: LocationListener? = null

    var mAMapLocationListener = object : AMapLocationListener {
        override fun onLocationChanged(amapLocation: AMapLocation?) {
            if (amapLocation != null) {
                if (amapLocation.errorCode == 0) {
                    //解析定位结果
                    locationListener?.LocationSuccess(amapLocation)
                    mLocationClient.stopLocation()
                } else {
                    Toasty.showToast("定位失败，请检查权限是否开启！")
                }
            }
        }
    }

    /**
     * 初始化定位信息
     */
    fun init() {
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption()
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.interval = 30000
        mLocationOption.isOnceLocation = false
    }

    /**
     * 定位开始
     */
    fun LocationStart(locationListener: LocationListener) {
        this.locationListener = locationListener
        mLocationClient.setLocationListener(mAMapLocationListener)
        mLocationClient.stopLocation()
        mLocationClient.startLocation()
    }

}