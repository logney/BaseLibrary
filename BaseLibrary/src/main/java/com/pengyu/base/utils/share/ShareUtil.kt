package com.pengyu.base.utils.share

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.text.TextUtils

import com.pengyu.base.widgets.Toasty

import java.io.File
import java.io.FileOutputStream

/**
 * Created by PengYu on 2018/1/11.
 */

object ShareUtil {
    /**
     * save the Bitmap to SDCard
     *
     * @param context context
     * @param bitmap  bitmap
     * @return filePath
     */
    fun saveBitmapToSDCard(context: Context?, bitmap: Bitmap?): String? {
        if (null == context) {
            return null
        }
        if (null == bitmap) {
            Toasty.showToastError("图片保存失败")
            return null
        }
        //SDCard is valid
        if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
            Toasty.showToastError("SD卡未挂载")
            return null
        }
        var filePath: String? = null
        val externalFilesDir = context.getExternalFilesDir(null)
        var dir: String? = null
        if (null != externalFilesDir) {
            dir = externalFilesDir.absolutePath
        }
        val packageName = context.packageName
        if (!TextUtils.isEmpty(dir)) {
            if (!dir!!.endsWith(File.separator)) {
                filePath = dir + File.separator + packageName + "_share_pic.png"
            } else {
                filePath = dir + packageName + "_share_pic.png"
            }
            try {
                val file = File(filePath)
                if (file.exists()) {
                    file.delete()
                }
                file.createNewFile()

                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
            } catch (e: Exception) {
                Toasty.showToastError(e.message)
            }

        }
        return filePath
    }
}
