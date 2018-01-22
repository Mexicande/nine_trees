package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/17.
 */

public class CodeMessage implements Serializable{

    /**
     * code : 200
     * message :
     * data : {"status":"1","token":"dasfsadfasdfsadfdasfaskakflka"}
     * error_code :
     * error_message :
     */

    private int code;
    private String message;
    private DataBean data;
    private int error_code;
    private String error_message;

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

    @Override
    public String toString() {
        return "CodeMessage{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", error_code=" + error_code +
                ", error_message='" + error_message + '\'' +
                '}';
    }

    public static class DataBean implements Serializable {
        /**
         * status : 1
         * token : dasfsadfasdfsadfdasfaskakflka
         */
        private int is_insure;
        private String status;
        private String token;
        private String link;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getIs_insure() {
            return is_insure;
        }

        public void setIs_insure(int is_insure) {
            this.is_insure = is_insure;
        }

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

        @Override
        public String toString() {
            return "DataBean{" +
                    "status='" + status + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
