package com.pengyu.base.utils.share

import android.content.Context
import android.content.pm.PackageManager
import android.text.TextUtils

/**
 * Created by PengYu on 2018/1/11.
 */

object ChannelUtil {
    /**
     * 判断App是否安装
     *
     * @param context context
     * @param pkgName pkgname
     * @return boolean
     */
    fun isAppInstall(context: Context, pkgName: String): Boolean {
        if (TextUtils.isEmpty(pkgName)) {
            return false
        }
        try {
            val pm = context.packageManager
            pm.getPackageInfo(pkgName, 0)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * 微博是否安装
     * @param context context
     * @return boolean
     */
    fun isWeiboInstall(context: Context): Boolean {
        return isAppInstall(context, "com.sina.weibo")
    }

    /**
     * @param context context
     * @return boolean
     */
    fun isWeiboLiteInstall(context: Context): Boolean {
        return isAppInstall(context, "com.sina.weibolite")
    }
}
