package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

//绑定一条Comment，显示的数据为content，authorName，authorAvatarUrl,releaseTime
public class CommentBean {
    private String ID;
    private String content;//评论内容
    private String headID;//上一级评论id

    private String authorName;//评论者名
    private String authorAccount;//评论者账号
    private String authorAvatarUrl;//评论者头像地址

    private String releaseTime;//评论时间

    //本地添加评论的构造函数
    public CommentBean(String ID,String content,String headID,
                       String authorName,String authorAccount,String authorAvatarUrl){
        this.ID=ID;
        this.content=content;
        this.headID=headID;
        this.authorName=authorName;
        this.authorAccount=authorAccount;
        this.authorAvatarUrl=authorAvatarUrl;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        this.releaseTime=simpleDateFormat.format(date);
    }

    public CommentBean(JSONObject videoJSON) throws JSONException {
        ID = videoJSON.getString("id");
        content=videoJSON.getString("content");
        headID=videoJSON.getString("head_comment_id");
        authorName=videoJSON.getString("username");
        authorAccount=videoJSON.getString("account");
        authorAvatarUrl=videoJSON.getString("avatar_url");
        releaseTime=videoJSON.getString("release_time");
    }

    public String getID() {
        return ID;
    }
    public String getContent() {
        return content == null ? "" : content;
    }
    public String getHeadID() {
        return headID;
    }
    public String getAuthorName() {
        return authorName;
    }
    public String getAuthorAccount() {
        return authorAccount;
    }
    public String getAuthorAvatarUrl() {
        return authorAvatarUrl;
    }
    public String getReleaseTime() {
        return releaseTime;
    }

}
