package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    private Context baseContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        baseContext = getApplicationContext();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    LocalBroadcastManager manager = LocalBroadcastManager.getInstance(baseContext);
                    Log.i("New Thread", "send Msg");
                    Intent intent = new Intent("testAction");
                    intent.putExtra("key", 1);
                    manager.sendBroadcast(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 1000);

    }
}
