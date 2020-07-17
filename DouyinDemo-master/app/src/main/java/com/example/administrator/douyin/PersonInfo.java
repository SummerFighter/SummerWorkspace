package com.example.administrator.douyin;
import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import Controller.Constant;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fragment.TabFragment;
import widget.FullViewPager;
import widget.ScaleScrollView;
import widget.TitleLayout;

/*
* 个人信息类
* */

public class PersonInfo extends AppCompatActivity  {

    private AppCompatTextView accountTextView;
    private AppCompatTextView usernameTextView;
    private AppCompatImageView avatarImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo);

        accountTextView = findViewById(R.id.userId);
        usernameTextView = findViewById(R.id.username);
        avatarImageView = findViewById(R.id.avatar);

        accountTextView.setText(Constant.currentUser.getAccount());
        usernameTextView.setText(Constant.currentUser.getUsername());
        Drawable image = Drawable.createFromPath(Constant.currentUser.getAvatarPath());
        avatarImageView.setBackground(image);

    }

    // 请求编辑个人信息
    public void OnClick_EditButton(View v)
    {
        Intent intent=new Intent();
        intent.setClass(PersonInfo.this, EditMyinfo.class);
        startActivity(intent);
    }
}