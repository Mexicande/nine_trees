package cn.com.laifenqicash.bean.cash;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/11/9.
 */

public class CashActivityBean implements Serializable {

    /**
     * code : 200
     * message :
     * data : {"activity":[{"title":"","img":"","desc":"","url":"","rule":""}]}
     * error_code : 0
     * error_message :
     * time : 2017-08-18 15:24:09
     */

    private int code;
    private String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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

    public static class DataBean implements Serializable {
        private List<ActivityBean> activity;

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        public static class ActivityBean implements Serializable{
            /**
             * title :
             * img :
             * desc :
             * url :
             * rule :
             */

            private String title;
            private String img;
            private String desc;
            private String url;
            private String rule;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getRule() {
                return rule;
            }

            public void setRule(String rule) {
                this.rule = rule;
            }
        }
    }
}
