package com.example.wanglingxiang.weather_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wanglingxiang on 2019/4/24.
 */

public class ZhuyeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuyetiaozhuang);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(ZhuyeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        ).start();
    }
}
