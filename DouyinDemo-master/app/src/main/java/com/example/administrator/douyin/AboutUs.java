package com.example.administrator.douyin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AboutUs extends AppCompatActivity {
    private ImageView bktsetting;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        bktsetting = findViewById(R.id.backtosetting);
        bktsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Backtosetting();

            }
        });


    }

    private void Backtosetting() {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }
}