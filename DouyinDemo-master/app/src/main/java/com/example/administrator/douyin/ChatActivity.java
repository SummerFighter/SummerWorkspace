package com.example.administrator.douyin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Controller.Constant;
import Controller.HttpUtil;
import adapter.ChatAdapter;
import model.ChatMessage;
import model.PullXml;
import model.VideoCase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
 * 聊天页面，初始化采用本地assets文件夹下的data。xml文件初始化
 * */
public class ChatActivity extends AppCompatActivity implements ChatAdapter.ListItemClickListener{

    private List<ChatMessage> messages=new ArrayList<>();
    private static final String TAG = "ItemViews";
    private RecyclerView recycleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        //取得需要展示的数据
        /*
        try {
            InputStream assetInput = getAssets().open("data.xml");//data.xml在assets文件夹里面
            messages = PullXml.pull2xml(assetInput);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        */

        TextView shouye = findViewById(R.id.shouye);
        shouye.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                tomain();
            }
        });

        TextView myinfo = findViewById(R.id.wo);
        myinfo.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                tomyinfo();
           }
        });
        recycleView = findViewById(R.id.rv_list);

        //设置Manager，即设置其样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycleView.setLayoutManager(layoutManager);

        //recycleView.setHasFixedSize(true);
        loadData();
        ImageView bkto = findViewById(R.id.backto);
        bkto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backto();
            }
        });
    }

    private void tomain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void Backto() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void tomyinfo() {
        Intent intent = new Intent(this, PersonInfo.class);
        startActivity(intent);
    }
    @Override
    public void onListItemClick(String chat_target) {
//        System.out.println(chat_target);
        Intent it = new Intent(this, ChatRoom.class);
        it.putExtra("chat_target", chat_target);
        startActivity(it);
    }

    private void loadAdapter(){
        ChatAdapter cAdapter = new ChatAdapter(this, messages, this);
        recycleView.setAdapter(cAdapter);
    }

    private void loadData(){
        RequestBody requestBody = new FormBody.Builder()
                .add("account", Constant.currentUser.getAccount())
                .build();
        HttpUtil.sendPostRequest(HttpUtil.rootUrl+ "getMessage", requestBody, new Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseData = response.body().string();
                try {
                    JSONArray messageJSONArray =new JSONObject(responseData).getJSONArray("message");
                    for(int i=0;i<messageJSONArray.length();i++){
                        JSONObject messageJSON=messageJSONArray.getJSONObject(i);
                        ChatMessage c=new ChatMessage(messageJSON);
                        messages.add(c);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadAdapter();
                    }
                });
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
        });

    }

}
