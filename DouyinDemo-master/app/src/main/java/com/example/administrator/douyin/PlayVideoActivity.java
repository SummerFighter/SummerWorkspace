package com.example.administrator.douyin;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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

public class PlayVideoActivity extends AppCompatActivity implements View.OnClickListener{
    private Uri upload;
    private File file = new File(Environment.getExternalStorageDirectory(),"myvideo.mp4");//打开软件直接播放的视频名字是movie.mp4
    private VideoView videoView;
    private static final int FILE_SELECT_CODE=1;
    private static final String TAG="PlayVideoActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        videoView=(VideoView)findViewById(R.id.video_view);
        Button play=(Button)findViewById(R.id.play);
        Button shoot=(Button)findViewById(R.id.shoot);
        Button upload=(Button)findViewById(R.id.upload);
        Button choice=(Button)findViewById(R.id.choice) ;//按钮的初始化
        choice.setOnClickListener(this);
        play.setOnClickListener(this);
        shoot.setOnClickListener(this);
        upload.setOnClickListener(this);//给按钮加监听
        if(ContextCompat.checkSelfPermission(PlayVideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PlayVideoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);//判断你是否授权
        }
        else {
            inintVideoPath();
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
                Intent intent1 = new Intent(PlayVideoActivity.this,ShootActivity.class);
                startActivity(intent1);
                break;
            case R.id.upload:
                if(upload==null){
                    Intent intent=new Intent(PlayVideoActivity.this,UploadActivity.class);
                    intent.putExtra("name",file.getPath());
                    PlayVideoActivity.this.startActivity(intent);
                }
                else{
                    String path = getPath(this,upload);
                    Intent intent=new Intent(PlayVideoActivity.this,UploadActivity.class);
                    intent.putExtra("name", path);
                    PlayVideoActivity.this.startActivity(intent);
                }
                break;
            case R.id.choice://选择文件
                Intent intent2=new Intent(Intent.ACTION_GET_CONTENT);
                intent2.setType("*/*");//设置类型，这是任意类型
                intent2.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent2,1);
        }
    }
    public void onDestroy(){//释放资源
        super.onDestroy();
        if(videoView!=null){
            videoView.suspend();
        }
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode== Activity.RESULT_OK){
            Uri uri=data.getData();
            videoView.setVideoURI(uri);//将选择的文件路径给播放器
            upload = uri;
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (requestCode == FILE_SELECT_CODE) {
            Uri uri = data.getData();
            upload = uri;
            Log.i(TAG, "------->" + uri.getPath());
        }
        super.onActivityResult(requestCode, resultCode, data);
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

}