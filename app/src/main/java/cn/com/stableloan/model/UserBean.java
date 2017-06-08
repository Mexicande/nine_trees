package cn.com.stableloan.model;

/**
 * Created by apple on 2017/6/8.
 */

public class UserBean {

    /**
     * userphone : 15622222222
     * identity : 2
     * nickname : UGG干活
     * isSuccess : true
     */

    private String userphone;
    private String identity;
    private String nickname;
    private boolean isSuccess;

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userphone='" + userphone + '\'' +
                ", identity='" + identity + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
