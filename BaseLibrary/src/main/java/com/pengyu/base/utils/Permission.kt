package com.pengyu.base.utils

import android.support.v7.app.AppCompatActivity
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.pengyu.base.widgets.Toasty

object Permission {

    val REQUEST_INSTALL_PACKAGES = "android.permission.REQUEST_INSTALL_PACKAGES" // 8.0及以上应用安装权限

    val SYSTEM_ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW" // 6.0及以上悬浮窗权限

    val READ_CALENDAR = "android.permission.READ_CALENDAR" // 读取日程提醒
    val WRITE_CALENDAR = "android.permission.WRITE_CALENDAR" // 写入日程提醒

    val CAMERA = "android.permission.CAMERA" // 拍照权限

    val READ_CONTACTS = "android.permission.READ_CONTACTS" // 读取联系人
    val WRITE_CONTACTS = "android.permission.WRITE_CONTACTS" // 写入联系人
    val GET_ACCOUNTS = "android.permission.GET_ACCOUNTS" // 访问账户列表

    val ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION" // 获取精确位置
    val ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION" // 获取粗略位置

    val RECORD_AUDIO = "android.permission.RECORD_AUDIO" // 录音权限

    val READ_PHONE_STATE = "android.permission.READ_PHONE_STATE" // 读取电话状态
    val CALL_PHONE = "android.permission.CALL_PHONE" // 拨打电话
    val READ_CALL_LOG = "android.permission.READ_CALL_LOG" // 读取通话记录
    val WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG" // 写入通话记录
    val ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL" // 添加语音邮件
    val USE_SIP = "android.permission.USE_SIP" // 使用SIP视频
    val PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS" // 处理拨出电话

    val BODY_SENSORS = "android.permission.BODY_SENSORS" // 传感器

    val SEND_SMS = "android.permission.SEND_SMS" // 发送短信
    val RECEIVE_SMS = "android.permission.RECEIVE_SMS" // 接收短信
    val READ_SMS = "android.permission.READ_SMS" // 读取短信
    val RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH" // 接收WAP PUSH信息
    val RECEIVE_MMS = "android.permission.RECEIVE_MMS" // 接收彩信
    val USE_FINGERPRINT = "android.permission.USE_FINGERPRINT" // 指纹

    val READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE" // 读取外部存储
    val WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE" // 写入外部存储

    object Group {

        // 日历
        val CALENDAR = arrayOf(Permission.READ_CALENDAR, Permission.WRITE_CALENDAR)

        // 联系人
        val CONTACTS = arrayOf(Permission.READ_CONTACTS, Permission.WRITE_CONTACTS, Permission.GET_ACCOUNTS)

        // 位置
        val LOCATION = arrayOf(Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION)

        // 存储
        val STORAGE = arrayOf(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)

        val ALL = arrayOf(Permission.READ_EXTERNAL_STORAGE
                , Permission.WRITE_EXTERNAL_STORAGE
                , Permission.READ_PHONE_STATE
                , Permission.SYSTEM_ALERT_WINDOW
                , Permission.ACCESS_COARSE_LOCATION
                , Permission.ACCESS_FINE_LOCATION
                , Permission.USE_FINGERPRINT)
    }


    /**
     * type : 0=位置、1=存储、2=相机、3联系人、4=支持请求6.0悬浮窗权限8.0请求安装权限
     */
    fun QXRequest(activity: AppCompatActivity, type: Int, requestPermi: RequestPermi) {
        when (type) {
            0 -> {
                LOCATION(activity, requestPermi)
            }
            1 -> {
                STORAGE(activity, requestPermi)
            }
            2 -> {
                CAMERA(activity, requestPermi)
            }
            3 -> {
                CONTACTS(activity, requestPermi)
            }
            4 -> {
                PER6AND8(activity, requestPermi)
            }
            5 -> {
                allPermission(activity, requestPermi)
            }
        }
    }

    private fun allPermission(activity: AppCompatActivity, requestPermi: RequestPermi) {
        XXPermissions.with(activity)
                .permission(Permission.Group.ALL) //不指定权限则自动获取清单中的危险权限
                .request(object : OnPermission {
                    override fun noPermission(denied: MutableList<String>?, quick: Boolean) {
                        if (quick) {
                            Toasty.showToast("被永久拒绝授权，请手动授予权限")
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(activity);
                        } else {
                            Toasty.showToast("获取权限失败")
                        }
                    }

                    override fun hasPermission(granted: MutableList<String>?, isAll: Boolean) {
                        if (isAll) {
                            requestPermi.successRequest()
                        } else {
                            Toasty.showToast("获取权限成功，部分权限未正常授予")
                        }
                    }

                })
    }

