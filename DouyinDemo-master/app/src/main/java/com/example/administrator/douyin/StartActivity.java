package com.example.administrator.douyin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class StartActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void init() {
        setFullScreen();

        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(StartActivity.this, Login.class));
                finish();
            }
        }.start();
    }
}