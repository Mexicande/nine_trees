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
    private String token;
    private String credits;  //integral
    private String total;   //amount
    private String vip;
    public UserBean() {
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public UserBean( String userphone,  String token, String credits, String total) {
        this.userphone = userphone;
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
                ", token='" + token + '\'' +
                ", credits='" + credits + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
