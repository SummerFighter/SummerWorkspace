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

import Controller.Constant;
import adapter.DetailAdapter;
import adapter.DetailViewHolder;
import model.VideoCase;
import view.ShareDialog;


//只作为临时查看搜索结果和我的视频的视频播放界面
public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "douyin";

    private RecyclerView mRecyclerView;
    private DetailAdapter mAdapter;
    private ImageView imgPlay;
    private ImageView imgThumb;

    private FullWindowVideoView fullVideoView;
    MyLayoutManager myLayoutManager;

    private boolean isBuffer;
    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        ImageButton shootButton = (ImageButton) findViewById(R.id.shoot);
        shootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, PlayVideoActivity.class);
                startActivity(intent);
            }
        });
        ImageButton searchButton = (ImageButton) findViewById(R.id.tosearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView myInfoTextView = (TextView)findViewById(R.id.myInfo);
        myInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, PersonInfo.class);
                startActivity(intent);
            }
        });

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
        mRecyclerView = findViewById(R.id.recycler);
        myLayoutManager = new MyLayoutManager(this, OrientationHelper.VERTICAL, false);
        mRecyclerView.setLayoutManager(myLayoutManager);
        SmartRefreshLayout refreshView = findViewById(R.id.refresh);
        refreshView.setEnableRefresh(false);
        refreshView.setEnableLoadMore(false);
        initListener();
        loadAdapter();
    }

    //加载适配器
    private void loadAdapter(){
        String rootActivity = getIntent().getStringExtra("rootActivity");
        int currrentPage =getIntent().getIntExtra("index",0);
        if("search".equals(rootActivity))
            mAdapter = new DetailAdapter(this, Constant.searchVideoDatas);
        else if("myWorks".equals(rootActivity))
            mAdapter = new DetailAdapter(this, Constant.currentUserVideoWorks);
        else if("myLikes".equals(rootActivity))
            mAdapter = new DetailAdapter(this, Constant.currentUserVideoLikes);
        else
            mAdapter = new DetailAdapter(this, Constant.videoDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(currrentPage);
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
        DetailViewHolder holder=(DetailViewHolder) mRecyclerView.findViewHolderForLayoutPosition(position);
        if (null == holder || null == holder.itemView) return;
        fullVideoView = holder.getView(R.id.video_view);
        if (null == fullVideoView) return;
        fullVideoView.requestFocus();
        imgPlay=holder.getView(R.id.img_play);
        imgThumb=holder.getView(R.id.img_thumb);
        TextView commentView = holder.getView(R.id.comment_num);
        TextView shareView = holder.getView(R.id.share);

        commentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDialog commentDialog = new CommentDialog();
                //传递videoID和位置信息
                Bundle bundle=new Bundle();
                bundle.putString("videoID",holder.getVideoID());
                bundle.putInt("position", position);
                commentDialog.setArguments(bundle);
                commentDialog.show(getSupportFragmentManager(), "");
            }
        });

        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareDialog shareDialog = new ShareDialog();
                shareDialog.show(getSupportFragmentManager(), holder.getVideoID());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != fullVideoView)
            fullVideoView.stopPlayback();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (null == fullVideoView || fullVideoView.isPlaying())
            return;
        fullVideoView.start();
        imgPlay.animate().alpha(0f).start();//fullVideoView不为null的话imgPlay也不为null
    }
}
