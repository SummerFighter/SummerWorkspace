package com.example.administrator.douyin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UploadActivity extends AppCompatActivity {
    private String path;
    private Button up;
    private EditText name;
    private EditText describe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        final Intent intent = getIntent();
        path = intent.getStringExtra("name");

        up = (Button) findViewById(R.id.up);
        name = (EditText)findViewById(R.id.name);
        describe = (EditText)findViewById(R.id.describe);
    }

    //在这里调用上传视频的接口
    public void btnUpload(View view) {
        String test = name.getText().toString() + describe.getText().toString() + path;
        Toast.makeText(this,test,Toast.LENGTH_LONG).show();
    }
}