package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
        Glide.with(mContext)
                .load(videoList.get(position).getCoverURL())
                .into(holder.img_thumb);
        holder.video_title.setText(videoList.get(position).getTitle());
        holder.video_info.setText(videoList.get(position).getDescription());
        holder.like_num.setText(String.valueOf(videoList.get(position).likeNum));
        holder.comment_num.setText(String.valueOf(videoList.get(position).commentNum));
        holder.videoView.setVideoPath(VideoCache.getProxy(this.mContext).getProxyUrl(videoList.get(position).getURL()));
        holder.author_account = videoList.get(position).getAuthorAccount();
        holder.author_avatar_url = videoList.get(position).get_authorAvatar();

        RequestOptions userAvatarOptions = new RequestOptions()
                .signature(new ObjectKey(System.currentTimeMillis()));

        Glide.with(mContext)
                .applyDefaultRequestOptions(userAvatarOptions)
                .load(holder.author_avatar_url)
                .into(holder.avatar_view);

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
