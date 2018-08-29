package cn.com.cashninetrees.bean.cash;

import java.io.Serializable;

/**
 * Created by apple on 2017/11/8.
 */

public class CashFlowBean implements Serializable {

    /**
     * code : 0
     * message :
     * error_code : 0
     * error_message :
     * data : {"money":"-1,000.00","status":"被驳回","title":"用户提现","resault":"交易失败，金额未扣除；详情请咨询客服","created_at":"2017-11-08 00:00:00","updated_at":"2017-11-09 00:00:00","account":{"name":"支付宝","account":"123***@qq.com"}}
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
         * money : -1,000.00
         * status : 被驳回
         * title : 用户提现
         * resault : 交易失败，金额未扣除；详情请咨询客服
         * created_at : 2017-11-08 00:00:00
         * updated_at : 2017-11-09 00:00:00
         * account : {"name":"支付宝","account":"123***@qq.com"}
         */

        private String money;
        private String status;
        private String title;
        private String resault;
        private String created_at;
        private String updated_at;
        private AccountBean account;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getResault() {
            return resault;
        }

        public void setResault(String resault) {
            this.resault = resault;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public AccountBean getAccount() {
            return account;
        }

        public void setAccount(AccountBean account) {
            this.account = account;
        }

        public static class AccountBean implements Serializable{
            /**
             * name : 支付宝
             * account : 123***@qq.com
             */

            private String name;
            private String account;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }
        }
    }
}
