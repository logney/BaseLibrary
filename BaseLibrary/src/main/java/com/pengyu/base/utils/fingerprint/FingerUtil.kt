package com.pengyu.base.utils.fingerprint

import android.content.Context
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal

object FingerUtil {

    private var mCancellationSignal: CancellationSignal? = null
    private var mAuthCallback: MyAuthCallback? = null
    private var fingerprintManager: FingerprintManagerCompat? = null
    private var iFingerprintResultListenerA: IFingerprintResultListener? = null

    fun FingerprintStop() {
        if (mCancellationSignal != null && !mCancellationSignal!!.isCanceled) {
            mCancellationSignal!!.cancel()
            mCancellationSignal = null
        }
        if (fingerprintManager != null) {
            fingerprintManager = null
        }
        if (mAuthCallback != null) {
            mAuthCallback = null
        }
    }

    fun FingerprintRestart(context: Context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            fingerprintManager = FingerprintManagerCompat.from(context)
            mAuthCallback = MyAuthCallback(iFingerprintResultListenerA)
            mCancellationSignal = CancellationSignal()
            fingerprintManager!!.authenticate(null, 0, mCancellationSignal, mAuthCallback!!, null)
        }
    }

    fun FingerprintStart(context: Context, iFingerprintResultListener: IFingerprintResultListener) {
        iFingerprintResultListenerA = iFingerprintResultListener
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            fingerprintManager = FingerprintManagerCompat.from(context)
            if (!fingerprintManager!!.isHardwareDetected) {
                //是否支持指纹识别
                iFingerprintResultListener.isHasFingerprint(false, "不支持指纹识别")
            } else if (!fingerprintManager!!.hasEnrolledFingerprints()) {
                //是否已注册指纹
                iFingerprintResultListener.isHasFingerprint(false, "未注册指纹")
            } else {
                iFingerprintResultListener.isHasFingerprint(true, "支持指纹识别")
                mAuthCallback = MyAuthCallback(iFingerprintResultListener)
                mCancellationSignal = CancellationSignal()
                fingerprintManager!!.authenticate(null, 0, mCancellationSignal, mAuthCallback!!, null)
            }
        }
    }


    /**
     * 指纹识别回调接口
     */
    interface IFingerprintResultListener {
        /**
         * 指纹识别成功
         */
        fun onAuthenticateSuccess()

        /**
         * 指纹识别失败
         */
        fun onAuthenticateFailed()

        /**
         * 指纹识别发生错误-不可短暂恢复
         */
        fun onAuthenticateError()

        fun isHasFingerprint(isHasFingerprint: Boolean, msg: String)
    }
}
