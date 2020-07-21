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
    private EditText comment;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_comment, container);
        ButterKnife.bind(this, view);

        comment = view.findViewById(R.id.comment);
        button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //在这里上传评论
                //评论内容为comment.getText().toString()



            }
        });

        init();

        return view;
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentAdapter = new CommentAdapter(getContext(), DataCreate.comments);
        System.out.println(DataCreate.comments.size());
        recyclerView.setAdapter(commentAdapter);
    }


    @Override
    protected int getHeight() {
        return getResources().getDisplayMetrics().heightPixels - 600;
    }
}