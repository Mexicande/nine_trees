package cn.com.stableloan.model.integarl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/8/11.
 */

public class IntegarlBean implements Serializable{

    /**
     * code : 200
     * message : 200
     * data : {"code":[{"id":1,"title":"什么","desc":"我好吗","code":"积分+100","status":100,"create_at":"1970-01-02 00:00:00","update_at":"1970-01-02 00:00:00","update_user_id":1,"url":"http://test.m.anwenqianbao.com/#/login?$inviteCode=170811zmxdfn2z","img":"http://qiniu.shoujijiekuan.com/%E5%AE%89%E7%A8%B3%E9%92%B1%E5%8C%85-%E7%A7%AF%E5%88%863_03.png"},{"id":2,"title":"神马","desc":"你好吗","code":"积分+200","status":200,"create_at":"1970-01-02 00:00:00","update_at":"1970-01-02 00:00:00","update_user_id":0,"url":"http://test.m.anwenqianbao.com/#/login?$inviteCode=170811zmxdfn2z","img":"http://qiniu.shoujijiekuan.com/%E5%AE%89%E7%A8%B3%E9%92%B1%E5%8C%85-%E7%A7%AF%E5%88%864_03.png"}],"status":"+500","offical":"","credits":0}
     * error_code : 0
     * error_message :
     * time : 2017-08-11 16:17:34
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
         * code : [{"id":1,"title":"什么","desc":"我好吗","code":"积分+100","status":100,"create_at":"1970-01-02 00:00:00","update_at":"1970-01-02 00:00:00","update_user_id":1,"url":"http://test.m.anwenqianbao.com/#/login?$inviteCode=170811zmxdfn2z","img":"http://qiniu.shoujijiekuan.com/%E5%AE%89%E7%A8%B3%E9%92%B1%E5%8C%85-%E7%A7%AF%E5%88%863_03.png"},{"id":2,"title":"神马","desc":"你好吗","code":"积分+200","status":200,"create_at":"1970-01-02 00:00:00","update_at":"1970-01-02 00:00:00","update_user_id":0,"url":"http://test.m.anwenqianbao.com/#/login?$inviteCode=170811zmxdfn2z","img":"http://qiniu.shoujijiekuan.com/%E5%AE%89%E7%A8%B3%E9%92%B1%E5%8C%85-%E7%A7%AF%E5%88%864_03.png"}]
         * status : +500
         * offical :
         * credits : 0
         */
        private int topCredits;
        private String status;
        private String offical;
        private int credits;
        private List<CodeBean> code;

        public int getTopCredits() {
            return topCredits;
        }

        public void setTopCredits(int topCredits) {
            this.topCredits = topCredits;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOffical() {
            return offical;
        }

        public void setOffical(String offical) {
            this.offical = offical;
        }

        public int getCredits() {
            return credits;
        }

        public void setCredits(int credits) {
            this.credits = credits;
        }

        public List<CodeBean> getCode() {
            return code;
        }

        public void setCode(List<CodeBean> code) {
            this.code = code;
        }

        public static class CodeBean implements Serializable{
            /**
             * id : 1
             * title : 什么
             * desc : 我好吗
             * code : 积分+100
             * status : 100
             * create_at : 1970-01-02 00:00:00
             * update_at : 1970-01-02 00:00:00
             * update_user_id : 1
             * url : http://test.m.anwenqianbao.com/#/login?$inviteCode=170811zmxdfn2z
             * img : http://qiniu.shoujijiekuan.com/%E5%AE%89%E7%A8%B3%E9%92%B1%E5%8C%85-%E7%A7%AF%E5%88%863_03.png
             */

            private int id;
            private String bg;

            public String getBg() {
                return bg;
            }

            public void setBg(String bg) {
                this.bg = bg;
            }

            private String title;
            private String desc;
            private String code;
            private String status;
            private String create_at;
            private String update_at;
            private int update_user_id;
            private String url;
            private String img;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public String getUpdate_at() {
                return update_at;
            }

            public void setUpdate_at(String update_at) {
                this.update_at = update_at;
            }

            public int getUpdate_user_id() {
                return update_user_id;
            }

            public void setUpdate_user_id(int update_user_id) {
                this.update_user_id = update_user_id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}

