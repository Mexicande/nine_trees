package cn.com.stableloan.bean;

/**
 * Created by apple on 2017/5/29.
 */

public class PlarformInfo {

    private String isSuccess;

    private platform platform;

    private String msg;

    public static class platform{

        private String platformname;//平台名称
        private String Introduction;//平台介绍

        private String logo;        //平台介绍
        private String online_time; //成立时间
        private String amount;      //月均放款量
        private String condition;   //申请条件
        private String limit;        //申请额度

        public String getPlatformname() {
            return platformname;
        }

        public void setPlatformname(String platformname) {
            this.platformname = platformname;
        }

        public String getIntroduction() {
            return Introduction;
        }

        public void setIntroduction(String introduction) {
            Introduction = introduction;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getOnline_time() {
            return online_time;
        }

        public void setOnline_time(String online_time) {
            this.online_time = online_time;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        @Override
        public String toString() {
            return "platform{" +
                    "platformname='" + platformname + '\'' +
                    ", Introduction='" + Introduction + '\'' +
                    ", logo='" + logo + '\'' +
                    ", online_time='" + online_time + '\'' +
                    ", amount='" + amount + '\'' +
                    ", condition='" + condition + '\'' +
                    ", limit='" + limit + '\'' +
                    '}';
        }
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public PlarformInfo.platform getPlatform() {
        return platform;
    }

    public void setPlatform(PlarformInfo.platform platform) {
        this.platform = platform;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "PlarformInfo{" +
                "isSuccess='" + isSuccess + '\'' +
                ", platform=" + platform +
                ", msg='" + msg + '\'' +
                '}';
    }
}
