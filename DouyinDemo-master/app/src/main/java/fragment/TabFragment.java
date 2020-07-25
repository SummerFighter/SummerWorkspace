package fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administrator.douyin.DetailActivity;
import com.example.administrator.douyin.R;
import com.example.administrator.douyin.SearchActivity;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import adapter.VideoAdapter;
import skeleton.Skeleton;
import skeleton.SkeletonScreen;
/*
* 作品、动态、我的喜欢
* */
public class TabFragment extends Fragment implements VideoAdapter.OnItemClickListener {

    //private SkeletonScreen mSkeletonScreen;
    private VideoAdapter mVideoAdapter;
    private RecyclerView mRecyclerView;
    private String videoSourceKind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        List<String> s = bundle.getStringArrayList("imageURL");
        List<Integer> i=bundle.getIntegerArrayList("likeNum");
        videoSourceKind=bundle.getString("rootActivity");
        mVideoAdapter = new VideoAdapter(getContext(),s,i,this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mVideoAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("rootActivity",videoSourceKind);//提示启动界面
        intent.putExtra("index", position);//设置位置
        startActivity(intent);
    }
}
