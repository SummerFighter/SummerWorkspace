package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.administrator.douyin.R;

import java.util.List;

import Controller.Constant;
import model.ChatMessage;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{
    Context context;
    List<ChatMessage> mData;
    //点击事件监听
    private final ListItemClickListener mOnClickListener;

    public ChatAdapter(Context context, List<ChatMessage> data, ListItemClickListener listener){
        this.context=context;
        mData = data;
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_chater;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        ChatMessage message = mData.get(position);
        myViewHolder.view_tv_title.setText(message.getTitle());
        myViewHolder.view_tv_description.setText(message.getDescription());
        myViewHolder.view_tv_time.setText(message.getTime());
        Glide.with(context)
                .load(message.getIcon())
                .into(myViewHolder.view_iv_avator);
    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //title
        private final TextView view_tv_title;
        private final TextView view_tv_description;
        private final TextView view_tv_time;
        private final ImageView view_iv_avator;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            //名称，描述，时间
            view_tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            view_tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            view_tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            //头像
            view_iv_avator = (ImageView) itemView.findViewById(R.id.iv_avatar);

            itemView.setOnClickListener(this);
        }
        /*
                    public void updateUI(ChatMessage message){
                        //设置名称，描述，时间
                        view_tv_title.setText(message.getTitle());
                        view_tv_description.setText(message.getDescription());
                        view_tv_time.setText(message.getTime());
                        //设置头像

                        if(message.getIcon().equals("TYPE_ROBOT")) {
                            view_iv_avator.setImageResource(R.drawable.session_robot);
                        } else if(message.getIcon().equals("TYPE_GAME")){
                            view_iv_avator.setImageResource(R.drawable.icon_micro_game_comment);
                        } else if(message.getIcon().equals("TYPE_SYSTEM")){
                            view_iv_avator.setImageResource(R.drawable.session_system_notice);
                        } else if(message.getIcon().equals("TYPE_USER")){
                            view_iv_avator.setImageResource(R.drawable.icon_girl);
                        } else if(message.getIcon().equals("TYPE_STRANGER")){
                            view_iv_avator.setImageResource(R.drawable.session_stranger);
                        }

                    }
        */
        @Override
        public void onClick(View v) {
            System.out.println();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(view_tv_title.getText().toString());
            }
        }

    }
    public interface ListItemClickListener {
        void onListItemClick(String chat_target);

    }
}
