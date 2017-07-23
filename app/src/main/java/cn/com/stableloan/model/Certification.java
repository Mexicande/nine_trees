package cn.com.stableloan.model;

/**
 * Created by apple on 2017/7/21.
 */

public class Certification {


    /**
     * code : 200
     * message :
     * data : {"TaobaoStatus":1,"CapStatus":1,"AliStatus":1}
     * error_code : 1
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

    public static class DataBean {
        /**
         * TaobaoStatus : 1
         * CapStatus : 1
         * AliStatus : 1
         */

        private int TaobaoStatus;
        private int CapStatus;
        private int AliStatus;

        public int getTaobaoStatus() {
            return TaobaoStatus;
        }

        public void setTaobaoStatus(int TaobaoStatus) {
            this.TaobaoStatus = TaobaoStatus;
        }

        public int getCapStatus() {
            return CapStatus;
        }

        public void setCapStatus(int CapStatus) {
            this.CapStatus = CapStatus;
        }

        public int getAliStatus() {
            return AliStatus;
        }

        public void setAliStatus(int AliStatus) {
            this.AliStatus = AliStatus;
        }
    }
}
