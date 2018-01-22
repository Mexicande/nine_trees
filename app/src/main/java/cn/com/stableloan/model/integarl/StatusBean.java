package cn.com.stableloan.model.integarl;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/17.
 */

public class StatusBean implements Serializable {

    /**
     * code : 200
     * message : 200
     * data : {"step1":"0","step3":"1","step2":"0"}
     * error_code : 0
     * error_message :
     * time : 2017-08-17 12:47:20
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

    public static class DataBean implements Serializable {
        /**
         * step1 : 0
         * step3 : 1
         * step2 : 0
         */

        private String step1;
        private String step3;
        private String step2;

        public String getStep1() {
            return step1;
        }

        public void setStep1(String step1) {
            this.step1 = step1;
        }

        public String getStep3() {
            return step3;
        }

        public void setStep3(String step3) {
            this.step3 = step3;
        }

        public String getStep2() {
            return step2;
        }

        public void setStep2(String step2) {
            this.step2 = step2;
        }
    }
}
