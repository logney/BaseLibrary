package com.pengyu.base.utils.share

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.text.TextUtils

import com.pengyu.base.R
import com.pengyu.base.widgets.Toasty

/**
 * Created by PengYu on 2018/1/11.
 */

class ShareByWeibo(private var context: Context?) {

    private var datas: ShareEntity? = null
    private var shareWeiBoListener: ShareWeiBoListener? = null

    fun share(context: Context, data: ShareEntity, shareWeiBoListener: ShareWeiBoListener) {
        this.datas = data
        this.context = context
        this.shareWeiBoListener = shareWeiBoListener
        val isWeiboInstalled = ChannelUtil.isWeiboInstall(context)
        var isWeiboLiteInstalled = false
        if (!isWeiboInstalled) {
            isWeiboLiteInstalled = ChannelUtil.isWeiboLiteInstall(context)
        }
        if (!isWeiboInstalled && !isWeiboLiteInstalled) {
            Toasty.showToast("分享失败，请先安装微博客户端")
            return
        }
        if (!TextUtils.isEmpty(data.imgUrl)) {
            if (data.imgUrl!!.startsWith("http")) {
                BitmapAsyncTask(data.imgUrl!!, object : BitmapAsyncTask.OnBitmapListener {
                    override fun onSuccess(bitmap: Bitmap) {
                        localSyncTask(bitmap)
                    }

                    override fun onException(exception: Exception) {
                        localSyncTask(null)
                    }
                }).execute()
            } else {
                localSyncTask()
            }
        } else if (data.drawableId != 0) {
            var drawable: BitmapDrawable? = null
            try {
                drawable = ContextCompat.getDrawable(context, data.drawableId) as BitmapDrawable?
            } catch (ignored: Exception) {
            }

            if (null != drawable) {
                localSyncTask(drawable.bitmap)
            } else {
                localSyncTask(null)
            }
        } else {
            localSyncTask(null)
        }

    }

    @SuppressLint("StaticFieldLeak")
    private fun localSyncTask(bitmap: Bitmap?) {
        object : AbstractAsyncTask<String>() {
            @Throws(Exception::class)
            override fun doLoadData(): String {
                val imgPath: String
                if (null != bitmap) {
                    imgPath = ShareUtil.saveBitmapToSDCard(context, bitmap)!!
                } else {
                    imgPath = ShareUtil.saveBitmapToSDCard(context, getDefaultBitmap(context))!!
                }
                weiboShare(imgPath)
                return imgPath
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    private fun localSyncTask() {
        if (!TextUtils.isEmpty(datas!!.imgUrl) && !datas!!.imgUrl!!.startsWith("http")) {
            object : AbstractAsyncTask<String>() {
                @Throws(Exception::class)
                override fun doLoadData(): String {
                    weiboShare(datas!!.imgUrl!!)
                    return datas!!.imgUrl!!
                }
            }.execute()
        } else {
            localSyncTask(null)
        }
    }

    private fun weiboShare(imgPath: String) {
        val weiboIntent = Intent(Intent.ACTION_SEND)
        weiboIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (!TextUtils.isEmpty(imgPath)) {
            weiboIntent.type = "image/*"
        } else {
            weiboIntent.type = "text/plain"
        }
        try {
            var pkgName = ""
            val pm = context!!.packageManager
            val matches = pm.queryIntentActivities(weiboIntent, PackageManager.MATCH_DEFAULT_ONLY)
            var info: ResolveInfo? = null
            for (each in matches) {
                pkgName = each.activityInfo.applicationInfo.packageName
                if ("com.sina.weibo" == pkgName || "com.sina.weibolite" == pkgName) {
                    info = each
                    break
                }
            }

            if (null != info) {
                weiboIntent.setClassName(pkgName, info.activityInfo.name)

                val builder = StringBuilder()
                if (!TextUtils.isEmpty(datas!!.content)) {
                    builder.append(datas!!.content)
                }
                if (!TextUtils.isEmpty(datas!!.url)) {
                    builder.append(" ").append(datas!!.url)
                }
                if (!TextUtils.isEmpty(builder.toString())) {
                    weiboIntent.putExtra(Intent.EXTRA_TEXT, builder.toString())
                }

                if (!TextUtils.isEmpty(imgPath)) {
                    weiboIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imgPath))
                }
                context!!.startActivity(weiboIntent)
                shareWeiBoListener!!.success()
            } else {
                shareWeiBoListener!!.faild()
            }
        } catch (ignore: Exception) {
            shareWeiBoListener!!.faild()
        }

    }

    private fun getDefaultBitmap(context: Context?): Bitmap {
        var icon: Drawable? = null
        try {
            icon = context!!.packageManager.getApplicationIcon(context.packageName)
        } catch (ignored: Exception) {
        }

        return if (icon is BitmapDrawable) {
            icon.bitmap
        } else BitmapFactory.decodeResource(context!!.resources, R.mipmap.regist_logo)

    }
}
