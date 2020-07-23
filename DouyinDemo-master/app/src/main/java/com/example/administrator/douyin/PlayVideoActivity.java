package com.example.administrator.douyin;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import Controller.Constant;
import Controller.FileUtil;
import Controller.MediaRecorderUtil;
import Controller.Mp4ParseUtil;

public class PlayVideoActivity extends AppCompatActivity implements View.OnClickListener{
    private Uri upload;
    private Uri music;
    private File file = new File(Environment.getExternalStorageDirectory() + "/AAA","myvideo.mp4");
    private File editfile = new File(Environment.getExternalStorageDirectory() + "/AAA","myevideo.mp4");
    private VideoView videoView;
    private static final int FILE_SELECT_CODE=1;
    private static final String TAG="PlayVideoActivity";
    private boolean edit = false;
    private boolean record = false;
    MediaRecorderUtil mrUtil=new MediaRecorderUtil();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        edit = false;
        record = false;
        videoView=(VideoView)findViewById(R.id.video_view);
        Button play=(Button)findViewById(R.id.play);
        Button shoot=(Button)findViewById(R.id.shoot);
        Button upload=(Button)findViewById(R.id.upload);
        Button choice=(Button)findViewById(R.id.choice) ;//按钮的初始化
        Button music=(Button)findViewById(R.id.background_music);
        Button confirm=(Button)findViewById(R.id.confirm);
        Button voice=(Button)findViewById(R.id.background_voice);
        Button confirm2=(Button)findViewById(R.id.confirm2);
        choice.setOnClickListener(this);
        play.setOnClickListener(this);
        shoot.setOnClickListener(this);
        upload.setOnClickListener(this);//给按钮加监听
        music.setOnClickListener(this);
        confirm.setOnClickListener(this);
        voice.setOnClickListener(this);
        confirm2.setOnClickListener(this);

        if(ContextCompat.checkSelfPermission(PlayVideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PlayVideoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);//判断你是否授权
        }
        else {
            inintVideoPath();
        }
        if(ContextCompat.checkSelfPermission(PlayVideoActivity.this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PlayVideoActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},1);//判断你是否授权
        }
        if(ContextCompat.checkSelfPermission(PlayVideoActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PlayVideoActivity.this,new String[]{Manifest.permission.CAMERA},1);//判断你是否授权
        }
    }

    private void inintVideoPath(){
        videoView.setVideoPath(file.getPath());//指定视频文件的路径
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    inintVideoPath();
                }
                else {
                    Toast.makeText(this,"拒绝权限将无法访问程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public void onClick(View v){//各按钮的功能
        switch (v.getId()){
            case R.id.play:
                if(!videoView.isPlaying()){//播放
                    videoView.start();
                }
                break;
            case R.id.shoot:
                edit = false;
                videoView.setVideoPath(file.getPath());
                Intent intent1 = new Intent(PlayVideoActivity.this,ShootActivity.class);
                startActivity(intent1);
                break;
            case R.id.upload:
                if(edit){
                    Intent intent=new Intent(PlayVideoActivity.this,UploadActivity.class);
                    intent.putExtra("name",editfile.getPath());
                    intent.putExtra("picture", GetFirstFrame(editfile.getPath()));
                    PlayVideoActivity.this.startActivity(intent);
                }
                else if(upload==null){
                    Intent intent=new Intent(PlayVideoActivity.this,UploadActivity.class);
                    intent.putExtra("name",file.getPath());
                    intent.putExtra("picture", GetFirstFrame(file.getPath()));
                    PlayVideoActivity.this.startActivity(intent);
                }
                else{
                    String path = getPath(this,upload);
                    Intent intent=new Intent(PlayVideoActivity.this,UploadActivity.class);
//                    Toast.makeText(PlayVideoActivity.this,path+"\n"+GetFirstFrame(path),Toast.LENGTH_LONG).show();
                    intent.putExtra("name", path);
                    intent.putExtra("picture", GetFirstFrame(path));
                    PlayVideoActivity.this.startActivity(intent);

                }
                break;
            case R.id.choice://选择文件
                edit = false;
                Intent intent2=new Intent(Intent.ACTION_GET_CONTENT);
                intent2.setType("*/*");//设置类型，这是任意类型
                intent2.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent2,FILE_SELECT_CODE);
                break;
            case R.id.background_music:
                Intent intent3=new Intent(Intent.ACTION_GET_CONTENT);
                intent3.setType("*/*");//设置类型，这是任意类型
                intent3.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent3,77);
                break;
            case R.id.confirm:
                if(upload==null&&music!=null){
                    Mp4ParseUtil.muxAacMp4(FileUtil.getFilePathByUri(this, music),file.getPath(),editfile.getPath());
                    videoView.setVideoPath(editfile.getPath());
                    edit = true;
                }
                else if(music!=null){
                    Mp4ParseUtil.muxAacMp4(FileUtil.getFilePathByUri(this, music),FileUtil.getFilePathByUri(this, upload),editfile.getPath());
                    videoView.setVideoPath(editfile.getPath());
                    edit = true;
                }
                break;
            case R.id.background_voice:
                if(!videoView.isPlaying()){//播放
                    videoView.start();
                }
                mrUtil.recorderStart();
                record = true;
                break;
            case R.id.confirm2:
                if(record) {
                    mrUtil.recorderSave();
                    if (upload == null) {
                        Mp4ParseUtil.muxAacMp4(Environment.getExternalStorageDirectory() + "/AAA/recorder.aac", file.getPath(), editfile.getPath());
                        videoView.setVideoPath(editfile.getPath());
                        edit = true;
                    } else{
                        Mp4ParseUtil.muxAacMp4(Environment.getExternalStorageDirectory() + "/AAA/recorder.aac", FileUtil.getFilePathByUri(this, upload), editfile.getPath());
                        videoView.setVideoPath(editfile.getPath());
                        edit = true;
                    }
                }
                record = false;
        }
    }
    public void onDestroy(){//释放资源
        super.onDestroy();
        if(videoView!=null){
            videoView.suspend();
        }
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                Uri uri=data.getData();
                videoView.setVideoURI(uri);//将选择的文件路径给播放器
                upload = uri;
                Toast.makeText(PlayVideoActivity.this,upload.getPath(),Toast.LENGTH_LONG).show();
                break;
            case 77:
                Uri uri2=data.getData();
                music = uri2;
                Toast.makeText(PlayVideoActivity.this,music.getPath(),Toast.LENGTH_LONG).show();
                break;
        }
    }

    //根据uri获取视频的绝对路径
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }

            else if (isDownloadsDocument(uri)) {


                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));


                return getDataColumn(context, contentUri, null, null);
            }

            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }

        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String GetFirstFrame(String path){
        MediaMetadataRetriever mmr=new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
        File file=new File(path);
        mmr.setDataSource(path);
        Bitmap bitmap = mmr.getFrameAtTime(0);  //0表示首帧图片
        mmr.release(); //释放MediaMetadataRetriever对象
        if(bitmap!=null){
            //存储媒体已经挂载，并且挂载点可读/写。
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                bitmap.recycle(); //回收bitmap
                return "";
            }
            try {
                String framePath = Environment.getExternalStorageDirectory() + "/AAA"; //图片保存文件夹
                File frame_file = new File(framePath);
                if (!frame_file.exists()) { //// 如果路径不存在，就创建路径
                    frame_file.mkdirs();
                }
                File picture_file = new File(framePath,"mypicture.jpg"); // 创建路径和文件名的File对象
                FileOutputStream out = new FileOutputStream(picture_file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();   //注意关闭文件流
                return picture_file.getPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return "";
        }
        return "";
    }

}