package cn.com.laifenqicash.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/6/12.
 */

public class Product_Detail implements Serializable{

    /**
     * isSuccess : true
     * platform : {"pl_name":"现金贷","introduction":"额外无无","logo":"http://or2eh71ll.bkt.clouddn.com/149673103190117.jpg?e=1496734631&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:goW_StPkB50Sv6genGVhvnt__FI=","online_time":"2017-05-30","amount":"111","summary":""}
     */

    private boolean isSuccess;
    private PlatformBean platform;

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public PlatformBean getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformBean platform) {
        this.platform = platform;
    }

    public static class PlatformBean implements Serializable{
        /**
         * pl_name : 现金贷
         * introduction : 额外无无
         * logo : http://or2eh71ll.bkt.clouddn.com/149673103190117.jpg?e=1496734631&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:goW_StPkB50Sv6genGVhvnt__FI=
         * online_time : 2017-05-30
         * amount : 111
         * summary :
         */

        private String pl_name;
        private String introduction;
        private String logo;
        private String online_time;
        private String amount;
        private String summary;

        public String getPl_name() {
            return pl_name;
        }

        public void setPl_name(String pl_name) {
            this.pl_name = pl_name;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
