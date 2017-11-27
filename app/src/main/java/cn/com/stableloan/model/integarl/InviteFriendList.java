package cn.com.stableloan.model.integarl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/8/25.
 */

public class InviteFriendList implements Serializable {

    /**
     * code : 200
     * message : 200
     * data : {"inviteCode":"1708118ho41gcj","qrCode":"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","inviteLog":[{"mobile":"15210842632","status":"已注册","money":"￥0.00"},{"mobile":"13046370000","status":"已注册","money":"￥0.00"},{"mobile":"18519893815","status":"已注册","money":"￥0.00"},{"mobile":"18201418667","status":"邀请中","money":"￥0.00"}]}
     * error_code : 0
     * error_message :
     * time : 2017-08-25 12:28:55
     */

    private int code;
    private int message;
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

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
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
         * inviteCode : 1708118ho41gcj
         * qrCode : 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
         * inviteLog : [{"mobile":"15210842632","status":"已注册","money":"￥0.00"},{"mobile":"13046370000","status":"已注册","money":"￥0.00"},{"mobile":"18519893815","status":"已注册","money":"￥0.00"},{"mobile":"18201418667","status":"邀请中","money":"￥0.00"}]
         */

        private String inviteCode;
        private String qrCode;
        private List<InviteLogBean> inviteLog;

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public List<InviteLogBean> getInviteLog() {
            return inviteLog;
        }

        public void setInviteLog(List<InviteLogBean> inviteLog) {
            this.inviteLog = inviteLog;
        }

        public static class InviteLogBean implements Serializable{
            /**
             * mobile : 15210842632
             * status : 已注册
             * money : ￥0.00
             */

            private String invitePhone;
            private String status;
            private String money;

            public String getInvitePhone() {
                return invitePhone;
            }

            public void setInvitePhone(String invitePhone) {
                this.invitePhone = invitePhone;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}
