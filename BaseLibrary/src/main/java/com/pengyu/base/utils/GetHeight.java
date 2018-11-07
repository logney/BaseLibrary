package com.pengyu.base.utils;

import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by PengYu on 2018/1/24.
 */

public class GetHeight {

    public static int height(WindowManager manager, int heightP) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        int heightC = (int) ((width / 750.0) * heightP);
        return heightC;
    }
}
