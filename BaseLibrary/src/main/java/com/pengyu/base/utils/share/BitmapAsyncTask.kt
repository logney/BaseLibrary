package com.pengyu.base.utils.share

import android.graphics.Bitmap
import android.graphics.BitmapFactory

import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL

/**
 * Created by zhanglifeng on 1/6/17
 */
class BitmapAsyncTask(private val urlStr: String, private val listener: OnBitmapListener?) :
    AbstractAsyncTask<Bitmap>() {

    @Throws(Exception::class)
    override fun doLoadData(): Bitmap {

        val url = URL(urlStr)
        val `is` = url.openStream()
        // 将InputStream变为Bitmap
        val bitmap = getSampleBitmap(`is`, 640, 640)
        `is`.close()

        return bitmap!!
    }

    private fun getSampleBitmap(`is`: InputStream, width: Int, height: Int): Bitmap? {

        val stream = BufferedInputStream(`is`)
        stream.mark(4 * 1024)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(stream, null, options)
        calculateInSampleSize(width, height, options, true)
        try {
            stream.reset()
        } catch (e: IOException) {
        }

        return BitmapFactory.decodeStream(stream, null, options)
    }

    override fun onSuccess(bitmap: Bitmap) {
        super.onSuccess(bitmap)
        listener?.onSuccess(bitmap)
    }

    override fun onException(exception: Exception) {
        super.onException(exception)
        listener?.onException(exception)
    }

    override fun onFinally() {
        super.onFinally()
    }


    interface OnBitmapListener {

        fun onSuccess(bitmap: Bitmap)

        fun onException(exception: Exception)
    }

    companion object {

        internal fun calculateInSampleSize(
            reqWidth: Int,
            reqHeight: Int,
            options: BitmapFactory.Options,
            centerInside: Boolean
        ) {
            calculateInSampleSize(
                reqWidth, reqHeight, options.outWidth, options.outHeight, options,
                centerInside
            )
        }

        internal fun calculateInSampleSize(
            reqWidth: Int, reqHeight: Int, width: Int, height: Int,
            options: BitmapFactory.Options, centerInside: Boolean
        ) {
            var sampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val heightRatio: Int
                val widthRatio: Int
                if (reqHeight == 0) {
                    sampleSize = Math.floor((width.toFloat() / reqWidth.toFloat()).toDouble()).toInt()
                } else if (reqWidth == 0) {
                    sampleSize = Math.floor((height.toFloat() / reqHeight.toFloat()).toDouble()).toInt()
                } else {
                    heightRatio = Math.floor((height.toFloat() / reqHeight.toFloat()).toDouble()).toInt()
                    widthRatio = Math.floor((width.toFloat() / reqWidth.toFloat()).toDouble()).toInt()
                    sampleSize = if (centerInside)
                        Math.max(heightRatio, widthRatio)
                    else
                        Math.min(heightRatio, widthRatio)
                }
            }
            options.inSampleSize = sampleSize
            options.inJustDecodeBounds = false
        }
    }

}

