package cn.com.stableloan.bean.cash;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/11/8.
 */

public class CashFlowBean implements Serializable {
    /**
     * code : 0
     * message : sasa
     * error_code : 0
     * error_message : 2121
     * data : {"account":"123****qq.com","total":"1,000.00","cashrecord":[{"remark":"提现申请","status":"失败","number":"-10.00元","font":"#23BE13","create_at":"2017-11-06 12:55:44"}]}
     */
    private int code;
    private String message;
    private int error_code;
    private String error_message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * account : 123****qq.com
         * total : 1,000.00
         * cashrecord : [{"remark":"提现申请","status":"失败","number":"-10.00元","font":"#23BE13","create_at":"2017-11-06 12:55:44"}]
         */

        private String account;
        private String total;
        private List<CashrecordBean> cashrecord;

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

        public List<CashrecordBean> getCashrecord() {
            return cashrecord;
        }

        public void setCashrecord(List<CashrecordBean> cashrecord) {
            this.cashrecord = cashrecord;
        }

        public static class CashrecordBean implements Serializable {
            /**
             * remark : 提现申请
             * status : 失败
             * number : -10.00元
             * font : #23BE13
             * create_at : 2017-11-06 12:55:44
             */

            private String remark;
            private String status;
            private String number;
            private String font;
            private String create_at;

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getFont() {
                return font;
            }

            public void setFont(String font) {
                this.font = font;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }
        }
    }
}
