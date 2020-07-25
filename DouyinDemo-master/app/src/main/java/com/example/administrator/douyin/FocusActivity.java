package com.example.administrator.douyin;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import Controller.Constant;
import Controller.HttpUtil;
import adapter.CommPagerAdapter;
import butterknife.BindView;
import model.FragmentUserItem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 *关注、粉丝
 * */
public class FocusActivity extends BaseActivity {
    @BindView(R.id.tablayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private CommPagerAdapter pagerAdapter;
    private String[] titles = new String[] {"关注", "粉丝", "推荐关注"};
    private ArrayList<FragmentUserItem> follows=new ArrayList<>();
    private ArrayList<FragmentUserItem> fans=new ArrayList<>();
    private ArrayList<FragmentUserItem> recommends=new ArrayList<>();
    private int index;

    protected int setLayoutId() {
        return R.layout.activity_focus;
    }

    protected void init() {
        index=getIntent().getIntExtra("index",0);
        initData();
        ImageView bktoPI = findViewById(R.id.backtopersoninfo);
        bktoPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData(){
        RequestBody requestBody = new FormBody.Builder()
                .add("account", Constant.currentUser.getAccount())
                .build();
        HttpUtil.sendPostRequest(HttpUtil.rootUrl+"myFollowers", requestBody, new Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseData = response.body().string();
                try {
                    JSONObject resultJSON=new JSONObject(responseData);
                    JSONArray followsJSONArray=resultJSON.getJSONArray("myFollows");
                    JSONArray fansJSONArray=resultJSON.getJSONArray("myFollowers");
                    JSONArray recommendsJSONArray=resultJSON.getJSONArray("recommends");
                    for(int i=0;i<followsJSONArray.length();i++){
                        FragmentUserItem item=new FragmentUserItem(followsJSONArray.getJSONObject(i));
                        item.setSign("已关注");
                        follows.add(item);
                    }
                    for(int i=0;i<fansJSONArray.length();i++){
                        FragmentUserItem item=new FragmentUserItem(fansJSONArray.getJSONObject(i));
                        item.setSign("关注");
                        fans.add(item);
                    }
                    for(int i=0;i<recommendsJSONArray.length();i++){
                        JSONObject itemJSON=recommendsJSONArray.getJSONObject(i);
                        if(Constant.currentUser.getAccount().equals(itemJSON.getString("account")))
                            continue;
                        FragmentUserItem item=new FragmentUserItem(itemJSON);
                        int flag=itemJSON.getInt("sign");
                        item.setSign(flag==0? "关注":"已关注");
                        recommends.add(item);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle[] bundles=new Bundle[3];
                        FansFragment[] fragment=new FansFragment[3];
                        for(int i=0;i<3;i++){
                            bundles[i]=new Bundle();
                            fragment[i]=new FansFragment();
                        }
                        bundles[0].putParcelableArrayList("fragmentUserItems",follows);
                        bundles[1].putParcelableArrayList("fragmentUserItems",fans);
                        bundles[2].putParcelableArrayList("fragmentUserItems",recommends);

                        for(int i=0;i<3;i++){
                            fragment[i].setArguments(bundles[i]);
                            fragments.add(fragment[i]);
                            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
                        }
                        pagerAdapter = new CommPagerAdapter(getSupportFragmentManager(), fragments, titles);
                        viewPager.setAdapter(pagerAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                        tabLayout.getTabAt(index).select();
                    }
                });
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }

}