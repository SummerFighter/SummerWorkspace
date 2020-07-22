package entities;

import android.util.Log;
import android.widget.Toast;

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
    //    private String avatarPath; //头像本地地址
    private String school;
    private String area;

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", balance='" + balance + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", school='" + school + '\'' +
                ", area='" + area + '\'' +
                ", gender='" + gender + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }

    private String gender;
    private String birth;
    public String getSchool() {
        return school;
    }

    public String getGender() {
        return gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public static User addUser(String account){
        User user = new User();
        user.setAccount(account);
        RequestBody requestBody = new FormBody.Builder()
                .add("account", String.valueOf(account))
                .build();

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
                user.setArea(map.get("area"));
                user.setBirth(map.get("birth"));
                user.setSchool(map.get("school"));
                user.setGender(map.get("gender"));

                Log.d("jiuming addUser",user.toString());
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("jiuming", "addUser onFailure");
            }
        });
        return user;
    }



    public String getBalance() {
        return balance;
    }

    public String getUsername() {
        return username;
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
