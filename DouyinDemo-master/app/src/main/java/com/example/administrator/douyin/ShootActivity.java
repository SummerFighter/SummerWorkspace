package com.example.administrator.douyin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.os.Build.VERSION_CODES.M;

public class ShootActivity extends Activity implements Camera.PreviewCallback {
    // 开始录制，停止录制按钮
    private Button startRecord,stopRecord;
    // 显示预览的SurfaceView
    private SurfaceView surfaceView;
    // 标记，判断当前是否正在录制
    boolean isRunning = false;
    // 录制类
    private MediaRecorder recorder;
    // 存储文件
    private File saveFile;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoot);

        startRecord = (Button) findViewById(R.id.startRecord);
        stopRecord = (Button) findViewById(R.id.stopRecord);
        surfaceView = (SurfaceView) findViewById(R.id.surView);

        // onCreate()初始化 ，一开始肯定没有开始录制，所以停止按钮不可点击
        stopRecord.setEnabled(false);

        // 设置Surface不需要维护自己的缓冲区
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 设置分辨率
        surfaceView.getHolder().setFixedSize(320, 280);
        // 设置该组件不会让屏幕自动关闭
        surfaceView.getHolder().setKeepScreenOn(true);
    }

    public void btnStartRecord(View view) {
        if(!isRunning){
            try{
                recorder = new MediaRecorder();
                recorder.reset();

                //解决摄像预览效果有90度翻转的问题
                Camera c = Camera.open();
                c.setDisplayOrientation(90);
                c.unlock();
                recorder.setCamera(c);

                //1.设置采集声音
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//设置采集图像
                recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);


//2.设置视频，音频的输出格式
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//3.设置音频的编码格式
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
//设置图像的编码格式
                recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

                //设置立体声
                recorder.setAudioChannels(2);
//设置最大录像时间 单位：毫秒
                recorder.setMaxDuration(600000);
//设置最大录制的大小 单位，字节
                recorder.setMaxFileSize(2048*2048);
//音频一秒钟包含多少数据位
                recorder.setAudioEncodingBitRate(128);
//设置选择角度，顺时针方向，因为默认是逆向90度的，这样图像就是正常显示了,这里设置的是观看保存后的视频的角度
                recorder.setOrientationHint(90);
                //设置录像的比特率
                recorder.setVideoEncodingBitRate(10*1920*1080);
                recorder.setVideoSize(640,480);


                //设置输出文件路径

                saveFile = new File("/mnt/sdcard/myvideo.mp4");
                recorder.setOutputFile(saveFile.getAbsolutePath());

                recorder.setPreviewDisplay(surfaceView.getHolder().getSurface());

                recorder.prepare();
//开始录制
                recorder.start();
//让开始按钮不可点击,停止按钮可点击
                startRecord.setEnabled(false);
                stopRecord.setEnabled(true);
                isRunning = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnStopRecord(View view) {
        if(isRunning){
            //停止录制
            recorder.stop();
//释放资源
            recorder.release();
            recorder = null;
//设置开始按钮可点击，停止按钮不可点击
            startRecord.setEnabled(true);
            stopRecord.setEnabled(false);
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
}