package com.example.administrator.douyin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

import adapter.ChatAdapter;
import model.ChatMessage;
import model.PullXml;
/*
 * 聊天页面，初始化采用本地assets文件夹下的data。xml文件初始化
 * */
public class ChatActivity extends AppCompatActivity implements ChatAdapter.ListItemClickListener{


    private ChatAdapter cAdapter;
    private List<ChatMessage> messages;
    private static final String TAG = "ItemViews";
    private ImageView bkto;
    private TextView shouye;
    private TextView myinfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        //取得需要展示的数据
        try {
            InputStream assetInput = getAssets().open("data.xml");//data.xml在assets文件夹里面
            messages = PullXml.pull2xml(assetInput);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        shouye = findViewById(R.id.shouye);
        shouye.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                tomain();
            }
        });

        myinfo = findViewById(R.id.wo);
        myinfo.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                tomyinfo();
           }
        });
        //定义recycleView
        RecyclerView recycleView = findViewById(R.id.rv_list);

        //设置Manager，即设置其样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycleView.setLayoutManager(layoutManager);

        recycleView.setHasFixedSize(true);

        //创建Adapter,将数据传入
        cAdapter = new ChatAdapter(messages, this);

        //设置Adapter
        recycleView.setAdapter(cAdapter);

        bkto = findViewById(R.id.backto);
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


}
