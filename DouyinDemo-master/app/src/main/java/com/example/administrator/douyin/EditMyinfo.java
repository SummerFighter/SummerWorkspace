package com.example.administrator.douyin;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditMyinfo extends AppCompatActivity {

    private Button fh,okBtn;
    EditText editname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        fh = findViewById(R.id.fh);
        fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasdwadf();
            }
        });

        okBtn = findViewById(R.id.ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store();
            }
        });
    }
    public void pasdwadf(){
        Intent intent=new Intent(this,PersonInfo.class);
        startActivity(intent);
    }
    public void store(){
        Intent intent=new Intent(this,PersonInfo.class);
        startActivity(intent);
    }


}
