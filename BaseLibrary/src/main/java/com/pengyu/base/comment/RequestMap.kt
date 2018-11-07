package com.pengyu.base.comment

import com.zhouyou.http.model.HttpParams

object RequestMap {

    private var map = HttpParams()
    var isLogin = false
    var USERID = ""

    /**
     *  @param phoneAccount 手机号
     *  @param phoneVerification 验证码或密码
     *  @param loginType 0手机号验证码登录 1手机号密码登录 2微信登录 3QQ登录
     * *
     *  @return
     */
    fun login(phoneAccount: String, phoneVerification: String, loginType: Int): HttpParams {
        map.clear()
        map.put("loginType", loginType.toString())
        map.put("phoneAccount", phoneAccount)
        map.put("phoneVerification", phoneVerification)
        map.put("password", phoneVerification)
        return map
    }

}