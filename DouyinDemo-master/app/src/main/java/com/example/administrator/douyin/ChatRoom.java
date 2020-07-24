package com.example.administrator.douyin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import adapter.ChatAdapter;
/*
 * 个人聊天界面
 * */
public class ChatRoom extends AppCompatActivity implements ChatAdapter.ListItemClickListener {

    private ChatAdapter mAdapter;
    private static final String TAG = "ItemViews";
    private ImageView backtomsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        //设置Manager，即设置其样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        backtomsg = findViewById(R.id.backtoMsg);
        backtomsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onListItemClick(String chat_target) {

    }
}
