package model;

public class VideoCase {
    private String _title;
    private String _description;
    private String _url;

    public int likeNum;
    public int commentNum;


    public VideoCase(String title, String description,String url,
                     int likeNum,int commentNum){
        this._title=title;
        this._description=description;
        this._url=url;

        this.likeNum = likeNum;
        this.commentNum = commentNum;
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

}
