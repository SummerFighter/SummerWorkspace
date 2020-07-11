package com.example.administrator.douyin;

public interface ProgressListener {
    void onProgress(long currentBytes, long contentLength, boolean done);
}
