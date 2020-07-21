package com.example.administrator.douyin;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import Controller.Constant;
import Controller.HttpUtil;
import adapter.DetailAdapter;
import model.VideoCase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements DetailAdapter.RemoveItemListener {
    private static final String TAG = "douyin";

    private SmartRefreshLayout refreshView;
    private RecyclerView mRecyclerView;
    private DetailAdapter mAdapter;
    private ImageView imgPlay;
    private ImageView imgThumb;
    private TextView commentView;

    private FullWindowVideoView fullVideoView;
    MyLayoutManager2 myLayoutManager;

    private ImageButton ShootButton;
    private ImageButton SearchButton;
    private int refreshNum = 0;

    private boolean isBuffer;
    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        ShootButton = (ImageButton) findViewById(R.id.shoot);
        ShootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayVideoActivity.class);
                startActivity(intent);
            }
        });
        SearchButton = (ImageButton) findViewById(R.id.tosearch);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        TextView myInfoTextView = (TextView)findViewById(R.id.myInfo);
        myInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PersonInfo.class);
                startActivity(intent);
            }
        });

        getVideoData(1);//开启子线程获取数据
        initState();
        initView();
    }

    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        refreshView = findViewById(R.id.refresh);
        refreshView.setEnableScrollContentWhenLoaded(false);
        mRecyclerView = findViewById(R.id.recycler);
        myLayoutManager = new MyLayoutManager2(this, OrientationHelper.VERTICAL, false);
        mRecyclerView.setLayoutManager(myLayoutManager);
        initListener();
    }

    //加载适配器
    private void loadAdapter(List<VideoCase> data){
        mAdapter = new DetailAdapter(this, data);
        mAdapter.setRemoveItemListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        myLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(TAG, "释放位置:" + position + " 下一页:" + isNext);
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position, boolean bottom) {
                Log.e(TAG, "选择位置:" + position + " 下一页:" + bottom);

                playVideo(position);
            }
        });

        refreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getVideoData(2);
            }
        });

        refreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getVideoData(3);
            }
        });
    }

    private void releaseVideo(int index) {
        View itemView = mRecyclerView.getChildAt(index);
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        //final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        videoView.stopPlayback();
        imgPlay.animate().alpha(0f).start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void playVideo(int position) {
        if (null != fullVideoView && fullVideoView.isPlaying()) return;
        View itemView = mRecyclerView.getChildAt(0);
        fullVideoView = itemView.findViewById(R.id.video_view);
        imgPlay = itemView.findViewById(R.id.img_play);
        imgThumb = itemView.findViewById(R.id.img_thumb);
        commentView = itemView.findViewById(R.id.comment_num);
        commentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDialog commentDialog = new CommentDialog();
                commentDialog.show(getSupportFragmentManager(), "");
            }
        });
        startVideoPlay();

    }

    private void startVideoPlay() {
        if (null == fullVideoView) return;

        fullVideoView.start();

        fullVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mp.setLooping(true);
                imgThumb.animate().alpha(0).setDuration(200).start();
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    //如果没在播放，则手动播放
                    if (!fullVideoView.isPlaying()) {
                        mp.start();
                    }
                }
                else if(what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                    //缓冲中
                    isBuffer = true;
                }else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    //恢复播放
                    isBuffer = false;
                }
                return false;
            }
        });

        fullVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
                if (null == arg0 || arg0.isPlaying()) return;
                if (!error) {
                    fullVideoView.seekTo(0);
                    fullVideoView.start();
                }
            }
        });

        fullVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                error = true;
                return false;
            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;
            @Override
            public void onClick(View v) {
                if (fullVideoView.isPlaying()) {
                    imgPlay.animate().alpha(0.7f).start();
                    fullVideoView.pause();
                    isPlaying = false;
                } else {
                    imgPlay.animate().alpha(0f).start();
                    fullVideoView.start();
                    isPlaying = true;
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != fullVideoView) fullVideoView.pause();
    }

    public void removeItem(final int position) {
        Constant.videoDatas.remove(position);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != fullVideoView)
            fullVideoView.stopPlayback();
    }

    private void getVideoData(int state){
        final String userAccount= getSharedPreferences("info1.txt", MODE_PRIVATE).getString("account","0");
        HttpUtil.getRecommendVideo(userAccount, refreshNum, new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(2==state){
                    Constant.videoDatas.clear();
                    //mAdapter.notifyDataSetChanged();
                }
                refreshNum++;
                final String responseData = response.body().string();
                int oldVideoDataNum=Constant.videoDatas.size(), newAddVideoDataNum= 0;
                try {
                    JSONArray videoJsonArray =new JSONObject(responseData).getJSONArray("videos");//获取的视频解析数组
                    newAddVideoDataNum = videoJsonArray.length();
                    for(int i=0;i<newAddVideoDataNum;i++){
                        JSONObject videoJSON=videoJsonArray.getJSONObject(i);
                        VideoCase v=new VideoCase(videoJSON);
                        Constant.videoDatas.add(v);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int finalNewAddVideoDataNum = newAddVideoDataNum;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (state){
                            case 1:
                                loadAdapter(Constant.videoDatas);
                                break;
                            case 2:
                                refreshView.finishRefresh();
                                //mAdapter.notifyItemRangeInserted(oldVideoDataNum, finalNewAddVideoDataNum);
                                break;
                            case 3:
                                refreshView.finishLoadMore();
                                mAdapter.notifyItemRangeInserted(oldVideoDataNum, finalNewAddVideoDataNum);
                                break;
                        }
                    }
                });
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"连接失败！！！",Toast.LENGTH_LONG).show();
                        //暂时不写
                    }
                });
            }
        });
    }

}