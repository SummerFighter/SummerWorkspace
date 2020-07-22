package adapter;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administrator.douyin.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Controller.Constant;
import Controller.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import widget.CircleImageView;

public class DetailViewHolder extends RecyclerView.ViewHolder {
    ImageView img_thumb;
    VideoView videoView;
    ImageView img_play;
    TextView video_title;
    TextView video_info;

    TextView like_num;
    TextView comment_num;

    RelativeLayout rootView;

    String author_account;
    String author_avatar_url;
    de.hdodenhof.circleimageview.CircleImageView avatar_view;

    ImageView add_follow;

    public <T extends View> T getView(int viewId) {
        return itemView.findViewById(viewId);
    }

    public DetailViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        img_thumb = itemView.findViewById(R.id.img_thumb);
        videoView = itemView.findViewById(R.id.video_view);
        img_play = itemView.findViewById(R.id.img_play);
        video_title= itemView.findViewById(R.id.video_title);
        video_info= itemView.findViewById(R.id.video_info);
        like_num = itemView.findViewById(R.id.like_num);
        comment_num = itemView.findViewById(R.id.comment_num);
        rootView = itemView.findViewById(R.id.root_view);
        avatar_view = itemView.findViewById(R.id.avatar_view);
        add_follow = itemView.findViewById(R.id.add_follow);

        add_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody requestBody = new FormBody.Builder()
                        .add("account", Constant.currentUser.getAccount())
                        .add("toFollow", author_account)
                        .add("flag","1")
                        .build();
                String url = HttpUtil.rootUrl +"follow";
                HttpUtil.sendPostRequest(url, requestBody, new Callback(){
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Looper.prepare();
                        Toast.makeText(context,"已关注",Toast.LENGTH_LONG).show();
                        Looper.loop();

                    }

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d("jiuming","adapter 关注onclick onfailure");
                    }
                });

            }
        });
    }

}
