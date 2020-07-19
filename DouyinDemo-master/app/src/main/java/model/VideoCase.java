package model;

public class VideoCase {
    private String _ID;//视频ID
    private String _title;//标题
    private String _description;//视频描述
    private String _url;//视频地址
    private String _coverUrl;//封面地址

    private String _authorAccount;//作者账号


    public int likeNum;//点赞数量
    public int commentNum;//评论数量


    public VideoCase(String ID, String title, String description,String url,
                     String coverUrl, String authorAccount,
                     int likeNum,int commentNum){
        this._ID=ID;
        this._title=title;
        this._description=description;
        this._url=url;
        this._coverUrl=coverUrl;

        this._authorAccount = authorAccount;

        this.likeNum = likeNum;
        this.commentNum = commentNum;
    }

    public String getID() {
        return _ID;
    }
    public String getTitle() {
        return _title;
    }
    public String getDescription() {
        return _description;
    }
    public String getURL() {
        return _url;
    }
    public String getCoverURL(){return _coverUrl;}

    public String getAuthorAccount(){return _authorAccount;}

}
