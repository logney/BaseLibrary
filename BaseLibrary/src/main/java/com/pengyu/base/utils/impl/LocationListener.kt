package com.pengyu.base.utils.impl

import com.amap.api.location.AMapLocation

interface LocationListener {
    fun LocationSuccess(amapLocation: AMapLocation)
}