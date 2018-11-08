package com.pengyu.base.utils.fingerprint

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat

class MyAuthCallback internal constructor(private val iFingerprintResultListener: FingerUtil.IFingerprintResultListener?) :
    FingerprintManagerCompat.AuthenticationCallback() {

    /**
     * 验证错误信息
     */
    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
        super.onAuthenticationError(errMsgId, errString)
        iFingerprintResultListener?.onAuthenticateError()
    }

    /**
     * 身份验证帮助
     */
    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
        super.onAuthenticationHelp(helpMsgId, helpString)
    }

    /**
     * 验证成功
     */
    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        iFingerprintResultListener?.onAuthenticateSuccess()
    }

    /**
     * 验证失败
     */
    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        iFingerprintResultListener?.onAuthenticateFailed()
    }
}
