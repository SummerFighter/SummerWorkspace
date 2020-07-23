package model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

//用于显示在FansFragment中的User
public class FragmentUserItem implements Parcelable {
    private String account; // 账号
    private String username; // 昵称
    private String avatarUrl; //头像服务器地址
    private String sign;//关注/已关注

    public FragmentUserItem(){
        super();
    }

    public FragmentUserItem(JSONObject jsonObject) throws JSONException {
        super();
        this.account = jsonObject.getString("account");
        this.username = jsonObject.getString("username");
        this.avatarUrl = jsonObject.getString("avatarUrl");
    }

    public FragmentUserItem(String account,String username,String avatarUrl,String sign){
        super();
        this.account=account;
        this.username=username;
        this.avatarUrl=avatarUrl;
        this.sign=sign;
    }

    public String getAccount() {
        return account;
    }
    public String getUsername() {
        return username;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public String getSign() {
        return sign;
    }

    public void setAccount(String account) {
        this.account=account;
    }
    public void setUsername(String username) {
        this.username=username;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl=avatarUrl;
    }
    public void setSign(String sign) {
        this.sign=sign;
    }

    public static final Creator<FragmentUserItem> CREATOR = new Creator<FragmentUserItem>() {
        @Override
        public FragmentUserItem createFromParcel(Parcel in) {
            FragmentUserItem f=new FragmentUserItem();
            f.setAccount(in.readString());
            f.setUsername(in.readString());
            f.setAvatarUrl(in.readString());
            f.setSign(in.readString());
            return f;
        }

        @Override
        public FragmentUserItem[] newArray(int size) {
            return new FragmentUserItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(account);
        parcel.writeString(username);
        parcel.writeString(avatarUrl);
        parcel.writeString(sign);
    }
}
