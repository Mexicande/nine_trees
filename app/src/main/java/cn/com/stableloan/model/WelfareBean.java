package cn.com.stableloan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/7/16.
 */

public class WelfareBean implements Serializable {
    /**
     * code : 200
     * message :
     * data : [{"name":"wangdong","link":"www.ali.com","image":"56"},{"name":"haidong","link":"www.baidu.com","image":"aksdjsda"}]
     * error_code :
     * error_message :
     */

    private String code;
    private String message;
    private String error_code;
    private String error_message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * name : wangdong
         * link : www.ali.com
         * image : 56
         */

        private String name;
        private String link;
        private String image;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
