package cn.com.stableloan.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/5/26.
 */

public class UserBean implements Serializable{
    private String isSuccess;
    private String nickname;
    private String userphone;
    private String identity;

    public UserBean() {
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

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

    @Override
    public String toString() {
        return "UserBean{" +
                "isSuccess='" + isSuccess + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userphone='" + userphone + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }
}
