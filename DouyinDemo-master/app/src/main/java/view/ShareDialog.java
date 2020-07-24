package view;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administrator.douyin.BaseBottomSheetDialog;
import com.example.administrator.douyin.ChatActivity;
import com.example.administrator.douyin.EditMyinfo;
import com.example.administrator.douyin.MainActivity;
import com.example.administrator.douyin.R;
import com.example.administrator.douyin.ReportActivity;
import com.example.administrator.douyin.SearchActivity;

import java.util.ArrayList;
import adapter.ShareAdapter;
import bean.ShareBean;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
*分享界面
 */
public class ShareDialog extends BaseBottomSheetDialog {

    @BindView(R.id.rv_share)
    RecyclerView rvShare;
    private ShareAdapter shareAdapter;
    private View view;
    private ArrayList<ShareBean> shareBeans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_share, container);
        Button reportbtn = (Button) view.findViewById(R.id.report);
        String videoID=this.getTag();
        reportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                intent.putExtra("videoID",videoID);
                startActivity(intent);
            }
        });
        ButterKnife.bind(this, view);
        init();

        return view;
    }



    private void init() {


        rvShare.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        shareAdapter = new ShareAdapter(getContext(), shareBeans);
        rvShare.setAdapter(shareAdapter);


        setShareDatas();

    }

    private void setShareDatas() {
        shareBeans.add(new ShareBean(R.string.icon_friends, "朋友圈", R.color.color_wechat_iconbg));
        shareBeans.add(new ShareBean(R.string.icon_wechat, "微信", R.color.color_wechat_iconbg));
        shareBeans.add(new ShareBean(R.string.icon_qq, "QQ", R.color.color_qq_iconbg));
        shareBeans.add(new ShareBean(R.string.icon_qq_space, "QQ空间", R.color.color_qqzone_iconbg));
        shareBeans.add(new ShareBean(R.string.icon_weibo, "微博", R.color.color_weibo_iconbg));


        shareAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getHeight() {
        return dp2px(getContext(), 355);
    }

}
