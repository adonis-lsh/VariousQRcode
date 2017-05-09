package com.lsh.variousqrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.google.zxing.WriterException;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class MakeQRCodeUtil {
    /**
     * 生成最基本的二维码
     *
     * @param content
     * @param width
     * @param height
     * @return
     * @throws WriterException
     */
    public static Bitmap makeQRImage(String content, int width, int height) {
        return CodeUtils.createImage(content, width, height, null);
    }


    /**
     * Drawable 转 Bitmap
     */
    public static Bitmap drawableToBitmap(Context context, int drawableId) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
                drawableId);
        return bmp;
    }

    /**
     * Bitmap 转 Drawable
     */
    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }


}
