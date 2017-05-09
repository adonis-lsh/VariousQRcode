package com.lsh.variousqrcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * Created by "小灰灰"
 * on 8/5/2017 15:20
 * 邮箱：www.adonis_lsh.com
 */

public class QRcodeView extends android.support.v7.widget.AppCompatImageView {

    private Bitmap backgroundImage;
    private Paint mPaint = new Paint();
    private Bitmap bitmap;// 位图
    private BitmapPosition mBitmapPosition;
    private int mBackDrawable;
    private Bitmap mwatermark;
    private double mScale;
    private Context context;
    private boolean isLoad;

    public QRcodeView(Context context) {
        this(context, null);
    }

    public QRcodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QRcodeView);
        isLoad = typedArray.getBoolean(R.styleable.QRcodeView_isLoadData, true);
        typedArray.recycle();
    }

    public QRcodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (isLoad) {
            initData();
            isLoad = false;
        }
        drawBitmap(canvas);
    }

    private void initData() {
        Integer[] drawableList = new Integer[]{R.drawable.a, R.drawable.b,
                R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f,
                R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
                R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n,
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
        mwatermark = MakeQRCodeUtil.drawableToBitmap(context, drawableList[random]);
        mBackDrawable = backgroundDrawbleList[random];
        backgroundImage = MakeQRCodeUtil.drawableToBitmap(context, mBackDrawable);
        String fileName = drawableJsons[random];
        String json = Util.readAssertResource(context, fileName);
        mBitmapPosition = Util.json2Bean(json, BitmapPosition.class);
        mPaint.setAntiAlias(true);
    }

    private void drawBitmap(Canvas canvas) {
        //我在这边拿到二维码
        Drawable qrCodeImage = getDrawable();
        BitmapDrawable bd = (BitmapDrawable) qrCodeImage;
        //获取位图
        bitmap = bd.getBitmap();
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                1, 1, 1, 0, 0});
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        Rect rect = new Rect();

        int background = dip2px(context, 200);
        mScale = background / 1000.0;
        rect.set((int) (mBitmapPosition.getX() * mScale), (int) (mBitmapPosition.getY() * mScale), (int) (
                        (mBitmapPosition.getX() + mBitmapPosition.getSize()) * mScale),
                (int) ((mBitmapPosition.getY() + mBitmapPosition.getSize()) * mScale));
        Paint paint = new Paint();
        canvas.drawBitmap(mwatermark, null, rect, paint);
        canvas.drawBitmap(bitmap, null, rect, mPaint);
        canvas.save();
        canvas.restore();
        setBackgroundResource(mBackDrawable);
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setIsLoad(boolean isLoad) {
        this.isLoad = isLoad;
    }

    public static int dip2px(Context con, float dpValue) {
        final float scale = con.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}