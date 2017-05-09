package com.lsh.variousqrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.Hashtable;

import static com.lsh.variousqrcode.R.drawable.m;

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

    public static Bitmap addBackground(Context context) {
        Integer[] drawableList = new Integer[]{R.drawable.a, R.drawable.b,
                R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f,
                R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
                R.drawable.k, R.drawable.l, m, R.drawable.n,
                R.drawable.o, R.drawable.p, R.drawable.q, R.drawable.r,
                R.drawable.s, R.drawable.a};
        Integer[] backgroundDrawbleList = new Integer[]{R.drawable.aa, R.drawable.kk,
                R.drawable.jj, R.drawable.dd, R.drawable.ee, R.drawable.ff,
                R.drawable.gg, R.drawable.hh, R.drawable.mm, R.drawable.jj,
                R.drawable.kk, R.drawable.ll, R.drawable.mm, R.drawable.aa,
                R.drawable.ll, R.drawable.kk, R.drawable.dd, R.drawable.ee,
                R.drawable.ff, R.drawable.gg};
        String[] drawableJsons = new String[]{"aa.json", "kk.json",
                "jj.json", "dd.json", "ee.json", "ff.json",
                "gg.json", "hh.json", "mm.json", "jj.json",
                "kk.json", "ll.json", "mm.json", "aa.json",
                "ll.json", "kk.json", "dd.json", "ee.json",
                "ff.json", "gg.json"};
        int random = (int) (Math.random() * 20);
        Bitmap mwatermark = MakeQRCodeUtil.drawableToBitmap(context, drawableList[random]);
        Bitmap backgroundImage = MakeQRCodeUtil.drawableToBitmap(context, backgroundDrawbleList[random]);
        String fileName = drawableJsons[random];
        String json = Util.readAssertResource(context,fileName);
        BitmapPosition mBitmapPosition = Util.json2Bean(json, BitmapPosition.class);
        int size = mBitmapPosition.getSize();
        int x = mBitmapPosition.getX();
        int y = mBitmapPosition.getY();
        Bitmap qrCode = null;
        try {
            qrCode = makeQRImage("www.bendaidai.top", mwatermark);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        qrCode.getWidth();
        qrCode.getHeight();
        int bgWidth = backgroundImage.getWidth();
        int bgHeight = backgroundImage.getHeight();
        Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight,
                Bitmap.Config.ARGB_4444);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(newmap);
        Rect rect = new Rect();
        canvas.drawBitmap(backgroundImage, 0, 0, paint);
        rect.set(3*x, 3*y, 3*(x + size), 3*(y + size));
        canvas.drawBitmap(qrCode, null, rect, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return newmap;
    }

    public static Bitmap makeQRImage(String content, Bitmap bitBg)
            throws WriterException {
        try {
            // 图像数据转换，使用了矩阵转换
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错率
            hints.put(EncodeHintType.MARGIN, 0); // default is 4
            hints.put(EncodeHintType.MAX_SIZE, 200);
            hints.put(EncodeHintType.MIN_SIZE, 200);
            int width = bitBg.getWidth();
            int height = bitBg.getHeight();
            BitMatrix bitMatrix = new QRCodeWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                // 下面这里按照二维码的算法，逐个生成二维码的图片，//两个for循环是图片横列扫描的结果
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = bitBg.getPixel(x, y);
                    } else {
                        pixels[y * width + x] = 0x00ffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.RGB_565);

            // 设置像素点
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

}
