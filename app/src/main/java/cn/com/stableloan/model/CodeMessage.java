package cn.com.stableloan.model;

/**
 * Created by apple on 2017/7/17.
 */

public class CodeMessage {

    /**
     * code : 200
     * message :
     * data : {"status":"1","token":"dasfsadfasdfsadfdasfaskakflka"}
     * error_code :
     * error_message :
     */

    private String code;
    private String message;
    private DataBean data;
    private String error_code;
    private String error_message;

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
         * status : 1
         * token : dasfsadfasdfsadfdasfaskakflka
         */

        private String status;
        private String token;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
