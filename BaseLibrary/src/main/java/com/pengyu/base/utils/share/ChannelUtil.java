package com.pengyu.base.utils.share;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by PengYu on 2018/1/11.
 */

public class ChannelUtil {
    /**
     * 判断App是否安装
     *
     * @param context context
     * @param pkgName pkgname
     * @return boolean
     */
    public static boolean isAppInstall(Context context, String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            return false;
        }
        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo(pkgName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
    /**
     * 微博是否安装
     * @param context context
     * @return boolean
     */
    public static boolean isWeiboInstall(Context context) {
        return isAppInstall(context, "com.sina.weibo");
    }

    /**
     * @param context context
     * @return boolean
     */
    public static boolean isWeiboLiteInstall(Context context) {
        return isAppInstall(context, "com.sina.weibolite");
    }
}
