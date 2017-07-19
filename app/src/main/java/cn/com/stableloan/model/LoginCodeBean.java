package cn.com.stableloan.model;

/**
 * Created by apple on 2017/7/18.
 */

public class LoginCodeBean {

    /**
     * code : 200
     * message :
     * data : {"nickname":"安稳4568812","userphone":"15849275060","identity":"0","token":"sadfasdfasfasdd456456"}
     * sign :
     * error_code :
     * error_message :
     */

    private String code;
    private String message;
    private DataBean data;
    private String sign;
    private String error_code;
    private String error_message;

    @Override
    public String toString() {
        return "LoginCodeBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", sign='" + sign + '\'' +
                ", error_code='" + error_code + '\'' +
                ", error_message='" + error_message + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public static class DataBean {
        /**
         * nickname : 安稳4568812
         * userphone : 15849275060
         * identity : 0
         * token : sadfasdfasfasdd456456
         */

        private String data;
        private String sign;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "data='" + data + '\'' +
                    ", sign='" + sign + '\'' +
                    '}';
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
