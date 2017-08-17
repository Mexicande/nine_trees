package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/16.
 */

public class BankInformation  implements Serializable{

    /**
     * code : 200
     * message : 200
     * data : {"isSuccess":"1","bank":{"debit":{"dnumber":"6228480210783906014","dname":"发发发","dperiod":"12121","dbank":"农业银行·金穗通宝卡(银联卡)","dphone":"18500634222"},"credit":{"cnumber":"6228480210783906014","cname":"li八年级","cperiod":"2121","cbank":"农业银行·金穗通宝卡(银联卡)","cphone":"15600575837"},"blass_time":"2017-07-09 09:16:45"},"status":"1"}
     * error_code : 0
     * error_message :
     * time : 2017-08-16 17:42:46
     */

    private int code;
    private int message;
    private Bank data;
    private int error_code;
    private String error_message;
    private String time;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public Bank getData() {
        return data;
    }

    public void setData(Bank data) {
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
         * isSuccess : 1
         * bank : {"debit":{"dnumber":"6228480210783906014","dname":"发发发","dperiod":"12121","dbank":"农业银行·金穗通宝卡(银联卡)","dphone":"18500634222"},"credit":{"cnumber":"6228480210783906014","cname":"li八年级","cperiod":"2121","cbank":"农业银行·金穗通宝卡(银联卡)","cphone":"15600575837"},"blass_time":"2017-07-09 09:16:45"}
         * status : 1
         */

        private String isSuccess;
        private Bank bank;
        private String status;

        public String getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(String isSuccess) {
            this.isSuccess = isSuccess;
        }

        public Bank getBank() {
            return bank;
        }

        public void setBank(Bank bank) {
            this.bank = bank;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


    }
}
