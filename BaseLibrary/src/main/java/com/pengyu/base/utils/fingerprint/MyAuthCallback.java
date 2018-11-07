package com.pengyu.base.utils.fingerprint;

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

public class MyAuthCallback extends FingerprintManagerCompat.AuthenticationCallback {

    private FingerUtil.IFingerprintResultListener iFingerprintResultListener;

    MyAuthCallback(FingerUtil.IFingerprintResultListener iFingerprintResultListener) {
        super();
        this.iFingerprintResultListener = iFingerprintResultListener;
    }

    /**
     * 验证错误信息
     */
    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        super.onAuthenticationError(errMsgId, errString);
        if (iFingerprintResultListener != null) {
            iFingerprintResultListener.onAuthenticateError();
        }
    }

    /**
     * 身份验证帮助
     */
    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        super.onAuthenticationHelp(helpMsgId, helpString);
    }

    /**
     * 验证成功
     */
    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        if (iFingerprintResultListener != null) {
            iFingerprintResultListener.onAuthenticateSuccess();
        }
    }

    /**
     * 验证失败
     */
    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        if (iFingerprintResultListener != null) {
            iFingerprintResultListener.onAuthenticateFailed();
        }
    }
}
