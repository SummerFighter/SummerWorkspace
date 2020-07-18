package com.example.administrator.douyin;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import Controller.DataCreate;
import adapter.FansAdapter;
import butterknife.BindView;

public class FansFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private FansAdapter fansAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_fans;
    }

    @Override
    protected void init() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fansAdapter = new FansAdapter(getContext(), DataCreate.userList);
        recyclerView.setAdapter(fansAdapter);
    }

}
