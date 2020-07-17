package com.example.administrator.douyin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.jiajie.load.LoadingDialog;
import com.alibaba.fastjson.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Controller.Constant;
import Controller.HttpUtil;
import entities.UserPool;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
* 登录
* */
public class Login extends AppCompatActivity {
    private SharedPreferences.Editor editor;

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button login;

    private final static int LOGIN_SUCCESS = 1;
    private final static int PASSWORD_WRONG = 2;
    private final static int ACCOUNT_NOT_EXIST = 3;


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

                    loginAsync(account, password);

                }
            }
        });

    }

    //登陆
    private void loginAsync(final String account, String password) {
        final LoadingDialog dialog = new LoadingDialog.Builder(this).loadText("加载中...").build();
        dialog.show();

        RequestBody requestBody = new FormBody.Builder()
                .add("account", account)
                .add("password", password)
                .build();
        String url = HttpUtil.rootUrl +"login";
        HttpUtil.sendPostRequest(url, requestBody, new Callback(){

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                JSONObject jsonObject = JSON.parseObject(responseData);
                int responseNum = jsonObject.getInteger("result");
                switch (responseNum) {
                    case LOGIN_SUCCESS: {
                        Looper.prepare();
                        Constant.currentUser = UserPool.addUser(account, Login.this);
                        Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(Login.this, MainActivity.class);
                        dialog.dismiss();
                        startActivity(intent);
                        Login.this.finish();
                        Looper.loop();
                        return;
                    }
                    case PASSWORD_WRONG: {
                        Looper.prepare();
                        Toast.makeText(Login.this, "密码错误", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Looper.loop();
                        break;


                    }
                    case ACCOUNT_NOT_EXIST: {
                        Looper.prepare();
                        Toast.makeText(Login.this, "账号不存在", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Looper.loop();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求错误", e.getMessage());
                Looper.prepare();
                Toast.makeText(Login.this, "登录出错", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Looper.loop();
            }
        });
    }
    // 请求注册账号
    public void OnClick_registerButton(View v)
    {
        Intent intent=new Intent();
        intent.setClass(Login.this, Register.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                accountEdit.setText(data.getStringExtra("account"));
                passwordEdit.setText(data.getStringExtra("password"));
            }
        }
    }

}