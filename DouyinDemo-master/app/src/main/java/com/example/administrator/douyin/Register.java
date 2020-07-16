package com.example.administrator.douyin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Controller.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Register extends Activity implements View.OnClickListener{


    private EditText user;
    private EditText pw;
    private EditText pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        user = (EditText) findViewById(R.id.et_user_register);
        pw = (EditText) findViewById(R.id.et_pass_register);
        pw2 = (EditText) findViewById(R.id.et_pass_register2);
        Button button = (Button) findViewById(R.id.register);

        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final String username1 = user.getText().toString().trim();
        final String password1 = pw.getText().toString().trim();
        final String password2 = pw2.getText().toString().trim();

        if (TextUtils.isEmpty(username1) || TextUtils.isEmpty(password1) || TextUtils.isEmpty(password2)) {
            Toast.makeText(Register.this, "请输入用户名，密码和确认密码！！！", Toast.LENGTH_SHORT).show();
        } else {
            if (!(password1.equals(password2))) {
                Toast.makeText(Register.this, "两次输入的密码不同，请重新输入！！！", Toast.LENGTH_SHORT).show();
            } else {

            }
        }
    }
}