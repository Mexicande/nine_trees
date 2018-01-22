package cn.com.stableloan.model.integarl;

import java.io.Serializable;

import cn.com.stableloan.model.UserBean;

/**
 * Created by apple on 2017/8/16.
 */

public class Personal implements Serializable {

    /**
     * code : 200
     * message : 200
     * data : {"userphone":"15600575837","identity":3,"nickname":"就放假放假","credits":0,"total":0}
     * error_code : 0
     * error_message :
     * time : 2017-08-16 14:51:30
     */

    private int code;
    private String message;
    private UserBean data;
    private int error_code;
    private String error_message;
    private String time;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static class DataBean implements Serializable{
        /**
         * userphone : 15600575837
         * identity : 3
         * nickname : 就放假放假
         * credits : 0
         * total : 0
         */

        private String userphone;
        private int identity;
        private String nickname;
        private int credits;
        private int total;

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

        public int getCredits() {
            return credits;
        }

        public void setCredits(int credits) {
            this.credits = credits;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
