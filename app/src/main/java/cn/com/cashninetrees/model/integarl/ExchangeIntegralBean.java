package cn.com.cashninetrees.model.integarl;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/28.
 */

public class ExchangeIntegralBean implements Serializable {

    /**
     * code : 200
     * message :
     * data : {"credit":200}
     * error_code : 0
     * error_message :
     * time : 2017-08-18 15:24:09
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
         * credit : 200
         */

        private int credit;

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }
    }
}
