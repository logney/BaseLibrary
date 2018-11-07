package com.pengyu.base.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pengyu.base.R
import com.pengyu.base.comment.BaseApplication
import com.pengyu.base.comment.BaseConstants

/**
 * Created by PengYu on 2018/1/23.
 */

object GlideManager {
    private val myOptions = RequestOptions()
            .error(R.mipmap.default_head)
            .placeholder(R.mipmap.default_head)

    fun loadHeadImg(url: Any, view: ImageView) {
        myOptions.error(R.mipmap.default_head)
                .placeholder(R.mipmap.default_head)
        val urls = BaseConstants.BASE_URL_START + url.toString()
        Glide.with(BaseApplication.application)
                .load(urls)
                .apply(myOptions)
                .into(view)
    }

    fun loadBannerImg(url: Any, view: ImageView) {
        myOptions.error(R.mipmap.default_banner)
                .placeholder(R.mipmap.default_banner)
        val urls = BaseConstants.BASE_URL_START + url.toString()
        Glide.with(BaseApplication.application)
                .load(urls)
                .apply(myOptions)
                .into(view)
    }

    fun loadZFXImg(url: String, view: ImageView) {
        myOptions.error(R.mipmap.default_zfx)
                .placeholder(R.mipmap.default_zfx)
        val urls = BaseConstants.BASE_URL_START + url.toString()
        Glide.with(BaseApplication.application)
                .load(urls)
                .apply(myOptions)
                .into(view)
    }

    fun loadOtherImg(url: Any, view: ImageView) {
        myOptions.error(R.mipmap.default_zfx)
                .placeholder(R.mipmap.default_zfx)
        Glide.with(BaseApplication.application)
                .load(url)
                .apply(myOptions)
                .into(view)
    }
}
