package com.kami.drawview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private Handler mhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.layout_root);
        int width = root.getMeasuredWidth();
        int height = root.getMeasuredHeight();
        final TextView helloText = findViewById(R.id.text_hello);

        final View rootView = LayoutInflater.from(this).inflate(R.layout.view_draw,null);
        //保存在本地 产品还没决定要不要保存在本地
        new Thread(new Runnable() {
            @Override
            public void run() {

                int width = rootView.getWidth();
                int height = rootView.getWidth();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bmp);
                rootView.layout(0, 0, width, height);
                rootView.draw(canvas);
                FileOutputStream fos;
                try {
                    // 判断手机设备是否有SD卡
                    boolean isHasSDCard = Environment.getExternalStorageState().equals(
                            android.os.Environment.MEDIA_MOUNTED);
                    if (isHasSDCard) {
                        // SD卡根目录
                        File sdRoot = Environment.getExternalStorageDirectory();
                        Log.e("ssh",sdRoot.toString());
                        File file = new File(sdRoot, "myPic.png");
                        fos = new FileOutputStream(file);
                    } else
                        throw new Exception("创建文件失败!");
                    //压缩图片 30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
                    bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

                    fos.flush();
                    fos.close();

                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            helloText.setText("绘制图片完成");
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
