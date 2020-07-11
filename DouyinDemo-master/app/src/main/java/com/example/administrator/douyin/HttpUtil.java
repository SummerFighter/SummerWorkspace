package com.example.administrator.douyin;

import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10000, TimeUnit.MILLISECONDS)
            .readTimeout(10000,TimeUnit.MILLISECONDS)
            .writeTimeout(10000, TimeUnit.MILLISECONDS).build();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    public static void postFile(String account, String description, String videoTag,
                                final ProgressListener listener, okhttp3.Callback callback, File...files){
        String url="http://127.0.0.1/api/upload";
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        Log.i("huang","files[0].getName()=="+files[0].getName());

        builder.addFormDataPart("account",account);
        builder.addFormDataPart("videoTitle",files[0].getName());
        builder.addFormDataPart("videoInfo",description);
        builder.addFormDataPart("videoTag",videoTag,
                RequestBody.create(MediaType.parse("application/octet-stream"),
                files[0]));
        MultipartBody multipartBody = builder.build();

        Request request  = new Request.Builder().url(url).post(new ProgressRequestBody(multipartBody,listener)).build();
        okHttpClient.newCall(request).enqueue(callback);
    }


}
