package com.lsh.variousqrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private QRcodeView mQrcodeContent;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQrcodeContent = (QRcodeView) findViewById(R.id.iv_qrcode_content);
        mImageView = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap = MakeQRCodeUtil.makeQRImage("www.bendaidai.top", 220, 220);
        mQrcodeContent.setImageBitmap(bitmap);
    }

    public void pixelClick(View view) {
        mImageView.setImageBitmap(MakeQRCodeUtil.addBackground(this));
    }

    public void matrixClick(View view) {
        mQrcodeContent.setIsLoad(true);
        mQrcodeContent.invalidate();
    }

}
