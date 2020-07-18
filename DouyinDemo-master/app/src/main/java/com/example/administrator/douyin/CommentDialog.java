package com.example.administrator.douyin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Controller.DataCreate;
import adapter.CommentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentDialog extends BaseBottomSheetDialog {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private CommentAdapter commentAdapter;
    private ArrayList<CommentBean> datas = new ArrayList<>();
    private View view;
    private int[] likeArray = new int[]{4919, 334};
    private String[] commentArray = new String[]{"我就说左脚踩右脚可以上天你们还不信！", "全是评论点赞，没人关注吗"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_comment, container);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentAdapter = new CommentAdapter(getContext(), datas);
        recyclerView.setAdapter(commentAdapter);

        loadData();
    }

    private void loadData() {
        //System.out.println(DataCreate.userList.size());
        for (int i = 0; i < DataCreate.userList.size(); i++) {
            CommentBean commentBean = new CommentBean();
            commentBean.setUserBean(DataCreate.userList.get(i));
            commentBean.setContent(commentArray[i]);
            commentBean.setLikeCount(likeArray[i]);
            datas.add(commentBean);
        }
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getHeight() {
        return getResources().getDisplayMetrics().heightPixels - 600;
    }
}