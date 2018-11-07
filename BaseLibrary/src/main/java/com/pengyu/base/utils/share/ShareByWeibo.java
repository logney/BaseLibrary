package com.pengyu.base.utils.share;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.pengyu.base.R;
import com.pengyu.base.widgets.Toasty;

import java.util.List;

/**
 * Created by PengYu on 2018/1/11.
 */

public class ShareByWeibo {

    private ShareEntity data;
    private Context context;
    private ShareWeiBoListener shareWeiBoListener;

    public ShareByWeibo(Context context) {
        this.context = context;
    }

    public void share(Context context, ShareEntity data, ShareWeiBoListener shareWeiBoListener) {
        this.data = data;
        this.context = context;
        this.shareWeiBoListener = shareWeiBoListener;
        boolean isWeiboInstalled = ChannelUtil.isWeiboInstall(context);
        boolean isWeiboLiteInstalled = false;
        if (!isWeiboInstalled) {
            isWeiboLiteInstalled = ChannelUtil.isWeiboLiteInstall(context);
        }
        if (!isWeiboInstalled && !isWeiboLiteInstalled) {
            Toasty.INSTANCE.showToast("分享失败，请先安装微博客户端");
            return;
        }
        if (!TextUtils.isEmpty(data.getImgUrl())) {
            if (data.getImgUrl().startsWith("http")) {
                new BitmapAsyncTask(data.getImgUrl(), new BitmapAsyncTask.OnBitmapListener() {
                    @Override
                    public void onSuccess(final Bitmap bitmap) {
                        localSyncTask(bitmap);
                    }

                    @Override
                    public void onException(Exception exception) {
                        localSyncTask(null);
                    }
                }).execute();
            } else {
                localSyncTask();
            }
        } else if (data.getDrawableId() != 0) {
            BitmapDrawable drawable = null;
            try {
                drawable = (BitmapDrawable) ContextCompat.getDrawable(context, data.getDrawableId());
            } catch (Exception ignored) {
            }
            if (null != drawable) {
                localSyncTask(drawable.getBitmap());
            } else {
                localSyncTask(null);
            }
        } else {
            localSyncTask(null);
        }

    }

    @SuppressLint("StaticFieldLeak")
    private void localSyncTask(final Bitmap bitmap) {
        new AbstractAsyncTask<String>() {
            @Override
            protected String doLoadData() throws Exception {
                String imgPath;
                if (null != bitmap) {
                    imgPath = ShareUtil.saveBitmapToSDCard(context, bitmap);
                } else {
                    imgPath = ShareUtil.saveBitmapToSDCard(context, getDefaultBitmap(context));
                }
                weiboShare(imgPath);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void localSyncTask() {
        if (!TextUtils.isEmpty(data.getImgUrl()) && !data.getImgUrl().startsWith("http")) {
            new AbstractAsyncTask<String>() {
                @Override
                protected String doLoadData() throws Exception {
                    weiboShare(data.getImgUrl());
                    return null;
                }
            }.execute();
        } else {
            localSyncTask(null);
        }
    }

    private void weiboShare(final String imgPath) {
        Intent weiboIntent = new Intent(Intent.ACTION_SEND);
        weiboIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!TextUtils.isEmpty(imgPath)) {
            weiboIntent.setType("image/*");
        } else {
            weiboIntent.setType("text/plain");
        }
        try {
            String pkgName = "";
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> matches = pm.queryIntentActivities(weiboIntent, PackageManager.MATCH_DEFAULT_ONLY);
            ResolveInfo info = null;
            for (ResolveInfo each : matches) {
                pkgName = each.activityInfo.applicationInfo.packageName;
                if ("com.sina.weibo".equals(pkgName) || "com.sina.weibolite".equals(pkgName)) {
                    info = each;
                    break;
                }
            }

            if (null != info) {
                weiboIntent.setClassName(pkgName, info.activityInfo.name);

                StringBuilder builder = new StringBuilder();
                if (!TextUtils.isEmpty(data.getContent())) {
                    builder.append(data.getContent());
                }
                if (!TextUtils.isEmpty(data.getUrl())) {
                    builder.append(" ").append(data.getUrl());
                }
                if (!TextUtils.isEmpty(builder.toString())) {
                    weiboIntent.putExtra(Intent.EXTRA_TEXT, builder.toString());
                }

                if (!TextUtils.isEmpty(imgPath)) {
                    weiboIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imgPath));
                }
                context.startActivity(weiboIntent);
                shareWeiBoListener.success();
            } else {
                shareWeiBoListener.faild();
            }
        } catch (Exception ignore) {
            shareWeiBoListener.faild();
        }
    }

    private Bitmap getDefaultBitmap(Context context) {
        Drawable icon = null;
        try {
            icon = context.getPackageManager().getApplicationIcon(context.getPackageName());
        } catch (Exception ignored) {
        }
        if (icon instanceof BitmapDrawable) {
            return ((BitmapDrawable) icon).getBitmap();
        }

        return BitmapFactory.decodeResource(context.getResources(), R.mipmap.regist_logo);
    }
}
