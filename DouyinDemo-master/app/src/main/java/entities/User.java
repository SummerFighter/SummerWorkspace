package entities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import Controller.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class User {
    private String account; // 账号

    public String getAccount() {
        return account;
    }

    private String username; // 昵称
    private String balance; //余额
    private String avatarUrl; //头像服务器地址
    private String avatarPath; //头像本地地址


    public static User addUser(String account){
        User user = new User();

        RequestBody requestBody = new FormBody.Builder()
                .add("account", String.valueOf(account))
                .build();

        final CountDownLatch countDownLatch = new CountDownLatch(1); // 等待线程执行完毕

        HttpUtil.sendPostRequest(HttpUtil.rootUrl + "getUserInfo", requestBody, new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                JSONObject jsonObject = JSON.parseObject(responseData);

                Map<String, String> map = JSONObject.parseObject(jsonObject.getJSONObject("info").toJSONString(), new TypeReference<Map<String, String>>() {});
                user.setUsername(map.get("username"));
                user.setBalance(map.get("balance"));
                user.setAccount(map.get("account"));
                user.setAvatarUrl(map.get("avatarUrl"));

                countDownLatch.countDown();
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                countDownLatch.countDown();
            }
        });

        return  user;
    }




    public String getBalance() {
        return balance;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public String getUsername() {
        return username;
    }


    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