    fun USE_FINGERPRINT(activity: AppCompatActivity, requestPermi: RequestPermi) {
        XXPermissions.with(activity)
                .permission(Permission.USE_FINGERPRINT) //不指定权限则自动获取清单中的危险权限
                .request(object : OnPermission {
                    override fun noPermission(denied: MutableList<String>?, quick: Boolean) {
                        if (quick) {
                            Toasty.showToast("被永久拒绝授权，请手动授予权限")
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(activity);
                        } else {
                            Toasty.showToast("获取权限失败")
                        }
                    }

                    override fun hasPermission(granted: MutableList<String>?, isAll: Boolean) {
                        if (isAll) {
                            requestPermi.successRequest()
                        } else {
                            Toasty.showToast("获取权限成功，部分权限未正常授予")
                        }
                    }

                })
    }

    private fun STORAGE(activity: AppCompatActivity, requestPermi: RequestPermi) {
        XXPermissions.with(activity)
                .permission(Permission.Group.STORAGE) //不指定权限则自动获取清单中的危险权限
                .request(object : OnPermission {
                    override fun noPermission(denied: MutableList<String>?, quick: Boolean) {
                        if (quick) {
                            Toasty.showToast("被永久拒绝授权，请手动授予权限")
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(activity);
                        } else {
                            Toasty.showToast("获取权限失败")
                        }
                        activity.finish()
                    }

                    override fun hasPermission(granted: MutableList<String>?, isAll: Boolean) {
                        if (isAll) {
                            requestPermi.successRequest()
                        } else {
                            Toasty.showToast("获取权限成功，部分权限未正常授予")
                        }
                    }

                })
    }

    private fun CONTACTS(activity: AppCompatActivity, requestPermi: RequestPermi) {
        XXPermissions.with(activity)
                .permission(Permission.Group.CONTACTS) //不指定权限则自动获取清单中的危险权限
                .request(object : OnPermission {
                    override fun noPermission(denied: MutableList<String>?, quick: Boolean) {
                        if (quick) {
                            Toasty.showToast("被永久拒绝授权，请手动授予权限")
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(activity);
                        } else {
                            Toasty.showToast("获取权限失败")
                        }
                        activity.finish()
                    }

                    override fun hasPermission(granted: MutableList<String>?, isAll: Boolean) {
                        if (isAll) {
                            requestPermi.successRequest()
                        } else {
                            Toasty.showToast("获取权限成功，部分权限未正常授予")
                        }
                    }

                })
    }


    private fun LOCATION(activity: AppCompatActivity, requestPermi: RequestPermi) {
        XXPermissions.with(activity)
                .permission(Permission.Group.LOCATION) //不指定权限则自动获取清单中的危险权限
                .request(object : OnPermission {
                    override fun noPermission(denied: MutableList<String>?, quick: Boolean) {
                        if (quick) {
                            Toasty.showToast("被永久拒绝授权，请手动授予权限")
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(activity);
                        } else {
                            Toasty.showToast("获取权限失败")
                        }
                    }

                    override fun hasPermission(granted: MutableList<String>?, isAll: Boolean) {
                        if (isAll) {
                            requestPermi.successRequest()
                        } else {
                            Toasty.showToast("获取权限成功，部分权限未正常授予")
                        }
                    }

                })
    }

    private fun CAMERA(activity: AppCompatActivity, requestPermi: RequestPermi) {

        XXPermissions.with(activity)
                .permission(Permission.CAMERA)
                .request(object : OnPermission {
                    override fun noPermission(denied: MutableList<String>?, quick: Boolean) {
                        if (quick) {
                            Toasty.showToast("被永久拒绝授权，请手动授予权限")
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(activity);
                        } else {
                            Toasty.showToast("获取权限失败")
                        }
                    }

                    override fun hasPermission(granted: MutableList<String>?, isAll: Boolean) {
                        if (isAll) {
                            requestPermi.successRequest()
                        } else {
                            Toasty.showToast("获取权限成功，部分权限未正常授予")
                        }
                    }

                })
    }

    private fun PER6AND8(activity: AppCompatActivity, requestPermi: RequestPermi) {
        XXPermissions.with(activity)
                .permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                .request(object : OnPermission {
                    override fun noPermission(denied: MutableList<String>?, quick: Boolean) {
                        if (quick) {
                            Toasty.showToast("被永久拒绝授权，请手动授予权限")
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(activity);
                        } else {
                            Toasty.showToast("获取权限失败")
                        }
                        activity.finish()
                    }

                    override fun hasPermission(granted: MutableList<String>?, isAll: Boolean) {
                        if (isAll) {
                            requestPermi.successRequest()
                        } else {
                            Toasty.showToast("获取权限成功，部分权限未正常授予")
                        }
                    }

                })
    }

    interface RequestPermi {
        fun successRequest()
    }

}
