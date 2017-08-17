package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/6/8.
 */

public class UserBean implements Serializable {

    /**
     * userphone : 15622222222
     * identity : 2
     * nickname : UGG干活
     * isSuccess : true
     * integral  积分
     * amount   总金额
     */

    private String userphone;
    private int identity;
    private String nickname;
    private String token;

    private String credits;  //integral
    private String total;   //amount

    public UserBean() {
    }

    public UserBean(String userphone, int identity, String nickname, String token, String credits, String total) {
        this.userphone = userphone;
        this.identity = identity;
        this.nickname = nickname;
        this.token = token;
        this.credits = credits;
        this.total = total;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userphone='" + userphone + '\'' +
                ", identity=" + identity +
                ", nickname='" + nickname + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
