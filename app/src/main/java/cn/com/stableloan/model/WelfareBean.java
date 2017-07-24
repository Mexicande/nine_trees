package cn.com.stableloan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/7/16.
 */

public class WelfareBean implements Serializable {

    /**
     * code : 200
     * message : 200
     * data : [{"id":1,"name":"福利彩票","image":"http://or2eh71ll.bkt.clouddn.com/150088225087834.jpg?e=15008"}]
     * error_code : 0
     * error_message :
     * time : 2017-07-24 17:59:15
     */

    private int code;
    private int message;
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

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
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

    public static class DataBean  implements Serializable{
        /**
         * id : 1
         * name : 福利彩票
         * image : http://or2eh71ll.bkt.clouddn.com/150088225087834.jpg?e=15008
         */

        private int id;
        private String name;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
