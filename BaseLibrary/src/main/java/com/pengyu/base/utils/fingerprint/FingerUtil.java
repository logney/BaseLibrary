package com.pengyu.base.utils.fingerprint;

import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

public class FingerUtil {

    private static CancellationSignal mCancellationSignal;
    private static MyAuthCallback mAuthCallback;
    private static FingerprintManagerCompat fingerprintManager;
    private static IFingerprintResultListener iFingerprintResultListenerA;

    public static void FingerprintStop() {
        if (mCancellationSignal != null && !mCancellationSignal.isCanceled()) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
        if (fingerprintManager != null) {
            fingerprintManager = null;
        }
        if (mAuthCallback != null) {
            mAuthCallback = null;
        }
    }

    public static void FingerprintRestart(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            fingerprintManager = FingerprintManagerCompat.from(context);
            mAuthCallback = new MyAuthCallback(iFingerprintResultListenerA);
            mCancellationSignal = new CancellationSignal();
            fingerprintManager.authenticate(null, 0, mCancellationSignal, mAuthCallback, null);
        }
    }

    public static void FingerprintStart(Context context, IFingerprintResultListener iFingerprintResultListener) {
        iFingerprintResultListenerA = iFingerprintResultListener;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            fingerprintManager = FingerprintManagerCompat.from(context);
            if (!fingerprintManager.isHardwareDetected()) {
                //是否支持指纹识别
                iFingerprintResultListener.isHasFingerprint(false, "不支持指纹识别");
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                //是否已注册指纹
                iFingerprintResultListener.isHasFingerprint(false, "未注册指纹");
            } else {
                iFingerprintResultListener.isHasFingerprint(true, "支持指纹识别");
                mAuthCallback = new MyAuthCallback(iFingerprintResultListener);
                mCancellationSignal = new CancellationSignal();
                fingerprintManager.authenticate(null, 0, mCancellationSignal, mAuthCallback, null);
            }
        }
    }


    /**
     * 指纹识别回调接口
     */
    public interface IFingerprintResultListener {
        /**
         * 指纹识别成功
         */
        void onAuthenticateSuccess();

        /**
         * 指纹识别失败
         */
        void onAuthenticateFailed();

        /**
         * 指纹识别发生错误-不可短暂恢复
         */
        void onAuthenticateError();

        void isHasFingerprint(boolean isHasFingerprint, String msg);
    }
}
