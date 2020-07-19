package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.administrator.douyin.CommentDialog;
import com.example.administrator.douyin.R;
import com.example.administrator.douyin.VideoCache;

import java.util.List;

import model.VideoCase;

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
        return new DetailViewHolder(view);
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
