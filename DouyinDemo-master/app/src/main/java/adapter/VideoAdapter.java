package adapter;

import android.content.Context;
import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.administrator.douyin.DetailActivity;
import com.example.administrator.douyin.R;

import java.util.ArrayList;
import java.util.List;

import Controller.Constant;
import model.VideoCase;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {
    private Context mContext;
    private OnItemClickListener mListener;
    //使用两个List便于Bundle传输
    private List<String> coverURLs;
    private List<Integer> likeNumbers;

    public VideoAdapter(Context context, List<String> coverURLs,List<Integer> likeNumbers, OnItemClickListener listener) {
        mContext = context;
        this.coverURLs=(coverURLs==null?new ArrayList<>():coverURLs);
        this.likeNumbers=(likeNumbers==null?new ArrayList<>():likeNumbers);
        if(likeNumbers.size()<coverURLs.size()){
            //如果likeNum不足，自动补0
            for(int i=likeNumbers.size()-1;i<coverURLs.size();i++){
                likeNumbers.add(0);
            }
        }
        mListener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_video, parent, false);
        view.setTransitionName(DetailActivity.TRANSITION_NAME);//转到的Activity名
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        Glide.with(mContext)
                .load(coverURLs.get(position))
                .into(holder.video_thumb);
        holder.video_thumb_likeNum.setText(String.valueOf(likeNumbers.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coverURLs.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        AppCompatImageView video_thumb;
        AppCompatTextView video_thumb_likeNum;

        public VideoHolder(@NonNull final View itemView) {
            super(itemView);
            video_thumb=itemView.findViewById(R.id.video_thumb);
            video_thumb_likeNum=itemView.findViewById(R.id.video_thumb_likeNum);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }
}
