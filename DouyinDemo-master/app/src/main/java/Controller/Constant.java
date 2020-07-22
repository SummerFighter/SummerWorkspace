package Controller;

import java.util.ArrayList;
import java.util.List;

import entities.User;
import model.VideoCase;

public class Constant {
    public static User currentUser;
    public static List<VideoCase> videoDatas = new ArrayList<>();//主界面的
    public static List<VideoCase> searchVideoDatas = new ArrayList<>();//搜索结果的

    public static List<VideoCase> currentUserVideoWorks=new ArrayList<>();//当前用户的作品
    public static List<VideoCase> currentUserVideoLikes=new ArrayList<>();//当前用户的喜欢的作品
}
