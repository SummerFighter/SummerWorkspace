package com.example.administrator.douyin;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

public class Setting extends AppCompatActivity {


    private ImageView bktpersoninfo;
    private Button unlogin ;
    private Button exitapp;
    private Button aboutus;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        unlogin = findViewById(R.id.unlogin);
        unlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        bktpersoninfo = findViewById(R.id.bktpersoninfo);
        bktpersoninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToPersoninfo();
            }
        });
        aboutus = findViewById(R.id.aboutus);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToAboutus();

            }
        });
        exitapp = findViewById(R.id.exitapp);
        exitapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExitPressed();

            }
        });
    }

    private void ToAboutus() {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
    }


    //确认退出弹框
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出登录吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       unLogin();
                    }
                })
                .setNegativeButton("容我想想", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“容我想想”后的操作,这里不设置没有任何操作

                    }
                }).show();
    }

        //退出APP
    private void onExitPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出小麦视频吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitApp();


                    }
                })
                .setNegativeButton("我再看看", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“我再看看”后的操作,这里不设置没有任何操作

                    }
                }).show();
    }


    private void unLogin() {


        Intent intent = new Intent(this, Login.class);
        //消除其他所有Activity，返回登录界面；
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);



    }


    private void exitApp() {


        Intent intent = new Intent(this, Login.class);
        //消除其他所有Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //杀掉所有进程，实现退出
        android.os.Process.killProcess(android.os.Process.myPid());

    }

    //返回
    private void ToPersoninfo() {
        Intent intent = new Intent(this, PersonInfo.class);
        startActivity(intent);

    }
}
