package cn.com.stableloan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/8/9.
 */

public class CashBean implements Serializable{
    /**
     * code : 1
     * message :
     * data : {"total":122,"account":"49924234@qq.com","cashRecord":[{"title":"邀请好友","number":"20","create_time":"1997-7-7 12:11:11"},{"title":"邀请好友","number":"20","create_time":"1997-7-7 12:11:11"}]}
     * error_code : 0
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

    public static class DataBean implements Serializable{
        /**
         * total : 122
         * account : 49924234@qq.com
         * cashRecord : [{"title":"邀请好友","number":"20","create_time":"1997-7-7 12:11:11"},{"title":"邀请好友","number":"20","create_time":"1997-7-7 12:11:11"}]
         */

        private int total;
        private String account;
        private List<CashRecordBean> cashRecord;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public List<CashRecordBean> getCashRecord() {
            return cashRecord;
        }

        public void setCashRecord(List<CashRecordBean> cashRecord) {
            this.cashRecord = cashRecord;
        }

        public static class CashRecordBean implements Serializable{
            /**
             * title : 邀请好友
             * number : 20
             * create_time : 1997-7-7 12:11:11
             */

            private String title;
            private int number;
            private String create_time;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
