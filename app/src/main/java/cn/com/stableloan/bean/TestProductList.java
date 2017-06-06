package cn.com.stableloan.bean;

import java.util.List;

/**
 * Created by apple on 2017/6/2.
 */

public class TestProductList {

    /**
     * product : [{"pl_name":"现金贷","logo":"default.jpg","introduction":"额外无无",
     * "pname":"贷款","product_introduction":"反反复复付付付付","min_algorithm":"0.1",
     * "interest_algorithm":"0",
     * "average_time":"","pl_id":"1","product_logo":
     * "149552544120087.jpg",
     * "minimum_amount":"1","maximum_amount":"10000",
     * "crowd":"[\"1\",\"2\",\"3\"]","review":"1","actual_account":"0","repayment":"0","repayment_channels":"1"
     * ,"max_algorithm":"0.5",
     * "prepayment":"1","product_details":""}]
     * isSuccess : true
     * advertising : [{"pictrue":"default.jpg","h5":"1111111111","app":"11111111111111111"}]
     */

    private boolean isSuccess;
    private List<ProductBean> product;
    private List<AdvertisingBean> advertising;

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public List<AdvertisingBean> getAdvertising() {
        return advertising;
    }

    public void setAdvertising(List<AdvertisingBean> advertising) {
        this.advertising = advertising;
    }

    public static class ProductBean {
        /**
         * pl_name : 现金贷
         * logo : default.jpg
         * introduction : 额外无无
         * pname : 贷款
         * product_introduction : 反反复复付付付付
         * min_algorithm : 0.1
         * interest_algorithm : 0
         * average_time :
         * pl_id : 1
         * product_logo : 149552544120087.jpg
         * minimum_amount : 1
         * maximum_amount : 10000
         * crowd : ["1","2","3"]
         * review : 1
         * actual_account : 0
         * repayment : 0
         * repayment_channels : 1
         * max_algorithm : 0.5
         * prepayment : 1
         * product_details :
         */

        private String pl_name;
        private String logo;
        private String introduction;
        private String pname;
        private String product_introduction;
        private String min_algorithm;
        private String interest_algorithm;
        private String average_time;
        private String pl_id;
        private String product_logo;
        private String minimum_amount;
        private String maximum_amount;
        private String crowd;
        private String review;
        private String actual_account;
        private String repayment;
        private String repayment_channels;
        private String max_algorithm;
        private String prepayment;
        private String product_details;

        public String getPl_name() {
            return pl_name;
        }

        public void setPl_name(String pl_name) {
            this.pl_name = pl_name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

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

        public String getPl_id() {
            return pl_id;
        }

        public void setPl_id(String pl_id) {
            this.pl_id = pl_id;
        }

        public String getProduct_logo() {
            return product_logo;
        }

        public void setProduct_logo(String product_logo) {
            this.product_logo = product_logo;
        }

        public String getMinimum_amount() {
            return minimum_amount;
        }

        public void setMinimum_amount(String minimum_amount) {
            this.minimum_amount = minimum_amount;
        }

        public String getMaximum_amount() {
            return maximum_amount;
        }

        public void setMaximum_amount(String maximum_amount) {
            this.maximum_amount = maximum_amount;
        }

        public String getCrowd() {
            return crowd;
        }

        public void setCrowd(String crowd) {
            this.crowd = crowd;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public String getActual_account() {
            return actual_account;
        }

        public void setActual_account(String actual_account) {
            this.actual_account = actual_account;
        }

        public String getRepayment() {
            return repayment;
        }

        public void setRepayment(String repayment) {
            this.repayment = repayment;
        }

        public String getRepayment_channels() {
            return repayment_channels;
        }

        public void setRepayment_channels(String repayment_channels) {
            this.repayment_channels = repayment_channels;
        }

        public String getMax_algorithm() {
            return max_algorithm;
        }

        public void setMax_algorithm(String max_algorithm) {
            this.max_algorithm = max_algorithm;
        }

        public String getPrepayment() {
            return prepayment;
        }

        public void setPrepayment(String prepayment) {
            this.prepayment = prepayment;
        }

        public String getProduct_details() {
            return product_details;
        }

        public void setProduct_details(String product_details) {
            this.product_details = product_details;
        }

        @Override
        public String toString() {
            return "ProductBean{" +
                    "pl_name='" + pl_name + '\'' +
                    ", logo='" + logo + '\'' +
                    ", introduction='" + introduction + '\'' +
                    ", pname='" + pname + '\'' +
                    ", product_introduction='" + product_introduction + '\'' +
                    ", min_algorithm='" + min_algorithm + '\'' +
                    ", interest_algorithm='" + interest_algorithm + '\'' +
                    ", average_time='" + average_time + '\'' +
                    ", pl_id='" + pl_id + '\'' +
                    ", product_logo='" + product_logo + '\'' +
                    ", minimum_amount='" + minimum_amount + '\'' +
                    ", maximum_amount='" + maximum_amount + '\'' +
                    ", crowd='" + crowd + '\'' +
                    ", review='" + review + '\'' +
                    ", actual_account='" + actual_account + '\'' +
                    ", repayment='" + repayment + '\'' +
                    ", repayment_channels='" + repayment_channels + '\'' +
                    ", max_algorithm='" + max_algorithm + '\'' +
                    ", prepayment='" + prepayment + '\'' +
                    ", product_details='" + product_details + '\'' +
                    '}';
        }
    }

    public static class AdvertisingBean {
        /**
         * pictrue : default.jpg
         * h5 : 1111111111
         * app : 11111111111111111
         */

        private String pictrue;
        private String h5;
        private String app;

        public String getPictrue() {
            return pictrue;
        }

        public void setPictrue(String pictrue) {
            this.pictrue = pictrue;
        }

        public String getH5() {
            return h5;
        }

        public void setH5(String h5) {
            this.h5 = h5;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        @Override
        public String toString() {
            return "AdvertisingBean{" +
                    "pictrue='" + pictrue + '\'' +
                    ", h5='" + h5 + '\'' +
                    ", app='" + app + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "TestProductList{" +
                "isSuccess=" + isSuccess +
                ", product=" + product +
                ", advertising=" + advertising +
                '}';
    }
}


