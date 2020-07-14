package com.example.administrator.douyin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.Serializable;

public class SearchResultActivity extends AppCompatActivity {

    private String keyword;
    private int[] imgs = {R.mipmap.img_video_3, R.mipmap.img_video_2, R.mipmap.img_video_1};
    private int[] videos = {R.raw.video_3, R.raw.video_2, R.raw.video_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        final Intent intent = getIntent();
        keyword = intent.getStringExtra("name");

        //在这里根据搜索条件获取视频，并设置视频和封面的路径数组



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;


        LinearLayout layout = (LinearLayout) this.findViewById(R.id.layout);

        ImageButton Btn[] = new ImageButton[imgs.length];
        int j = -1;
        for (int i=0; i<Btn.length; i++) {
            Btn[i] = new ImageButton(this);
            Btn[i].setId(2000 + i);
            Btn[i].setImageResource(imgs[i]);
            Btn[i].setBackgroundColor(00000000);
            layout.addView(Btn[i]); //将按钮放入layout组件
        }

        for (int k = 0; k <= Btn.length-1; k++) {
            //这里不需要findId，因为创建的时候已经确定哪个按钮对应哪个Id
            Btn[k].setTag(k);    //为按钮设置一个标记，来确认是按下了哪一个按钮

            Btn[k].setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = (Integer) v.getTag();
                    Intent intent = new Intent(SearchResultActivity.this, MainActivity2.class);
                    intent.putExtra("index",i);
                    intent.putExtra("img", (Serializable) imgs);
                    intent.putExtra("video", (Serializable) videos);
                    startActivity(intent);
                }
            });
        }

    }


}