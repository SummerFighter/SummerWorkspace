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
import android.widget.ImageView;
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

import Controller.DataCreate;
import fragment.TabFragment;
import model.TabItemModel;
import widget.CircleImageView;
import widget.FullViewPager;
import widget.ScaleScrollView;
import widget.TitleLayout;

/*
* 个人信息类
* */

public class PersonInfo extends AppCompatActivity implements ScaleScrollView.OnScrollChangeListener  {

    private AppCompatTextView accountTextView;
    private AppCompatTextView usernameTextView;
    private AppCompatImageView avatarImageView;
    private TabLayout tab1, tab2;
    private TitleLayout titleLayout;
    private int colorPrimary;
    private ArgbEvaluator evaluator;
    private View statusView;
    private Button editinfo;
    private TextView sy;
    private TextView at;
    private ImageView hd;
    CircleImageView ivHead;
    private VideoBean.UserBean curUserBean;
    private int getStatusBarHeight() {
        int height = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = getResources().getDimensionPixelSize(resId);
        }
        return height;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        new DataCreate().initData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo);
        colorPrimary = ContextCompat.getColor(this, R.color.colorPrimary);

        sy = findViewById(R.id.sy);
        sy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asdasd();
            }
        });

        editinfo = findViewById(R.id.editinfo);
        editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInfo();
            }
        });

        at=findViewById((R.id.follow));
        at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attention();
            }
        });
        initView();
        accountTextView = findViewById(R.id.userId);
        usernameTextView = findViewById(R.id.username);
        avatarImageView = findViewById(R.id.iv_head);

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

    private void EditInfo() {
        Intent intent = new Intent(this, EditMyinfo.class);
        startActivity(intent);
    }
    public void asdasd() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void attention() {
        Intent intent = new Intent(this, FocusActivity.class);
        startActivity(intent);
    }


    private void initView() {
        //设置状态栏和导航栏
        statusView = findViewById(R.id.statusView);
        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        statusView.setLayoutParams(lp);
        statusView.setBackgroundColor(Color.TRANSPARENT);
        statusView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setNavigationBarColor(colorPrimary);

        AppCompatImageView banner = findViewById(R.id.banner);
        ScaleScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.setTargetView(banner);
        scrollView.setOnScrollChangeListener(this);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        titleLayout = findViewById(R.id.title_layout);
        FullViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new adapter.TabAdapter(this, getSupportFragmentManager(), getTabs()));
        tab1.setupWithViewPager(viewPager);
        tab2.setupWithViewPager(viewPager);
    }

    private List<TabItemModel> getTabs() {
        List<model.TabItemModel> tabs = new ArrayList<>();
        tabs.add(new model.TabItemModel("作品10", TabFragment.class.getName(), null));
        tabs.add(new model.TabItemModel("动态10", TabFragment.class.getName(), null));
        tabs.add(new model.TabItemModel("喜欢999", TabFragment.class.getName(), null));
        return tabs;
    }

    @Override
    public void onScrollChange(NestedScrollView v, int x, int y, int ox, int oy) {
        if (null != tab1 && null != tab2 && null != titleLayout && null != statusView) {
            int distance = tab1.getTop() - titleLayout.getHeight() - statusView.getHeight();
            float ratio = v.getScaleY() * 1f / distance;
            if (distance <= v.getScrollY()) {
                ratio = 1;
                if (tab2.getVisibility() != View.VISIBLE) {
                    tab2.setVisibility(View.VISIBLE);
                    statusView.setBackgroundColor(colorPrimary);
                }
            } else {
                if (tab2.getVisibility() == View.VISIBLE) {
                    tab2.setVisibility(View.INVISIBLE);
                    statusView.setBackgroundColor(Color.TRANSPARENT);
                }
            }
            if (null == evaluator) {
                evaluator = new ArgbEvaluator();
            }
            titleLayout.setBackgroundColor((int) evaluator.evaluate(ratio, Color.TRANSPARENT, colorPrimary));
            titleLayout.setTitleColor((int) evaluator.evaluate(ratio, Color.TRANSPARENT, Color.WHITE));
        }
    }
}