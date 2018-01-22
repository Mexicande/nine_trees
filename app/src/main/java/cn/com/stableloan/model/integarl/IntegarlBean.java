package cn.com.stableloan.model.integarl;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/11.
 */

public class IntegarlBean implements Serializable{


    /**
     * code : 200
     * message : 0
     * data : {"status":{"status1":0,"status2":1,"status3":0,"status4":0},"offical":"积分越多，就能兑更多的钱哦","credits":0,"topCredits":13500}
     * error_code : 0
     * error_message :
     * time : 2017-11-24 19:01:29
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
         * status : {"status1":0,"status2":1,"status3":0,"status4":0}
         * offical : 积分越多，就能兑更多的钱哦
         * credits : 0
         * topCredits : 13500
         */

        private StatusBean status;
        private String offical;
        private int credits;
        private int topCredits;

        public StatusBean getStatus() {
            return status;
        }

        public void setStatus(StatusBean status) {
            this.status = status;
        }

        public String getOffical() {
            return offical;
        }

        public void setOffical(String offical) {
            this.offical = offical;
        }

        public int getCredits() {
            return credits;
        }

        public void setCredits(int credits) {
            this.credits = credits;
        }

        public int getTopCredits() {
            return topCredits;
        }

        public void setTopCredits(int topCredits) {
            this.topCredits = topCredits;
        }

        public static class StatusBean implements Serializable{
            /**
             * status1 : 0
             * status2 : 1
             * status3 : 0
             * status4 : 0
             */

            private int status1;
            private int status2;
            private int status3;
            private int status4;

            public int getStatus1() {
                return status1;
            }

            public void setStatus1(int status1) {
                this.status1 = status1;
            }

            public int getStatus2() {
                return status2;
            }

            public void setStatus2(int status2) {
                this.status2 = status2;
            }

            public int getStatus3() {
                return status3;
            }

            public void setStatus3(int status3) {
                this.status3 = status3;
            }

            public int getStatus4() {
                return status4;
            }

            public void setStatus4(int status4) {
                this.status4 = status4;
            }
        }
    }
}

