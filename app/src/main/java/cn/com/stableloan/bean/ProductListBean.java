package cn.com.stableloan.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/5/22.
 */

public class ProductListBean implements Serializable{

    /**
     * isSuccess : true
     * advertising : [{"pictrue":" "},{"pictrue":" "},{"pictrue":" "},{"pictrue":" "},{"pictrue":" "}]
     * product : [{"pname":"安稳贷","product_introduction":"风火雷电，急急如律令","min_algorithm":"1.0%","interest_algorithm":"0","average_time":"1小时"," platformname":"2345贷款平台","Introduction":"平台简介","logo":"平台logo","minimum_amount":"最小额度","maximum_amount":"最大额度","crowd":"上班白领","review":"急速审核","arrive":"支付宝，银行开，信用卡","actual_account":"10000","repayment":"等额本息","repayment_channels":"银行卡自动划缴","max_algorithm":" 1.7%","prepayment":"1"},{"pname":"安静贷","product_introduction":"风火雷电，急急如律令","min_algorithm":"1.0%","interest_algorithm":"0","average_time":"1小时"," platformname":"2345贷款平台","Introduction":"平台简介","logo":"平台logo","minimum_amount":"最小额度","maximum_amount":"最大额度","crowd":"上班白领","review":"急速审核","arrive":"支付宝，银行开，信用卡","actual_account":"10000","repayment":"等额本息","repayment_channels":"银行卡自动划缴","max_algorithm":" 1.7%","prepayment":"1"}]
     */

    private String isSuccess;
    private java.util.List<AdvertisingBean> advertising;
    private java.util.List<ProductBean> product;

    public static class AdvertisingBean {
        /**
         * pictrue :
         */

        private String pictrue;

        public String getPictrue() {
            return pictrue;
        }

        public void setPictrue(String pictrue) {
            this.pictrue = pictrue;
        }
    }

    public static class ProductBean {
        /**
         * pname : 安稳贷
         * product_introduction : 风火雷电，急急如律令
         * min_algorithm : 1.0%
         * interest_algorithm : 0
         * average_time : 1小时
         *  platformname : 2345贷款平台
         * Introduction : 平台简介
         * logo : 平台logo
         * minimum_amount : 最小额度
         * maximum_amount : 最大额度
         * crowd : 上班白领
         * review : 急速审核
         * arrive : 支付宝，银行开，信用卡
         * actual_account : 10000
         * repayment : 等额本息
         * repayment_channels : 银行卡自动划缴
         * max_algorithm :  1.7%
         * prepayment : 1
         */

        private String pname;
        private String product_introduction;
        private String min_algorithm;
        private String interest_algorithm;
        private String average_time;
        private String platformname;
        private String Introduction;
        private String logo;
        private String minimum_amount;
        private String maximum_amount;
        private String crowd;
        private String review;
        private String arrive;
        private String actual_account;
        private String repayment;
        private String repayment_channels;
        private String max_algorithm;
        private String prepayment;

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getProduct_introduction() {
            return product_introduction;
        }

        public void setProduct_introduction(String product_introduction) {
            this.product_introduction = product_introduction;
        }

        public String getMin_algorithm() {
            return min_algorithm;
        }

        public void setMin_algorithm(String min_algorithm) {
            this.min_algorithm = min_algorithm;
        }

        public String getInterest_algorithm() {
            return interest_algorithm;
        }

        public void setInterest_algorithm(String interest_algorithm) {
            this.interest_algorithm = interest_algorithm;
        }

        public String getAverage_time() {
            return average_time;
        }

        public void setAverage_time(String average_time) {
            this.average_time = average_time;
        }

        @Override
        public String toString() {
            return "ProductBean{" +
                    "pname='" + pname + '\'' +
                    ", product_introduction='" + product_introduction + '\'' +
                    ", min_algorithm='" + min_algorithm + '\'' +
                    ", interest_algorithm='" + interest_algorithm + '\'' +
                    ", average_time='" + average_time + '\'' +
                    ", platformname='" + platformname + '\'' +
                    ", Introduction='" + Introduction + '\'' +
                    ", logo='" + logo + '\'' +
                    ", minimum_amount='" + minimum_amount + '\'' +
                    ", maximum_amount='" + maximum_amount + '\'' +
                    ", crowd='" + crowd + '\'' +
                    ", review='" + review + '\'' +
                    ", arrive='" + arrive + '\'' +
                    ", actual_account='" + actual_account + '\'' +
                    ", repayment='" + repayment + '\'' +
                    ", repayment_channels='" + repayment_channels + '\'' +
                    ", max_algorithm='" + max_algorithm + '\'' +
                    ", prepayment='" + prepayment + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProductListBean{" +
                "isSuccess='" + isSuccess + '\'' +
                ", advertising=" + advertising +
                ", product=" + product +
                '}';
    }
}
