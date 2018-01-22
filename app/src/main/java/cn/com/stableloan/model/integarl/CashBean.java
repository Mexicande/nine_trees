package cn.com.stableloan.model.integarl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/8/11.
 */

public class CashBean implements Serializable {

    /**
     * code : 200
     * message : 200
     * data : {"account":"","total":"0.000000","cashRecord":[{"type":"干嘛呢","expend":"6.000000","create_at":"1970-01-02 00:00:00","font":"#23BE13","number":"-6元"},{"type":"大哥大","income":"6.000000","create_at":"1970-01-02 00:00:00","font":"#FEB324","number":"+6元"}]}
     * error_code : 0
     * error_message :
     * time : 2017-08-11 18:15:32
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
         * account :
         * total : 0.000000
         * cashRecord : [{"type":"干嘛呢","expend":"6.000000","create_at":"1970-01-02 00:00:00","font":"#23BE13","number":"-6元"},{"type":"大哥大","income":"6.000000","create_at":"1970-01-02 00:00:00","font":"#FEB324","number":"+6元"}]
         */

        private String account;
        private String total;
        private List<CashRecordBean> cashRecord;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<CashRecordBean> getCashRecord() {
            return cashRecord;
        }

        public void setCashRecord(List<CashRecordBean> cashRecord) {
            this.cashRecord = cashRecord;
        }

        public static class CashRecordBean implements Serializable{
            /**
             * type : 干嘛呢
             * expend : 6.000000
             * create_at : 1970-01-02 00:00:00
             * font : #23BE13
             * number : -6元
             * income : 6.000000
             */
            private String remark;

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
            private String log_id;
            private String type;
            private String expend;
            private String create_at;
            private String font;
            private String number;
            private String income;

            public String getLog_id() {
                return log_id;
            }

            public void setLog_id(String log_id) {
                this.log_id = log_id;
            }

            private String status;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getExpend() {
                return expend;
            }

            public void setExpend(String expend) {
                this.expend = expend;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public String getFont() {
                return font;
            }

            public void setFont(String font) {
                this.font = font;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getIncome() {
                return income;
            }

            public void setIncome(String income) {
                this.income = income;
            }
        }
    }
}
