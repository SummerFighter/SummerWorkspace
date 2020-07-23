package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.administrator.douyin.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import Controller.Constant;
import Controller.HttpUtil;
import butterknife.BindView;
import model.FragmentUserItem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import widget.CircleImageView;

/**
 *粉丝列表
 */
public class FansAdapter extends BaseRvAdapter<FragmentUserItem, FansAdapter.FansViewHolder> {

    public FansAdapter(Context context, List<FragmentUserItem> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(FansViewHolder holder, FragmentUserItem user, int position) {
        Glide.with(context)
                .load(user.getAvatarUrl())
                .into(holder.ivHead);
        holder.tvNickname.setText(user.getUsername());
        holder.tvFocus.setText(user.getSign());
        if(user.getSign().equals("已关注"))
            holder.tvFocus.setBackgroundResource(R.drawable.shape_round_halfwhite);
        holder.tvFocus.setOnClickListener(v -> {
            int toFocus=(holder.tvFocus.getText().toString().equals("关注")? 1:0);
            if (toFocus==1) {
                holder.tvFocus.setText("已关注");
                user.setSign("已关注");
                holder.tvFocus.setBackgroundResource(R.drawable.shape_round_halfwhite);
            } else {
                holder.tvFocus.setText("关注");
                user.setSign("关注");
                holder.tvFocus.setBackgroundResource(R.drawable.shape_round_red);
            }
            RequestBody requestBody = new FormBody.Builder()
                    .add("account", Constant.currentUser.getAccount())
                    .add("toFollow", user.getAccount())
                    .add("flag",toFocus+"")
                    .build();
            HttpUtil.sendPostRequest(HttpUtil.rootUrl+"follow", requestBody, new Callback(){
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                }
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }
            });
        });

    }

    @NonNull
    @Override
    public FansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fans, parent, false);
        return new FansViewHolder(view);
    }

    public class FansViewHolder extends BaseRvViewHolder {
        @BindView(R.id.iv_head)
        CircleImageView ivHead;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_focus)
        TextView tvFocus;

        public FansViewHolder(View itemView) {
            super(itemView);
        }
    }
}
