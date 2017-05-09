博客地址:www.bendaidai.top
如何是你的二维码更有个性,比如微信,支付宝,他们都有切换不同样式的二维码,如何自己也实现一个呢?
二维码说白了就是上面的黑白点相当于计算机的0,1代码来储存的数据,所以我们可以改变黑色的点为其他深色的点,信息一样不会丢失,基于这些,我们就可以做一些花样的二维码出来了
废话不多说,先上图.

![](http://opgkgu3ek.bkt.clouddn.com/17-5-9/46671669-file_1494316087189_10462.gif)

为了模拟,我选了几张水印图,已经有正方形空白的背景图,还有包含背景图空白位置坐标的json.

![](http://opgkgu3ek.bkt.clouddn.com/17-5-9/76587176-file_1494317104939_e3bd.png)
- 第一个集合里面存放的是水印图片
- 第二个集合里面存放的是含有中心空白方块的背景图片
- 第三个集合里面存放的是包含背景图空白位置坐标的json
下面说说我的两种思路.
## 第一种:程序员的思路:
1. 先生成一个二维码
2. 把二维码的像素点和水印图片的像素点进行重新的颜色组合生成一张新的图片
3. 把这张图片画到背景图片的空白区域

```
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
```

## 第二种:小孩子的思路
不就是一个二维码下面放了一张图片吗?你把二维码黑色部分变成透明的颜色,让下面的背景颜色透过来不就行了吗?
1. 先生成一个二维码
2. 通过颜色矩阵来给二维码黑色的部分变成透明的
3. 把水印和二维码都放到背景图片的空白区域

   

```
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
```

### 提示
上面只是部分代码,完整demo地址,https://github.com/adonis-lsh/VariousQRcode
如果你在项目中使用,请记得开子线程.
