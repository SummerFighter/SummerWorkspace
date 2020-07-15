package com.example.administrator.douyin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class SearchActivity extends AppCompatActivity {

    private Button SearchButton;
    private EditText keyword;
    private int[] imgs = {R.mipmap.img_video_3, R.mipmap.img_video_2, R.mipmap.img_video_1};
    private int[] videos = {R.raw.video_3, R.raw.video_2, R.raw.video_1};
    private String[] describes = {"第一个视频简介","阿巴阿巴阿巴阿巴","2333333333"};
    private Context p = this;
    private CheckBox[] cbs = new CheckBox[11];
    private String kinds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final LinearLayout layout = (LinearLayout) this.findViewById(R.id.layout);
        cbs[0]=this.findViewById(R.id.music_cb);
        cbs[1]=this.findViewById(R.id.movie_cb);
        cbs[2]=this.findViewById(R.id.society_cb);
        cbs[3]=this.findViewById(R.id.game_cb);
        cbs[4]=this.findViewById(R.id.food_cb);
        cbs[5]=this.findViewById(R.id.child_cb);
        cbs[6]=this.findViewById(R.id.live_cb);
        cbs[7]=this.findViewById(R.id.PE_cb);
        cbs[8]=this.findViewById(R.id.culture_cb);
        cbs[9]=this.findViewById(R.id.fashion_cb);
        cbs[10]=this.findViewById(R.id.science_cb);

        SearchButton = (Button) findViewById(R.id.search);
        keyword = (EditText) findViewById(R.id.keyword);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kinds="";
                for(int i=0;i<11;i++){
                    if(cbs[i].isChecked()){
                        kinds = kinds + cbs[i].getText() + " ";
                    }
                }
                Toast.makeText(p,kinds,Toast.LENGTH_SHORT).show();
                //在这里根据搜索条件(keyword和kinds)获取视频，并设置视频和封面的路径数组以及视频描述





                ImageButton Btn[] = new ImageButton[imgs.length];
                TextView Txt[] = new TextView[imgs.length];
                int j = -1;
                for (int i=0; i<Btn.length; i++) {
                    Btn[i] = new ImageButton(p);
                    Btn[i].setId(2000 + i);
                    Btn[i].setImageResource(imgs[i]);
                    Btn[i].setBackgroundColor(00000000);
                    Btn[i].setScaleType(ImageView.ScaleType.FIT_START);
                    Txt[i] = new TextView(p);
                    Txt[i].setText(describes[i]);
                    Txt[i].setTextSize(40);
                    Txt[i].setTextColor(Color.rgb(255, 255, 255));
                    layout.addView(Btn[i]); //将按钮放入layout组件
                    layout.addView(Txt[i]);
                }

                for (int k = 0; k <= Btn.length-1; k++) {

                    Btn[k].setTag(k);    //为按钮设置一个标记，来确认是按下了哪一个按钮

                    Btn[k].setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int i = (Integer) v.getTag();
                            Intent intent = new Intent(SearchActivity.this, MainActivity2.class);
                            intent.putExtra("index",i);
                            intent.putExtra("img", (Serializable) imgs);
                            intent.putExtra("video", (Serializable) videos);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}