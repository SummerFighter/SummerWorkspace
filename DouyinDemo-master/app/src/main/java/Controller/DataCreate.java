package Controller;



import com.example.administrator.douyin.CommentBean;
import com.example.administrator.douyin.R;
import com.example.administrator.douyin.VideoBean;

import java.util.ArrayList;

/**
 * 本地创建数据模拟服务器-TODO
 */
public class DataCreate {
    public static ArrayList<VideoBean> datas = new ArrayList<>();
    public static ArrayList<VideoBean.UserBean> userList = new ArrayList<>();
    public static ArrayList<CommentBean> comments = new ArrayList<>();

    public static void initData() {

        VideoBean videoBeanOne = new VideoBean();
        videoBeanOne.setCoverRes(R.mipmap.head);
        videoBeanOne.setContent("内容一");
        videoBeanOne.setVideoRes(R.raw.video_1);
        videoBeanOne.setDistance(7.9f);
        videoBeanOne.setFocused(false);
        videoBeanOne.setLiked(true);
        videoBeanOne.setLikeCount(226823);
        videoBeanOne.setCommentCount(3480);
        videoBeanOne.setShareCount(4252);

        VideoBean.UserBean userBeanOne = new VideoBean.UserBean();
        userBeanOne.setUid(1);
        userBeanOne.setHead(R.mipmap.head3);
        userBeanOne.setNickName("用户一");
        userBeanOne.setSign("用户简介1");
        userBeanOne.setSubCount(119323);
        userBeanOne.setFocusCount(482);
        userBeanOne.setFansCount(32823);
        userBeanOne.setWorkCount(42);
        userBeanOne.setDynamicCount(42);
        userBeanOne.setLikeCount(821);

        userList.add(userBeanOne);
        videoBeanOne.setUserBean(userBeanOne);

        VideoBean videoBeanTwo = new VideoBean();
        videoBeanTwo.setCoverRes(R.mipmap.head);
        videoBeanTwo.setContent("内容二");
        videoBeanTwo.setVideoRes(R.raw.video_3);
        videoBeanTwo.setDistance(19.7f);
        videoBeanTwo.setFocused(true);
        videoBeanTwo.setLiked(false);
        videoBeanTwo.setLikeCount(1938230);
        videoBeanTwo.setCommentCount(8923);
        videoBeanTwo.setShareCount(5892);

        VideoBean.UserBean userBeanTwo = new VideoBean.UserBean();
        userBeanTwo.setUid(2);
        userBeanTwo.setHead( R.mipmap.header_icon_2);
        userBeanTwo.setNickName("用户二");
        userBeanTwo.setSign("简介二");
        userBeanTwo.setSubCount(20323234);
        userBeanTwo.setFocusCount(244);
        userBeanTwo.setFansCount(1938232);
        userBeanTwo.setWorkCount(123);
        userBeanTwo.setDynamicCount(123);
        userBeanTwo.setLikeCount(344);

        userList.add(userBeanTwo);
        videoBeanTwo.setUserBean(userBeanTwo);


        datas.add(videoBeanOne);
        datas.add(videoBeanTwo);

        CommentBean commentBeanOne = new CommentBean();
        commentBeanOne.setUserBean(DataCreate.userList.get(0));
        commentBeanOne.setContent("我就说左脚踩右脚可以上天你们还不信！");
        commentBeanOne.setLikeCount(4919);

        CommentBean commentBeanTwo = new CommentBean();
        commentBeanTwo.setUserBean(DataCreate.userList.get(1));
        commentBeanTwo.setContent("全是评论点赞，没人关注吗");
        commentBeanTwo.setLikeCount(334);

        comments.add(commentBeanOne);
        comments.add(commentBeanTwo);
    }
}
