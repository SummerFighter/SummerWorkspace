package com.example.administrator.douyin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

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

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Controller.Constant;
import Controller.HttpUtil;
import model.VideoCase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    private ImageButton SearchButton;
    private EditText keyword;
    private LinearLayout layout;
    private CheckBox[] cbs = new CheckBox[11];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        layout = (LinearLayout) this.findViewById(R.id.search_result_layout);
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

        SearchButton = (ImageButton) findViewById(R.id.search);
        keyword = (EditText) findViewById(R.id.keyword);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在这里根据搜索条件(keyword和kinds)获取视频，并设置视频和封面的路径数组以及视频描述
                layout.removeAllViews();
                List<String> searchTags=new ArrayList<>();
                for(int i=0;i<11;i++){
                    if(cbs[i].isChecked()){
                        searchTags.add(cbs[i].getText().toString());
                    }
                }
                if(searchTags.isEmpty()){
                    RequestBody body = new FormBody.Builder()
                            .add("keyword",keyword.getText().toString())
                            .build();
                    HttpUtil.sendPostRequest(HttpUtil.rootUrl+ "returnByKeyword", body, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SearchActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Constant.searchVideoDatas.clear();
                            final String responseData = response.body().string();
                            JSONArray videoJsonArray =JSON.parseObject(responseData).getJSONArray("videos");//获取的视频解析数组
                            for(int i=0;i<videoJsonArray.size();i++){
                                JSONObject videoJSON=videoJsonArray.getJSONObject(i);
                                VideoCase v=new VideoCase(videoJSON);
                                Constant.searchVideoDatas.add(v);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadResultLayout();
                                }
                            });
                        }
                    });
                }
                else {
                    FormBody.Builder builder=new FormBody.Builder();
                    for(String searchTag : searchTags){
                        builder.add("tag",searchTag);
                    }
                    HttpUtil.sendPostRequest(HttpUtil.rootUrl+ "returnByTag", builder.build(), new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SearchActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Constant.searchVideoDatas.clear();
                            final String responseData = response.body().string();
                            JSONArray videoJsonArray =JSON.parseObject(responseData).getJSONArray("videos");//获取的视频解析数组
                            for(int i=0;i<videoJsonArray.size();i++){
                                JSONObject videoJSON=videoJsonArray.getJSONObject(i);
                                VideoCase v=new VideoCase(videoJSON);
                                Constant.searchVideoDatas.add(v);
                            }
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    loadResultLayout();
                                }
                            });
                        }
                    });
                }

            }
        });
    }

    private void loadResultLayout(){
        //没有任何结果时
        if(Constant.searchVideoDatas.isEmpty()){
            TextView textView=new TextView(this);
            textView.setText("没有找到你想要的结果！");
            textView.setTextSize(40);
            textView.setTextColor(Color.rgb(255, 255, 255));
            layout.addView(textView);
            return;
        }
        int resultNum = Constant.searchVideoDatas.size();
        for (int i=0;i<resultNum;i++){
            ImageButton imageButton=new ImageButton(this);
            Glide.with(this)
                    .load(Constant.searchVideoDatas.get(i).getCoverURL())
                    .into(imageButton);
            imageButton.setBackgroundColor(00000000);
            imageButton.setScaleType(ImageView.ScaleType.FIT_START);
            int finalI = i;
            imageButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                    intent.putExtra("rootActivity","search");//提示启动界面
                    intent.putExtra("index", finalI);//设置位置
                    startActivity(intent);
                }
            });

            TextView textView=new TextView(this);
            textView.setText(Constant.searchVideoDatas.get(i).getDescription());
            textView.setTextSize(40);
            textView.setTextColor(Color.rgb(255, 255, 255));

            layout.addView(imageButton);
            layout.addView(textView);
        }

    }

}