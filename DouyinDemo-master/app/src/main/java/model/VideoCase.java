package model;

public class VideoCase {
    private String _title;
    private String _description;
    private String _url;

    public VideoCase(String title, String description,String url){
        this._title=title;
        this._description=description;
        this._url=url;

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
