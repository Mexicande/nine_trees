package cn.com.laifenqicash.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/7/31.
 */

public class TagFlowBean implements Serializable{


    /**
     * code : 200
     * message : 200
     * data : [{"id":1,"name":"实名手机","font":"#ffffff","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00"},{"id":2,"name":"身份证","font":"#ffffff","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15"},{"id":3,"name":"信用卡","font":"#ffffff","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 09:11:23","updated_at":"2017-07-27 09:11:23"},{"id":4,"name":"芝麻信用","font":"#ffffff","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 09:11:55","updated_at":"2017-07-27 09:11:55"},{"id":5,"name":"信用卡","font":"#ffffff","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 09:13:02","updated_at":"2017-07-27 09:13:02"},{"id":6,"name":"一次还清","font":"#ffffff","background":"#ff8c59","number":0,"status":1,"created_at":"2017-07-27 09:13:35","updated_at":"2017-07-27 10:05:59"},{"id":7,"name":"淘宝京东","font":"#ffffff","background":"#68b3ff","number":0,"status":1,"created_at":"2017-07-27 09:14:09","updated_at":"2017-07-27 09:42:11"}]
     * error_code : 0
     * error_message :
     * time : 2017-07-31 18:02:56
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

    @Override
    public String toString() {
        return "TagFlowBean{" +
                "code=" + code +
                ", message=" + message +
                ", error_code=" + error_code +
                ", error_message='" + error_message + '\'' +
                ", time='" + time + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean  implements Serializable{
        /**
         * id : 1
         * name : 实名手机
         * font : #ffffff
         * background : #13a3ff
         * number : 1
         * status : 1
         * created_at : 2017-07-26 16:00:00
         * updated_at : 2017-07-27 10:23:00
         */

        private int id;
        private String name;
        private String font;
        private String background;
        private int number;
        private int status;
        private String created_at;
        private String updated_at;

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

        public String getFont() {
            return font;
        }

        public void setFont(String font) {
            this.font = font;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", font='" + font + '\'' +
                    ", background='" + background + '\'' +
                    ", number=" + number +
                    ", status=" + status +
                    ", created_at='" + created_at + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    '}';
        }
    }
}
