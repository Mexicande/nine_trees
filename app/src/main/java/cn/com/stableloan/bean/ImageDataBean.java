package cn.com.stableloan.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/9/18.
 */

public class ImageDataBean implements Serializable {

    /**
     * code : 200
     * message : 0
     * data : {"positive_photo":"","negative_photo":"","am_photo":"","debit_photo":"","credite_photo":"","license_photo":"","brand_photo":"","isSuccess":"1","status":"1"}
     * error_code : 0
     * error_message :
     * time : 2017-09-18 19:04:02
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
         * positive_photo :
         * negative_photo :
         * am_photo :
         * debit_photo :
         * credite_photo :
         * license_photo :
         * brand_photo :
         * isSuccess : 1
         * status : 1
         */

        private String positive_photo;
        private String negative_photo;
        private String am_photo;
        private String debit_photo;
        private String credite_photo;
        private String license_photo;
        private String brand_photo;
        private String isSuccess;
        private String status;

        public String getPositive_photo() {
            return positive_photo;
        }

        public void setPositive_photo(String positive_photo) {
            this.positive_photo = positive_photo;
        }

        public String getNegative_photo() {
            return negative_photo;
        }

        public void setNegative_photo(String negative_photo) {
            this.negative_photo = negative_photo;
        }

        public String getAm_photo() {
            return am_photo;
        }

        public void setAm_photo(String am_photo) {
            this.am_photo = am_photo;
        }

        public String getDebit_photo() {
            return debit_photo;
        }

        public void setDebit_photo(String debit_photo) {
            this.debit_photo = debit_photo;
        }

        public String getCredite_photo() {
            return credite_photo;
        }

        public void setCredite_photo(String credite_photo) {
            this.credite_photo = credite_photo;
        }

        public String getLicense_photo() {
            return license_photo;
        }

        public void setLicense_photo(String license_photo) {
            this.license_photo = license_photo;
        }

        public String getBrand_photo() {
            return brand_photo;
        }

        public void setBrand_photo(String brand_photo) {
            this.brand_photo = brand_photo;
        }

        public String getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(String isSuccess) {
            this.isSuccess = isSuccess;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
