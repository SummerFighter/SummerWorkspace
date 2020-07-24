package com.example.administrator.douyin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.regex.Pattern;

import Controller.Constant;
import Controller.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import view.ShareDialog;

public class ReportActivity extends AppCompatActivity {

    private CheckBox[] cbs = new CheckBox[12];
    EditText editReasonView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        editReasonView=findViewById(R.id.edit_report_reason);
        ImageView backtoshare = findViewById(R.id.backtoshare);
        backtoshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String videoID=this.getIntent().getStringExtra("videoID");
        Button commitReport = findViewById(R.id.commit_report);
        commitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommitReport(videoID);
            }
        });
        Toast.makeText(ReportActivity.this, videoID, Toast.LENGTH_SHORT).show();
        cbs[0]=this.findViewById(R.id.seqing);
        cbs[1]=this.findViewById(R.id.ruma);
        cbs[2]=this.findViewById(R.id.feiyuanchuang);
        cbs[3]=this.findViewById(R.id.guanggao);
        cbs[4]=this.findViewById(R.id.illegal);
        cbs[5]=this.findViewById(R.id.zhengzhimingan);
        cbs[6]=this.findViewById(R.id.baoliqingxiang);
        cbs[7]=this.findViewById(R.id.yinrenbushi);
        cbs[8]=this.findViewById(R.id.weichengnianmistake);
        cbs[9]=this.findViewById(R.id.leadweichengnian);
        cbs[10]=this.findViewById(R.id.ziwoshanghai);
        cbs[11]=this.findViewById(R.id.youdao);

    }

    private void CommitReport(String videoID) {
        StringBuilder reportReason= new StringBuilder();
        //勾选的理由直接加入description
        for(int i=0;i<12;i++){
            if(cbs[i].isChecked()){
                reportReason.append(cbs[i].getText().toString()+",");
            }
        }
        Pattern pattern = Pattern.compile("\\s{2,}|\t");
        String editReason=editReasonView.getText().toString();

        if(!pattern.matcher(editReason).matches())
            reportReason.append(editReason);
        if(pattern.matcher(reportReason.toString()).matches()){
            Toast.makeText(ReportActivity.this, "您总得说点理由吧", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(ReportActivity.this, reportReason, Toast.LENGTH_SHORT).show();

        RequestBody body = new FormBody.Builder()
                .add("account", Constant.currentUser.getAccount())
                .add("videoID",videoID)
                .add("description",reportReason.toString())
                .build();
        HttpUtil.sendPostRequest(HttpUtil.rootUrl+"reportVideo", body, new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(responseData.equals("ok"))
                            Toast.makeText(ReportActivity.this, "您的反馈已经收到，感谢配合", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
        });
    }
}
