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
    private TabLayout work_like, set_poster;
    private TitleLayout titleLayout;
    private int colorPrimary;
    private ArgbEvaluator evaluator;
    private View statusView;
    private Button editinfo;
    private TextView shouye;
    private TextView follow;
    private TextView msg;

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

        shouye = findViewById(R.id.shouye);
        shouye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toshouye();
            }
        });

        editinfo = findViewById(R.id.editinfo);
        editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInfo();
            }
        });

        follow=findViewById((R.id.follow));
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attention();
            }
        });

        msg=findViewById((R.id.xiaoxi));
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToMessage();
            }
        });
        initView();
        accountTextView = findViewById(R.id.userId);
        usernameTextView = findViewById(R.id.username);
        avatarImageView = findViewById(R.id.iv_head);

        accountTextView.setText(Constant.currentUser.getAccount());
        usernameTextView.setText(Constant.currentUser.getUsername());
        Glide.with(this).load(Constant.currentUser.getAvatarUrl()).into(avatarImageView);

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
    public void toshouye() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void attention() {
        Intent intent = new Intent(this, FocusActivity.class);
        startActivity(intent);
    }

    private void ToMessage(){
        Intent intent = new Intent(this, ChatActivity.class);
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
        FullViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new adapter.TabAdapter(this, getSupportFragmentManager(), getTabs()));
        work_like.setupWithViewPager(viewPager);
        set_poster.setupWithViewPager(viewPager);
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

        Bundle likeVideoBundle=new Bundle();
        ArrayList<String>myLikeCovers=new ArrayList<>();
        ArrayList<Integer>myLikeLikeNums=new ArrayList<>();

        for(VideoCase v:Constant.currentUserVideoLikes){
            myLikeCovers.add(v.getCoverURL());
            myLikeLikeNums.add(v.likeNum);
        }
        likeVideoBundle.putStringArrayList("imageURL",myLikeCovers);
        likeVideoBundle.putIntegerArrayList("likeNum",myLikeLikeNums);

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
}