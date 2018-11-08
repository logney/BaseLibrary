package com.pengyu.base.utils.share

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.DrawableRes

/**
 * Created by zhanglifeng
 */
class ShareEntity : Parcelable {

    var title: String? = null
    var content: String? = null
    var url: String? = null

    var imgUrl: String? = null
    var drawableId: Int = 0
    var isShareBigImg: Boolean = false

    @JvmOverloads
    constructor(title: String, content: String, url: String? = null, imgUrl: String? = null) {
        this.title = title
        this.content = content
        this.url = url
        this.imgUrl = imgUrl
    }

    override fun describeContents(): Int {
        return 0
    }

    protected constructor(`in`: Parcel) {
        title = `in`.readString()
        content = `in`.readString()
        url = `in`.readString()
        imgUrl = `in`.readString()
        drawableId = `in`.readInt()
        isShareBigImg = `in`.readByte().toInt() != 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(content)
        dest.writeString(url)
        dest.writeString(imgUrl)
        dest.writeInt(drawableId)
        dest.writeByte((if (isShareBigImg) 1 else 0).toByte())
    }

    companion object {

        val CREATOR: Parcelable.Creator<ShareEntity> = object : Parcelable.Creator<ShareEntity> {
            override fun createFromParcel(`in`: Parcel): ShareEntity {
                return ShareEntity(`in`)
            }

            override fun newArray(size: Int): Array<ShareEntity?> {
                return arrayOfNulls(size)
            }
        }
    }

    object CREATOR : Parcelable.Creator<ShareEntity> {
        override fun createFromParcel(parcel: Parcel): ShareEntity {
            return ShareEntity(parcel)
        }

        override fun newArray(size: Int): Array<ShareEntity?> {
            return arrayOfNulls(size)
        }
    }
}

