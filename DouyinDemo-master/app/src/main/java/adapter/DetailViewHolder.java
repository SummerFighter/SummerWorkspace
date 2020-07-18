package adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administrator.douyin.R;

public class DetailViewHolder extends RecyclerView.ViewHolder {
    ImageView img_thumb;
    VideoView videoView;
    ImageView img_play;
    TextView like_num;
    TextView comment_num;

    RelativeLayout rootView;

    public <T extends View> T getView(int viewId) {
        return itemView.findViewById(viewId);
    }

    public DetailViewHolder(@NonNull View itemView) {
        super(itemView);
        img_thumb = itemView.findViewById(R.id.img_thumb);
        videoView = itemView.findViewById(R.id.video_view);
        img_play = itemView.findViewById(R.id.img_play);
        like_num = itemView.findViewById(R.id.like_num);
        comment_num = itemView.findViewById(R.id.comment_num);
        rootView = itemView.findViewById(R.id.root_view);
    }

}
