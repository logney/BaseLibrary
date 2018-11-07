package com.pengyu.base.utils

import android.text.TextUtils
import com.pengyu.base.comment.BaseApplication
import com.pengyu.base.module.UploadBean
import com.pengyu.base.widgets.Toasty
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import com.zhouyou.http.model.HttpParams
import java.io.File

object PublishImgTool {
    private var imgUrls: String? = null
    private var count = 0

    fun upload(file: File, size: Int, curentIndex: Int, postImgDListener: PostImgDListener) {
        PostImg(file, object : PostImgListener {
            override fun success(uploadBean: UploadBean) {
                count += 1
                imgUrls = if (TextUtils.isEmpty(imgUrls)) {
                    uploadBean.url
                } else {
                    imgUrls + "," + uploadBean.url
                }
                if (size == curentIndex + 1) {
                    postImgDListener.success(true, imgUrls!!)
                    count = 0
                    imgUrls = null
                } else {
                    postImgDListener.success(false, imgUrls!!)
                }
            }

            override fun error() {
                postImgDListener.error()
                count = 0
                imgUrls = ""
            }
        })
    }


    private fun PostImg(file: File, postImgListener: PostImgListener) {
        val httpParams = HttpParams()
        httpParams.put("upload", file, file.name, null)
        EasyHttp.post("Upload.ashx?filename=${file.name}")
                .params(httpParams)
                .accessToken(true)
                .timeStamp(true)
                .execute<String>(object : SimpleCallBack<String>() {
                    override fun onError(e: ApiException) {
                        postImgListener.error()
                    }

                    override fun onSuccess(response: String) {
                        val uploadBean = BaseApplication.gson.fromJson<UploadBean>(response, UploadBean::class.java)
                        if (uploadBean.msg == null) {
                            postImgListener.success(uploadBean)
                        } else {
                            Toasty.showToastError(uploadBean.msg)
                            postImgListener.error()
                        }
                    }
                })
    }


    interface PostImgSucListener {
        fun postAllListener(urls: String)
    }

    interface PostImgListener {
        fun success(uploadBean: UploadBean)
        fun error()
    }

    interface PostImgDListener {
        fun success(isAll: Boolean, imgUrls: String)
        fun error()
    }
}
