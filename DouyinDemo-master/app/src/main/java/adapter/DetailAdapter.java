package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.administrator.douyin.CommentDialog;
import com.example.administrator.douyin.MainActivity;
import com.example.administrator.douyin.PersonInfo;
import com.example.administrator.douyin.R;
import com.example.administrator.douyin.VideoCache;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import Controller.Constant;
import Controller.HttpUtil;
import model.VideoCase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailAdapter extends RecyclerView.Adapter<DetailViewHolder>{
    private Context mContext;
    private List<VideoCase> videoList;
    protected RemoveItemListener removeItemListener;
    public DetailAdapter(Context context,List<VideoCase> list) {
        this.mContext = context;
        this.videoList=list;
    }

    @NotNull
    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);
        return new DetailViewHolder(view,mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        if (null==videoList) {
            if (null != removeItemListener)
                removeItemListener.removeItem(position);
            return;
        }
        VideoCase videoCase=videoList.get(position);
        Glide.with(mContext)
                .load(videoCase.getCoverURL())
                .into(holder.img_thumb);
        Glide.with(mContext)
                .load(videoCase.getAuthorAvatar())
                .into(holder.avatar_view);

        holder.videoID=videoCase.getID();
        holder.video_title.setText(videoCase.getTitle());
        holder.video_info.setText(videoCase.getDescription());
        holder.like_num.setText(String.valueOf(videoCase.likeNum));
        holder.comment_num.setText(String.valueOf(videoCase.commentNum));
        holder.videoView.setVideoPath(VideoCache.getProxy(this.mContext).getProxyUrl(videoCase.getURL()));
        holder.author_account = videoCase.getAuthorAccount();

        Drawable likeIcon = mContext.getResources().getDrawable(R.mipmap.heart_icon2);
        Drawable unlikeIcon = mContext.getResources().getDrawable(R.mipmap.heart_icon);
        holder.like_num.setCompoundDrawablesWithIntrinsicBounds(null, videoCase.ifLike?likeIcon:unlikeIcon, null, null);

        holder.like_num.setOnClickListener(v -> {
            //本地数值上先更新
            if(!videoCase.ifLike) {
                //点赞
                holder.like_num.setText(String.valueOf(++videoCase.likeNum));
                Animation animation = new AlphaAnimation(1.0f, 0.0f);
                animation.setDuration(300);
                holder.like_num.startAnimation(animation);
                holder.like_num.setCompoundDrawablesWithIntrinsicBounds(null, likeIcon, null, null);
                videoCase.ifLike = true;
            }
            else{
                //取消点赞
                holder.like_num.setText(String.valueOf(--videoCase.likeNum));
                Animation animation = new AlphaAnimation(1.0f, 0.0f);
                animation.setDuration(300);
                holder.like_num.startAnimation(animation);
                holder.like_num.setCompoundDrawablesWithIntrinsicBounds(null, unlikeIcon, null, null);
                videoCase.ifLike = false;
            }
            //通知服务器
            RequestBody requestBody = new FormBody.Builder()
                    .add("account", Constant.currentUser.getAccount())
                    .add("videoID", videoCase.getID())
                    .add("flag", videoCase.ifLike?"1":"0")
                    .build();
            String url = HttpUtil.rootUrl +"getALike";
            HttpUtil.sendPostRequest(url, requestBody, new Callback(){
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }
            });

        });

        holder.add_follow.setVisibility(videoCase.ifFollow?View.INVISIBLE:View.VISIBLE);
        holder.add_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.add_follow.setVisibility(View.INVISIBLE);
                for(VideoCase video:videoList){
                    //所有该作者的关注都设为是
                    if(video.getAuthorAccount().equals(videoCase.getAuthorAccount()))
                        video.ifFollow = true;
                }
                videoCase.ifFollow = false;
                RequestBody requestBody = new FormBody.Builder()
                        .add("account", Constant.currentUser.getAccount())
                        .add("toFollow", videoCase.getAuthorAccount())
                        .add("flag","1")
                        .build();
                String url = HttpUtil.rootUrl +"follow";
                HttpUtil.sendPostRequest(url, requestBody, new Callback(){
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    }

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public void addAll(List<VideoCase>list){
        synchronized (this) {
            if(null==videoList)
                videoList=list;
            else
                videoList.addAll(list);
            notifyDataSetChanged();
        }

    }

    public void replace(List<VideoCase>list){
        synchronized (this) {
            videoList=list;
        }
        notifyDataSetChanged();
    }

    public void setRemoveItemListener(RemoveItemListener removeItemListener) {
        this.removeItemListener = removeItemListener;
    }

    public interface RemoveItemListener {
        void removeItem(int podition);
    }

}
