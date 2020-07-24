package model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 聊天消息类
 */
public class ChatMessage {



    private boolean isOfficial;
    private String title;
    private String time;
    private String description;
    private String icon;
    private String reciprocalAccount;

    public ChatMessage(){
        super();
    }

    public ChatMessage(JSONObject messageJSON) throws JSONException {
        title=messageJSON.getString("username");
        time=messageJSON.getString("time");
        description=messageJSON.getString("description");
        icon=messageJSON.getString("userAvatarUrl");
        reciprocalAccount= messageJSON.getString("fromAccount");
        isOfficial=title.equals("小麦视频官方")?true:false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isOfficial() {
        return isOfficial;
    }

    public void setOfficial(boolean official) {
        isOfficial = official;
    }

    @Override
    public String toString() {
        return "Message{" +
                "isOfficial=" + isOfficial +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

}
