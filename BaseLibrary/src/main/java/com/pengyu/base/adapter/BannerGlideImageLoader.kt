package com.pengyu.base.adapter

import android.content.Context
import android.widget.ImageView

import com.youth.banner.loader.ImageLoader
import com.pengyu.base.utils.GlideManager

/**
 * Created by PengYu on 2018/1/23.
 */

class BannerGlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        GlideManager.loadOtherImg(path, imageView)
    }
}
