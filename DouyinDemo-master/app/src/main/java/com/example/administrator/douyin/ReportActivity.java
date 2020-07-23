package com.example.administrator.douyin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import view.ShareDialog;

public class ReportActivity extends AppCompatActivity {

    private ImageView backtoshare;
    private Button commitreport;
    private CheckBox seqingCb;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        backtoshare = findViewById(R.id.backtoshare);
        backtoshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToMain();
            }
        });
        commitreport = findViewById(R.id.commitreport);
        commitreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommitReport();
            }
        });

        seqingCb = (CheckBox)findViewById(R.id.seqing);
    }
//返回主界面
    private void ToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void CommitReport() {
        //提交举报结果


    }
}
