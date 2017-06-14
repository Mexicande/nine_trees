package cn.com.stableloan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/6/11.
 */

public class Product_DescBean implements Serializable {

    /**
     * product : {"pname":"学子速贷","product_introduction":"大学生专属，通过率高，高效快速放款","id":"32","product_logo":"http://or2eh71ll.bkt.clouddn.com/149701337726906.png?e=1497016999&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:sHqFDfi7Y9mPdqAbO4pyvTu7EKg=","min_algorithm":"0.5","interest_algorithm":"1","average_time":"12小时","pl_id":"1","minimum_amount":"3000","maximum_amount":"30000","crowd":"2","review":"0","actual_account":"0","repayment":"1","repayment_channels":"1","max_algorithm":"1.2","prepayment":"1","product_details":"","raiders_connection":"","fastest_time":"2小时","labels":[],"header":0,"amount":27000}
     * platformdetail : null
     * isSuccess : true
     */

    private ProductBean product;
    private Object platformdetail;
    private boolean isSuccess;

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public Object getPlatformdetail() {
        return platformdetail;
    }

    public void setPlatformdetail(Object platformdetail) {
        this.platformdetail = platformdetail;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public static class ProductBean implements Serializable{
        /**
         * pname : 学子速贷
         * product_introduction : 大学生专属，通过率高，高效快速放款
         * id : 32
         * product_logo : http://or2eh71ll.bkt.clouddn.com/149701337726906.png?e=1497016999&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:sHqFDfi7Y9mPdqAbO4pyvTu7EKg=
         * min_algorithm : 0.5
         * interest_algorithm : 1
         * average_time : 12小时
         * pl_id : 1
         * minimum_amount : 3000
         * maximum_amount : 30000
         * crowd : 2
         * review : 0
         * actual_account : 0
         * repayment : 1
         * repayment_channels : 1
         * max_algorithm : 1.2
         * prepayment : 1
         * product_details :
         * raiders_connection :
         * fastest_time : 2小时
         * labels : []
         * header : 0
         * amount : 27000
         */
        private String pname;
        private String product_introduction;
        private String id;
        private String product_logo;
        private String min_algorithm;
        private String interest_algorithm;
        private String average_time;
        private String pl_id;
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
        private String raiders_connection;
        private String fastest_time;
        private String header;
        private String amount;
        private List<Class_ListProductBean.ProductBean.LabelsBean> labels;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProduct_logo() {
            return product_logo;
        }

        public void setProduct_logo(String product_logo) {
            this.product_logo = product_logo;
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

        public String getRaiders_connection() {
            return raiders_connection;
        }

        public void setRaiders_connection(String raiders_connection) {
            this.raiders_connection = raiders_connection;
        }

        public String getFastest_time() {
            return fastest_time;
        }

        public void setFastest_time(String fastest_time) {
            this.fastest_time = fastest_time;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public List<Class_ListProductBean.ProductBean.LabelsBean> getLabels() {
            return labels;
        }
        public void setLabels(List<Class_ListProductBean.ProductBean.LabelsBean> labels) {
            this.labels = labels;
        }
    }
}

