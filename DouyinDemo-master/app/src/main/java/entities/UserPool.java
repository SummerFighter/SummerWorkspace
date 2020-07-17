package entities;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import Controller.HttpUtil;

public class UserPool {
    private static HashMap<String, User> users = new HashMap<>();
    public static User addUser(final String account, Context context){
        // 如果已经登陆了，直接返回记录的account
        if (users.containsKey(account)) {
            return getUser(account);
        }

        // 接下来获取用户所有信息
        final User user = new User();
        final CountDownLatch countDownLatch = new CountDownLatch(1); // 等待线程执行完毕
        RequestBody requestBody = new FormBody.Builder()
                .add("account", String.valueOf(account))
                .build();
        HttpUtil.sendPostRequest(HttpUtil.rootUrl + "getUserInfo", requestBody, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                countDownLatch.countDown();
            }

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
        });
        try {
            countDownLatch.await();
        }
        catch (InterruptedException e) {
        }
        // 下载头像图片
        try {
            File file = Glide.with(context)
                    .load(user.getAvatarUrl())
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            user.setAvatarPath(file.getAbsolutePath());
        }
        catch (ExecutionException | InterruptedException e) {

        }
        users.put(account, user);

        return user;
    }

    public static User getUser(String account) {
        return users.get(account);
    }
}
