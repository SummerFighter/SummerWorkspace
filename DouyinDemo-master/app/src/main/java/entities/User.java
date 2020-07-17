package entities;

public class User {
    private String account; // 账号
    private String username; // 昵称
    private String balance; //余额
    private String avatarUrl; //头像服务器地址
    private String avatarPath; //头像本地地址

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
