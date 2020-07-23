package com.example.administrator.douyin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import Controller.Constant;
import Controller.HttpUtil;
import adapter.CommentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.CommentBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentDialog extends BaseBottomSheetDialog {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private CommentAdapter commentAdapter;
    private ArrayList<CommentBean> datas = new ArrayList<>();//所有评论的内容
    private String videoID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_comment, container);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = this.getArguments();
        assert bundle != null;
        videoID = bundle.getString("videoID", "0");
        init(view);

        EditText comment = view.findViewById(R.id.comment);
        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //发布评论
                String commentContent=comment.getText().toString();
                if(null==commentAdapter||commentContent.equals(""))
                    return;
                String upperID="0";//暂时没有二级评论，上级评论ID暂时用0
                RequestBody body = new FormBody.Builder()
                        .add("account", Constant.currentUser.getAccount())
                        .add("videoID",videoID)
                        .add("upper_id",upperID)
                        .add("content",commentContent)
                        .build();
                HttpUtil.sendPostRequest("http://47.104.232.108/setComment", body, new Callback() {

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        int oldCommentNum=datas.size();
                        final String responseData = response.body().string();
                        try {
                            JSONObject commentIDJSON=new JSONObject(responseData);
                            String commentID=commentIDJSON.getString("comment_id");
                            CommentBean c=new CommentBean(commentID,commentContent,upperID,
                                    Constant.currentUser.getUsername(),Constant.currentUser.getAccount(),Constant.currentUser.getAvatarUrl());
                            datas.add(c);
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    comment.setText("");
                                    commentAdapter.notifyItemRangeInserted(oldCommentNum,1);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }
                });
            }
        });

        return view;
    }

    private void init(View view) {
        RequestBody body = new FormBody.Builder()
                .add("videoID", videoID)
                .build();
        HttpUtil.sendPostRequest("http://47.104.232.108/videoComments", body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("聊天框", "内容加载失败 ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseData = response.body().string();
                try {
                    JSONArray commentJsonArray =new JSONObject(responseData).getJSONArray("comment");
                    for(int i=0;i<commentJsonArray.length();i++){
                        JSONObject videoJSON=commentJsonArray.getJSONObject(i);
                        CommentBean c=new CommentBean(videoJSON);
                        datas.add(c);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                view.post(new Runnable() {
                    public void run() {
                        commentAdapter=new CommentAdapter(getContext(),datas);
                        recyclerView.setAdapter(commentAdapter);
                    }
                });
            }
        });
    }


    @Override
    protected int getHeight() {
        return getResources().getDisplayMetrics().heightPixels - 600;
    }
}