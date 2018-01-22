package cn.com.stableloan.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/9/26.
 */

public class SpecialClassBean implements Serializable{


    /**
     * code : 200
     * message : 200
     * data : [{"id":1,"project_name":"大额专题","title ":"大额专题","project_logo":"http://4565655asfsafsadfdasd","preface":"引言","total_amount":"500000","product_amount":10000},{"id":2,"project_name":"大额专题","title ":"大额专题","project_logo":"http://4565655asfsafsadfdasd","preface":"引言","total_amount":"500000","product_amount":10000}]
     * error_code : 0
     * error_message :
     * time : 2017-08-08 11:28:27
     */

    private int code;
    private String message;
    private int error_code;
    private String error_message;
    private String time;
    private List<DataBean> data;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * project_name : 大额专题
         * title  : 大额专题
         * project_logo : http://4565655asfsafsadfdasd
         * preface : 引言
         * total_amount : 500000
         * product_amount : 10000
         */

        private int id;
        private String project_name;
        private String title;
        private String project_logo;
        private String preface;
        private String total_amount;
        private int product_amount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getProject_logo() {
            return project_logo;
        }

        public void setProject_logo(String project_logo) {
            this.project_logo = project_logo;
        }

        public String getPreface() {
            return preface;
        }

        public void setPreface(String preface) {
            this.preface = preface;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public int getProduct_amount() {
            return product_amount;
        }

        public void setProduct_amount(int product_amount) {
            this.product_amount = product_amount;
        }
    }
}
