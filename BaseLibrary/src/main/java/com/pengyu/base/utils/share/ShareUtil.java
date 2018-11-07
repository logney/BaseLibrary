package com.pengyu.base.utils.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import com.pengyu.base.widgets.Toasty;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by PengYu on 2018/1/11.
 */

public class ShareUtil {
    /**
     * save the Bitmap to SDCard
     *
     * @param context context
     * @param bitmap  bitmap
     * @return filePath
     */
    public static String saveBitmapToSDCard(Context context, Bitmap bitmap) {
        if (null == context) {
            return null;
        }
        if (null == bitmap) {
            Toasty.INSTANCE.showToastError("图片保存失败");
            return null;
        }
        //SDCard is valid
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toasty.INSTANCE.showToastError("SD卡未挂载");
            return null;
        }
        String filePath = null;
        File externalFilesDir = context.getExternalFilesDir(null);
        String dir = null;
        if (null != externalFilesDir) {
            dir = externalFilesDir.getAbsolutePath();
        }
        String packageName = context.getPackageName();
        if (!TextUtils.isEmpty(dir)) {
            if (!dir.endsWith(File.separator)) {
                filePath = dir + File.separator + packageName + "_share_pic.png";
            } else {
                filePath = dir + packageName + "_share_pic.png";
            }
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();

                FileOutputStream outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                Toasty.INSTANCE.showToastError(e.getMessage());
            }
        }
        return filePath;
    }
}
