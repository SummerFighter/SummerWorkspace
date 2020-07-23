package adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.administrator.douyin.R;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import model.CommentBean;

public class CommentAdapter extends BaseRvAdapter<CommentBean, CommentAdapter.CommentViewHolder> {

    public CommentAdapter(Context context, List<CommentBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(CommentViewHolder holder, CommentBean commentBean, int position) {
        Glide.with(context)
                .load(commentBean.getAuthorAvatarUrl())
                .into(holder.ivHead);
        holder.tvNickname.setText(commentBean.getAuthorName());
        holder.tvContent.setText(commentBean.getContent());
        //Drawable top1 = context.getResources().getDrawable(R.mipmap.heart_icon);
        //Drawable top2 = context.getResources().getDrawable(R.mipmap.heart_icon2);

        /*
        if(commentBean.isLiked()){
            holder.tvLikecount.setCompoundDrawablesWithIntrinsicBounds(null, top2 , null, null);
        }else{
            holder.tvLikecount.setCompoundDrawablesWithIntrinsicBounds(null, top1 , null, null);
        }
        holder.tvLikecount.setOnClickListener(v -> {
            if (!commentBean.isLiked()) {
                holder.tvLikecount.setText(commentBean.getLikeCount()+1+"");
                Animation animation=new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(300);
                holder.tvLikecount.startAnimation(animation);
                holder.tvLikecount.setCompoundDrawablesWithIntrinsicBounds(null, top2 , null, null);
                //点赞
            } else {
                holder.tvLikecount.setText(commentBean.getLikeCount()-1+"");
                Animation animation=new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(300);
                holder.tvLikecount.startAnimation(animation);
                holder.tvLikecount.setCompoundDrawablesWithIntrinsicBounds(null, top1 , null, null);
                //取消点赞
            }
            commentBean.setLiked(!commentBean.isLiked());
        });
        */
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    public class CommentViewHolder extends BaseRvViewHolder {
        @BindView(R.id.iv_head)
        CircleImageView ivHead;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.like)
        TextView tvLikecount;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }
}