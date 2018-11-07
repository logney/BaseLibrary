package com.pengyu.base.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.luck.picture.lib.entity.LocalMedia
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream

object ImageUtils {

    //把bitmap转换成String
    fun bitmapToString(filePath: String): Observable<String> {
        return Observable.create<String> { emitter ->
            val bm = BitmapFactory.decodeFile(filePath)
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 80, baos)
            val b = baos.toByteArray()
            emitter.onNext(Base64.encodeToString(b, Base64.DEFAULT))
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    //把bitmap转换成String
    fun bitmapToString(filePaths: MutableList<LocalMedia>): Observable<String> {
        return Observable.create<String> { emitter ->
            for (i in 0 until filePaths.size) {
                val bm = BitmapFactory.decodeFile(filePaths[i].compressPath)
                val baos = ByteArrayOutputStream()
                bm.compress(Bitmap.CompressFormat.JPEG, 40, baos)
                val b = baos.toByteArray()
                emitter.onNext(Base64.encodeToString(b, Base64.DEFAULT))
                if (i == filePaths.size - 1) {
                    emitter.onComplete()
                }
//                PostBase64(Base64.encodeToString(b, Base64.DEFAULT), emitter, filePaths.size)
            }
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}