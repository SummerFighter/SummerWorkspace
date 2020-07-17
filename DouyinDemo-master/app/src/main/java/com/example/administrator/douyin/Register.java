package com.example.administrator.douyin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiajie.load.LoadingDialog;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Controller.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends Activity implements View.OnClickListener{

    private final static int REGISTER_SUCCESS = 4;
    private final static int REGISTER_FAILURE = 5;

    private EditText user;
    private EditText pw;
    private EditText pw2;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        user = (EditText) findViewById(R.id.et_user_register);
        pw = (EditText) findViewById(R.id.et_pass_register);
        pw2 = (EditText) findViewById(R.id.et_pass_register2);
        username = (EditText) findViewById(R.id.et_username_register);
        Button button = (Button) findViewById(R.id.register);

        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final String username1 = user.getText().toString().trim();
        final String password1 = pw.getText().toString().trim();
        final String password2 = pw2.getText().toString().trim();
        final String username2 = username.getText().toString().trim();

        if (TextUtils.isEmpty(username1) || TextUtils.isEmpty(password1) || TextUtils.isEmpty(password2)) {
            Toast.makeText(Register.this, "请输入用户名，密码和确认密码！！！", Toast.LENGTH_SHORT).show();
        } else {
            if (!(password1.equals(password2))) {
                Toast.makeText(Register.this, "两次输入的密码不同，请重新输入！！！", Toast.LENGTH_SHORT).show();
            } else {
                registerAsync(username1,password1,username2);
            }
        }
    }

    private void registerAsync(final String account, final String password, String username) {
        final LoadingDialog dialog = new LoadingDialog.Builder(this).loadText("加载中...").build();
        dialog.show();

        RequestBody requestBody = new FormBody.Builder()
                .add("account", account)
                .add("password", password)
                .add("username",username)
                .build();
        String url = HttpUtil.rootUrl+"register";
        HttpUtil.sendPostRequest(url, requestBody, new Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                JSONObject jsonObject = JSON.parseObject(responseData);
                int responseNum = jsonObject.getInteger("result");
                switch (responseNum) {
                    case REGISTER_SUCCESS: {
                        Looper.prepare();
                        Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("account", account);
                        intent.putExtra("password", password);
                        Register.this.setResult(RESULT_OK, intent);
                        dialog.dismiss();
                        Register.this.finish();
                        Looper.loop();
                        break;
                    }
                    case REGISTER_FAILURE: {
                        Looper.prepare();
                        Toast.makeText(Register.this, "账号已被注册", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Looper.loop();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Looper.prepare();
                Toast.makeText(Register.this, "注册出错", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Looper.loop();
            }
        });
    }

}