package com.example.administrator.douyin;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiajie.load.LoadingDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import Controller.Constant;
import Controller.HttpUtil;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadActivity extends AppCompatActivity {
    private String path;
    private String cover_path;
    private Button up;
    private EditText name;
    private EditText describe;
    private ImageView cover;

    private CheckBox music;
    private CheckBox society;
    private CheckBox movie;
    private CheckBox game;
    private CheckBox food;
    private CheckBox science;
    private CheckBox fashion;
    private CheckBox culture;
    private CheckBox PE;
    private CheckBox live;
    private CheckBox child;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        final Intent intent = getIntent();
        path = intent.getStringExtra("name");
        cover_path = intent.getStringExtra("picture");

        Toast.makeText(UploadActivity.this,path+"\n"+ cover_path,Toast.LENGTH_LONG).show();

        up = (Button) findViewById(R.id.button);
        name = (EditText)findViewById(R.id.name);
        describe = (EditText)findViewById(R.id.describe);
        cover= findViewById(R.id.imageView3);

        music=findViewById(R.id.music_cb);
        game = findViewById(R.id.game_cb);
        live = findViewById(R.id.live_cb);
        PE = findViewById(R.id.PE_cb);
        culture = findViewById(R.id.culture_cb);
        food = findViewById(R.id.food_cb);
        society = findViewById(R.id.society_cb);
        fashion = findViewById(R.id.fashion_cb);
        movie = findViewById(R.id.movie_cb);
        child = findViewById(R.id.culture_cb);
        science = findViewById(R.id.science_cb);

        cover.setImageURI(Uri.parse(cover_path));

    }

    //在这里调用上传视频的接口
    public void btnUpload(View view) {

        final LoadingDialog dialog = new LoadingDialog.Builder(this).loadText("加载中...").build();
        dialog.show();

        String url = HttpUtil.rootUrl+"upload";
        File file = new File(path);

        Log.d("jiujiuwo","_________________");

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", Constant.currentUser.getAccount())
                .addFormDataPart(
                        "video",
                        "filename",
                        RequestBody.create(MediaType.parse("video/mpeg4"),file)
                ).addFormDataPart("videoTitle",name.getText().toString())
                .addFormDataPart("videoInfo",describe.getText().toString())
                ;

        if(music.isChecked()) builder.addFormDataPart("videoTag","音乐");
        if(game.isChecked()) builder.addFormDataPart("videoTag","游戏");
        if(society.isChecked()) builder.addFormDataPart("videoTag","社会");
        if(science.isChecked()) builder.addFormDataPart("videoTag","科技");
        if(fashion.isChecked()) builder.addFormDataPart("videoTag","时尚");
        if(child.isChecked()) builder.addFormDataPart("videoTag","儿童");
        if(culture.isChecked()) builder.addFormDataPart("videoTag","文化");
        if(movie.isChecked()) builder.addFormDataPart("videoTag","影视");
        if(live.isChecked()) builder.addFormDataPart("videoTag","生活");
        if(PE.isChecked()) builder.addFormDataPart("videoTag","体育");
        if(food.isChecked()) builder.addFormDataPart("videoTag","美食");

        RequestBody requestBody = builder.build();

        Log.d("jiujiuwo","_________________");

        HttpUtil.sendPostRequest(url, requestBody, new Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                JSONObject jsonObject = JSON.parseObject(responseData);
                int responseNum = jsonObject.getInteger("result");
                switch (responseNum) {
                    case 7:{
                        Looper.prepare();
                        Toast.makeText(UploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(UploadActivity.this, MainActivity.class);
                        dialog.dismiss();
                        startActivity(intent);
                        UploadActivity.this.finish();
                        Looper.loop();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Looper.prepare();
                Toast.makeText(UploadActivity.this, "请求出错", Toast.LENGTH_SHORT).show();
                Log.e("frost_connection",e.getMessage());
                dialog.dismiss();
                Looper.loop();
            }
        });


    }

//    class radioGroupListener implements RadioGroup.OnCheckedChangeListener{

//        @Override
//        public void onCheckedChanged(RadioGroup group, int checkedId) {
//            if(group.getId() == rg.getId()){
//                if(checkedId == R.id.music_rb){
//                    videoTag = "音乐";
//                }
//                else if(checkedId == R.id.movie_rb){
//                    videoTag = "影视";
//                }
//                else if(checkedId == R.id.society_rb){
//                    videoTag = "社会";
//                }
//                else if(checkedId == R.id.game_rb){
//                    videoTag = "游戏";
//                }
//                else if(checkedId == R.id.food_rb){
//                    videoTag = "美食";
//                }
//                else if(checkedId == R.id.child_rb){
//                    videoTag = "儿童";
//                }
//                else if(checkedId == R.id.live_rb){
//                    videoTag = "生活";
//                }
//                else if(checkedId == R.id.PE_rb){
//                    videoTag = "体育";
//                }
//                else if(checkedId == R.id.culture_rb){
//                    videoTag = "文化";
//                }
//                else if(checkedId == R.id.fashion_rb){
//                    videoTag = "时尚";
//                }
//                else if(checkedId == R.id.science_rb){
//                    videoTag = "科技";
//                }
//            }
//        }
//    }
}