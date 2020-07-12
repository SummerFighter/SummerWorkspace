package com.example.administrator.douyin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonInfo extends AppCompatActivity {

    private TextView tz;
    private Button editinfo;
    private ImageView ht;
    private TextView sy;
    private TextView gz;
    private TextView xx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        sy = findViewById(R.id.sy);
        sy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asdasd();
            }
        });
        editinfo = findViewById(R.id.editinfo);
        editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInfo();
            }
        });
    }
    public void asdasd() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void EditInfo() {
        Intent intent = new Intent(this, EditMyinfo.class);
        startActivity(intent);
    }
}