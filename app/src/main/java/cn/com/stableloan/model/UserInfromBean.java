package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/20.
 */

public class UserInfromBean implements Serializable{

    /**
     * code : 200
     * message : 200
     * data : {"nickname":"安稳钱包16514572","userphone":"18500634223","identity":1,"token":"473eb8ac119f69fff61fa60dfedc71a8"}
     * error_code : 0
     * error_message :
     * time : 2017-07-20 16:48:57
     */

    private int code;
    private String message;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
         * nickname : 安稳钱包16514572
         * userphone : 18500634223
         * identity : 1
         * token : 473eb8ac119f69fff61fa60dfedc71a8
         */

        private String nickname;
        private String userphone;
        private int identity;
        private String token;

        private String credits;  //integral
        private String total;   //amount

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

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "nickname='" + nickname + '\'' +
                    ", userphone='" + userphone + '\'' +
                    ", identity=" + identity +
                    ", token='" + token + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "UserInfromBean{" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                ", error_code=" + error_code +
                ", error_message='" + error_message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
