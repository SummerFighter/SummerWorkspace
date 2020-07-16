package com.example.administrator.douyin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

/*
* 登录
* */
public class Login extends Activity{
    private SharedPreferences.Editor editor;

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button login;

    private static final int OK = 200;
    private CheckBox rememberPass;
    private SharedPreferences sp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getWindow().setBackgroundDrawableResource(R.drawable.login);//第二种方式设置背景图片
        sp = getSharedPreferences("info1.txt", MODE_PRIVATE);
        accountEdit = (EditText) findViewById(R.id.user);
        passwordEdit = (EditText) findViewById(R.id.pass);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        login = (Button) findViewById(R.id.login);
        boolean isRemember = sp.getBoolean("remember_password", false);
        if (isRemember) {
            //将账号密码都设置到文本框中
            String account = sp.getString("account", "");
            String password = sp.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String account = accountEdit.getText().toString();
                final String password = passwordEdit.getText().toString();

                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this,"账号或密码不能为空！",Toast.LENGTH_SHORT).show();
                }
                else {
                    editor = sp.edit();
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.commit();
                    HttpUtil.login(account,password,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            try{
                                runOnUiThread(new Runnable(){
                                    @Override
                                    public void run() {
                                        Toast.makeText(Login.this, "连接失败！！", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch(Exception ee){
                                ee.printStackTrace();
                            }
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //final String responseData = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    });

                }
            }
        });

        TextView tv_register=(TextView) findViewById(R.id.register);
        tv_register.setClickable(true);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Login.this,Register.class);
                startActivity(intent2);
            }
        });
    }
}