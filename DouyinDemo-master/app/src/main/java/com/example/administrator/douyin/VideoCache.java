package com.example.administrator.douyin;

import android.app.Application;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

public class VideoCache extends Application {

    private HttpProxyCacheServer proxy;
    //本地代理服务器
    public static HttpProxyCacheServer getProxy(Context context) {
        VideoCache app = (VideoCache) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(this);
    }
}
