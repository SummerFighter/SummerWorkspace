package com.example.administrator.douyin;
import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.tabs.TabLayout;
import com.jiajie.load.LoadingDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Controller.DataCreate;
import entities.User;
import fragment.TabFragment;
import model.TabItemModel;
import model.VideoCase;
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
    private AppCompatTextView genderView;
    private AppCompatTextView locationView;
    private TabLayout work_like, set_poster;
    private TitleLayout titleLayout;
    private int colorPrimary;
    private ArgbEvaluator evaluator;
    private View statusView;
    private TextView likeNumView;
    private TextView follow;
    private TextView fans;
    private FullViewPager viewPager;
    private int getStatusBarHeight() {
        int height = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = getResources().getDimensionPixelSize(resId);
        }
        return height;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("jiuming","personinfo"+Constant.currentUser.toString());
        new DataCreate().initData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo);
        colorPrimary = ContextCompat.getColor(this, R.color.colorPrimary);

        TextView shouye = findViewById(R.id.shouye);
        shouye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toshouye();
            }
        });

        Button editinfo = findViewById(R.id.editinfo);
        editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInfo();
            }
        });

        follow = findViewById((R.id.follow));
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attention(0);
            }
        });
        fans = findViewById((R.id.fans));
        fans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attention(1);
            }
        });


        TextView msg = findViewById((R.id.xiaoxi));
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToMessage();
            }
        });

        accountTextView = findViewById(R.id.userId);
        usernameTextView = findViewById(R.id.username);
        avatarImageView = findViewById(R.id.iv_head);
        genderView=findViewById(R.id.sex);
        locationView=findViewById(R.id.location);
        likeNumView=findViewById(R.id.praise);

        ImageView setting = findViewById((R.id.settings));
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tosetting();
            }
        });
        initView();
    }

    private void EditInfo() {
        Intent intent = new Intent(this, EditMyinfo.class);
        startActivity(intent);
    }
    public void toshouye() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void attention(int index) {
        Intent intent = new Intent(this, FocusActivity.class);
        intent.putExtra("index",index);
        startActivity(intent);
    }

    private void ToMessage(){
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
    private void Tosetting(){
        Intent intent = new Intent(this, Setting.class);
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
        work_like = findViewById(R.id.work_dongtai_like);
        set_poster = findViewById(R.id.poster);
        titleLayout = findViewById(R.id.title_layout);
        viewPager = findViewById(R.id.viewpager);
    }

    private List<TabItemModel> getTabs() {
        List<model.TabItemModel> tabs = new ArrayList<>();
        //传输我的相关视频bundle
        Bundle myVideoBundle=new Bundle();
        ArrayList<String>myWorkCovers=new ArrayList<>();
        ArrayList<Integer>myWorkLikeNums=new ArrayList<>();
        for(VideoCase v:Constant.currentUserVideoWorks){
            myWorkCovers.add(v.getCoverURL());
            myWorkLikeNums.add(v.likeNum);
        }
        myVideoBundle.putStringArrayList("imageURL",myWorkCovers);
        myVideoBundle.putIntegerArrayList("likeNum",myWorkLikeNums);
        myVideoBundle.putString("rootActivity","myWorks");

        Bundle likeVideoBundle=new Bundle();
        ArrayList<String>myLikeCovers=new ArrayList<>();
        ArrayList<Integer>myLikeLikeNums=new ArrayList<>();
        for(VideoCase v:Constant.currentUserVideoLikes){
            myLikeCovers.add(v.getCoverURL());
            myLikeLikeNums.add(v.likeNum);
        }
        likeVideoBundle.putStringArrayList("imageURL",myLikeCovers);
        likeVideoBundle.putIntegerArrayList("likeNum",myLikeLikeNums);
        likeVideoBundle.putString("rootActivity","myLikes");

        tabs.add(new model.TabItemModel("作品"+ myWorkCovers.size(), TabFragment.class.getName(), myVideoBundle));
        //tabs.add(new model.TabItemModel("动态", TabFragment.class.getName(), null));
        tabs.add(new model.TabItemModel("喜欢"+ myLikeCovers.size(), TabFragment.class.getName(), likeVideoBundle));
        return tabs;
    }

    @Override
    public void onScrollChange(NestedScrollView v, int x, int y, int ox, int oy) {
        if (null != work_like && null != set_poster && null != titleLayout && null != statusView) {
            int distance = work_like.getTop() - titleLayout.getHeight() - statusView.getHeight();
            float ratio = v.getScaleY() * 1f / distance;
            if (distance <= v.getScrollY()) {
                ratio = 1;
                if (set_poster.getVisibility() != View.VISIBLE) {
                    set_poster.setVisibility(View.VISIBLE);
                    statusView.setBackgroundColor(colorPrimary);
                }
            } else {
                if (set_poster.getVisibility() == View.VISIBLE) {
                    set_poster.setVisibility(View.INVISIBLE);
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


    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }

    @SuppressLint("SetTextI18n")
    private void loadData(){
        accountTextView.setText(Constant.currentUser.getAccount());
        usernameTextView.setText(Constant.currentUser.getUsername());
        genderView.setText(Constant.currentUser.getGender());
        locationView.setText(Constant.currentUser.getArea());
        likeNumView.setText(Constant.currentUser.getLikeNum()+" 获赞 ");
        follow.setText(Constant.currentUser.follow+" 关注 ");
        fans.setText(Constant.currentUser.fans+" 粉丝 ");
        Glide.with(this)
                .load(Constant.currentUser.getAvatarUrl())
                .into(avatarImageView);
        viewPager.setAdapter(new adapter.TabAdapter(this, getSupportFragmentManager(), getTabs()));//我的作品和喜欢的作品
        work_like.setupWithViewPager(viewPager);
        set_poster.setupWithViewPager(viewPager);
    }

}