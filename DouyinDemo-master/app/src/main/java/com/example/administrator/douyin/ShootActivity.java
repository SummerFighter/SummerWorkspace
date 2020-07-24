package com.example.administrator.douyin;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ShootActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoFitTextureView mTextureview;

    private Button mVideoRecodeBtn;//开始录像
    private LinearLayout mVerticalLinear;
    private Button mVideoRecodeBtn2;//开始录像
    private LinearLayout mHorizontalLinear;
    private Button mVHScreenBtn;
    private CameraController mCameraController;
    private boolean mIsRecordingVideo; //开始停止录像
    public static String BASE_PATH = Environment.getExternalStorageDirectory() + "/AAA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_shoot);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取相机管理类的实例
        mCameraController = CameraController.getmInstance(this);
        mCameraController.setFolderPath(BASE_PATH);

        initView();
        //判断当前横竖屏状态
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mVHScreenBtn.setText("切换为竖屏");
        } else {
            mVHScreenBtn.setText("切换为横屏");

        }
    }

    private void initView() {
        mTextureview = (AutoFitTextureView) findViewById(R.id.textureview);
        mVideoRecodeBtn = (Button) findViewById(R.id.video_recode_btn);
        mVideoRecodeBtn.setOnClickListener(this);
        mVerticalLinear = (LinearLayout) findViewById(R.id.vertical_linear);
        mVideoRecodeBtn2 = (Button) findViewById(R.id.video_recode_btn2);
        mVideoRecodeBtn2.setOnClickListener(this);
        mHorizontalLinear = (LinearLayout) findViewById(R.id.horizontal_linear);
        mVHScreenBtn = (Button) findViewById(R.id.v_h_screen_btn);
        mVHScreenBtn.setOnClickListener(this);

        //判断当前屏幕方向
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            //竖屏时
            mHorizontalLinear.setVisibility(View.VISIBLE);
            mVerticalLinear.setVisibility(View.GONE);
        } else {
            //横屏时
            mVerticalLinear.setVisibility(View.VISIBLE);
            mHorizontalLinear.setVisibility(View.GONE);
        }
        mCameraController.InitCamera(mTextureview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.video_recode_btn:
                if (mIsRecordingVideo) {
                    mIsRecordingVideo = !mIsRecordingVideo;
                    mCameraController.stopRecordingVideo();
                    mVideoRecodeBtn.setText("开始录像");
                    mVideoRecodeBtn2.setText("开始录像");
                    Toast.makeText(this, "录像结束", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    mVideoRecodeBtn.setText("停止录像");
                    mVideoRecodeBtn2.setText("停止录像");
                    mIsRecordingVideo = !mIsRecordingVideo;
                    mCameraController.startRecordingVideo();
                    Toast.makeText(this, "录像开始", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.video_recode_btn2:
                if (mIsRecordingVideo) {
                    mIsRecordingVideo = !mIsRecordingVideo;
                    mCameraController.stopRecordingVideo();
                    mVideoRecodeBtn.setText("开始录像");
                    mVideoRecodeBtn2.setText("开始录像");
                    Toast.makeText(this, "录像结束", Toast.LENGTH_SHORT).show();
                } else {
                    mVideoRecodeBtn.setText("停止录像");
                    mVideoRecodeBtn2.setText("停止录像");
                    mIsRecordingVideo = !mIsRecordingVideo;
                    mCameraController.startRecordingVideo();
                    Toast.makeText(this, "录像开始", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.v_h_screen_btn:
                //判断当前屏幕方向
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    //切换竖屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    Toast.makeText(ShootActivity.this, "竖屏了", Toast.LENGTH_SHORT).show();
                } else {
                    //切换横屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    Toast.makeText(ShootActivity.this, "横屏了", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}