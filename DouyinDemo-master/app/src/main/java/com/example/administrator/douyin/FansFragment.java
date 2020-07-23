package com.example.administrator.douyin;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import adapter.FansAdapter;
import butterknife.BindView;
import model.FragmentUserItem;

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
        Bundle bundle=this.getArguments();
        assert bundle != null;
        List<FragmentUserItem> fragmentUserItems = bundle.getParcelableArrayList("fragmentUserItems");
        fansAdapter = new FansAdapter(getContext(), fragmentUserItems);
        recyclerView.setAdapter(fansAdapter);
    }

}
