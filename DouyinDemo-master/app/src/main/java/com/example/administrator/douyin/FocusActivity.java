package com.example.administrator.douyin;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import java.util.ArrayList;

import adapter.CommPagerAdapter;
import butterknife.BindView;


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
    private String[] titles = new String[] {"关注 10", "粉丝 100", "推荐关注"};
    private ImageView bktoPI;

    protected int setLayoutId() {
        return R.layout.activity_focus;
    }


    protected void init() {

        for (int i=0;i<titles.length;i++) {
            fragments.add(new FansFragment());
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
        }

        pagerAdapter = new CommPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        bktoPI = findViewById(R.id.backtopersoninfo);
        bktoPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backto();
            }
        });
    }

    private void Backto() {
        Intent intent=new Intent(this,PersonInfo.class);
        startActivity(intent);
    }
}