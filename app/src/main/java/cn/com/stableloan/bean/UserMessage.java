package cn.com.stableloan.bean;

/**
 * Created by apple on 2017/5/29.
 */

public class UserMessage {
    private String isSuccess;
    private String nickname;
    private String userphone;
    private String identity;
    private String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "isSuccess='" + isSuccess + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userphone='" + userphone + '\'' +
                ", identity='" + identity + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
