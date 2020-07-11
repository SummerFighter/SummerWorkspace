package com.example.administrator.douyin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UploadActivity extends AppCompatActivity {
    private String path;
    private Button up;
    private EditText name;
    private EditText describe;

    private ProgressBar postProgress;
    private TextView postText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        final Intent intent = getIntent();
        path = intent.getStringExtra("name");

        up = (Button) findViewById(R.id.up);
        name = (EditText)findViewById(R.id.name);
        describe = (EditText)findViewById(R.id.describe);
        postProgress=(ProgressBar)findViewById(R.id.postProgressBar);
        postText=(TextView)findViewById(R.id.postText);
        postProgress.setVisibility(View.INVISIBLE);
        postText.setVisibility(View.INVISIBLE);
    }

    //在这里调用上传视频的接口
    public void btnUpload(View view) {
        String test = name.getText().toString() + describe.getText().toString() + path;
        Toast.makeText(this,test,Toast.LENGTH_LONG).show();

        postProgress.setVisibility(View.VISIBLE);
        postText.setVisibility(View.VISIBLE);

        File file = new File(path);
        //还缺少的两个参数
        String account="";
        String videoTag=" ";

        HttpUtil.postFile(account,describe.getText().toString(), videoTag, new ProgressListener() {
            @Override
            public void onProgress(long currentBytes, long contentLength, boolean done) {
                //Log.i(TAG, "currentBytes==" + currentBytes + "==contentLength==" + contentLength + "==done==" + done);
                int progress = (int) (currentBytes * 100 / contentLength);
                postProgress.setProgress(progress);
                postText.setText(progress + "%");
            }
        }, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null) {
                    String result = response.body().string();
                    //Log.i(TAG, "result===" + result);
                }
            }
        }, file);


    }
}