package cn.com.laifenqicash.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/9/26.
 */

public class Seckill_Bean implements Serializable {


    /**
     * code : 200
     * message : 0
     * data : [{"id":1,"product_id":1,"product_logo":"http://www.baidu.com","product_name":"测试1","headline":"测试1","activity_desc":"测试赛","amount":"10000","status":1}]
     * error_code : 0
     * error_message :
     * time : 2017-09-26 17:44:32
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
         * product_id : 1
         * product_logo : http://www.baidu.com
         * product_name : 测试1
         * headline : 测试1
         * activity_desc : 测试赛
         * amount : 10000
         * status : 1
         */

        private int id;
        private int product_id;
        private String product_logo;
        private String product_name;
        private String headline;
        private String activity_desc;
        private String amount;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_logo() {
            return product_logo;
        }

        public void setProduct_logo(String product_logo) {
            this.product_logo = product_logo;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getHeadline() {
            return headline;
        }

        public void setHeadline(String headline) {
            this.headline = headline;
        }

        public String getActivity_desc() {
            return activity_desc;
        }

        public void setActivity_desc(String activity_desc) {
            this.activity_desc = activity_desc;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
