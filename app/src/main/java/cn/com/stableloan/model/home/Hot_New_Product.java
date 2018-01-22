package cn.com.stableloan.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/9/26.
 */

public class Hot_New_Product implements Serializable{

    /**
     * code : 200
     * message : 0
     * data : [{"pname":"省呗","id":16,"product_introduction":"帮你代还信用卡，低息，方便","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760752088277.png?e=1497611120&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:1kWFrgOP8kRSEGEF9vBAt0FM9tw=","min_algorithm":"1.08","interest_algorithm":1,"fastest_time":"24小时","maximum_amount":"20000","activity":0,"online":1,"labels":[{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":16,"labels_id":1}},{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":16,"labels_id":2}}]},{"pname":"手机贷","id":15,"product_introduction":"费用超级低，要借钱快点来","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760730533351.png?e=1497610905&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:bT5QXl6sG3OLZ7BWIs2-CCMN7RU=","min_algorithm":"0.3","interest_algorithm":1,"fastest_time":"24小时","maximum_amount":"3000","activity":0,"online":1,"labels":[{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":15,"labels_id":1}},{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":15,"labels_id":2}}]},{"pname":"用钱宝","id":14,"product_introduction":"想不想试试，30分钟拿到钱","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760705756398.png?e=1497610657&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:kKGKKyJ_pe2qHzQ67ZZbjwyw2AM=","min_algorithm":"0.3","interest_algorithm":1,"fastest_time":"3分钟","maximum_amount":"5000","activity":0,"online":1,"labels":[{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":14,"labels_id":2}},{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":14,"labels_id":1}}]},{"pname":"小赢卡贷","id":13,"product_introduction":"信用卡快速还款，当天到账","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760669746429.png?e=1497610297&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:wYTcdfmH82QgSq1C-JVs46RYpAE=","min_algorithm":"1.4","interest_algorithm":1,"fastest_time":"24小时","maximum_amount":"200000","activity":0,"online":1,"labels":[{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":13,"labels_id":1}},{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":13,"labels_id":2}}]},{"pname":"你我贷","id":12,"product_introduction":"当天下款，件均13000元","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760636345541.png?e=1497609963&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:ykkwHiMCJQXgY302paWLEDF5_Wc=","min_algorithm":"0.7","interest_algorithm":1,"fastest_time":"2小时","maximum_amount":"30000","activity":0,"online":1,"labels":[{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":12,"labels_id":1}},{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":12,"labels_id":2}}]},{"pname":"现金巴士","id":11,"product_introduction":"小额短期，无限循环","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760610288833.png?e=1497609702&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:2Zl_Xt2vvN4rIRx0uuqIqa-CtbE=","min_algorithm":"1","interest_algorithm":1,"fastest_time":"24小时","maximum_amount":"1000","activity":0,"online":1,"labels":[{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":11,"labels_id":1}},{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":11,"labels_id":2}},{"id":6,"name":"一次还清","font":"#ffffff","background":"#ff8c59","number":0,"status":1,"created_at":"2017-07-27 09:13:35","updated_at":"2017-07-27 10:05:59","pivot":{"product_id":11,"labels_id":6}}]},{"pname":"小花钱包","id":10,"product_introduction":"最快3分钟，件均5500元","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760543558230.png?e=1497609035&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:Vd-w-W3TMgdHCYHb33afGJ6VLuY=","min_algorithm":"0.57","interest_algorithm":1,"fastest_time":"3分钟","maximum_amount":"20000","activity":0,"online":1,"labels":[{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":10,"labels_id":2}},{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":10,"labels_id":1}}]},{"pname":"借钱快","id":9,"product_introduction":"身份证借款，最快5分钟到账","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760511382844.png?e=1497608713&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:gANDnnevTanc-Iv7RXZ5at0UVj0=","min_algorithm":"0.3","interest_algorithm":1,"fastest_time":"5分钟","maximum_amount":"5000","activity":0,"online":1,"labels":[{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":9,"labels_id":1}},{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":9,"labels_id":2}}]},{"pname":"身份贷","id":8,"product_introduction":"借款额度范围大，类型齐全","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760482120714.png?e=1497608421&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:J3Z49qce4EjBUc68IUcy7RVT0Vw=","min_algorithm":"0.87","interest_algorithm":1,"fastest_time":"24小时","maximum_amount":"50000","activity":0,"online":1,"labels":[{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":8,"labels_id":2}},{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":8,"labels_id":1}}]},{"pname":"闪银","id":7,"product_introduction":"11月免息借钱，现金10分钟到账","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760444187890.png?e=1497608041&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:4U1aG8qtSVWeWgYIFPq7_X5powI=","min_algorithm":"1.3","interest_algorithm":1,"fastest_time":"24小时","maximum_amount":"5000","activity":0,"online":1,"labels":[{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":7,"labels_id":1}},{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":7,"labels_id":2}}]}]
     * error_code : 0
     * error_message :
     * time : 2017-09-28 11:19:33
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
         * pname : 省呗
         * id : 16
         * product_introduction : 帮你代还信用卡，低息，方便
         * product_logo : http://or2eh71ll.bkt.clouddn.com/149760752088277.png?e=1497611120&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:1kWFrgOP8kRSEGEF9vBAt0FM9tw=
         * min_algorithm : 1.08
         * interest_algorithm : 1
         * fastest_time : 24小时
         * maximum_amount : 20000
         * activity : 0
         * online : 1
         * labels : [{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":16,"labels_id":1}},{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":16,"labels_id":2}}]
         */

        private String pname;
        private int id;
        private String product_introduction;
        private String product_logo;
        private String min_algorithm;
        private int interest_algorithm;
        private String fastest_time;
        private String maximum_amount;
        private int activity;
        private int online;
        private List<LabelsBean> labels;

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProduct_introduction() {
            return product_introduction;
        }

        public void setProduct_introduction(String product_introduction) {
            this.product_introduction = product_introduction;
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

        public int getInterest_algorithm() {
            return interest_algorithm;
        }

        public void setInterest_algorithm(int interest_algorithm) {
            this.interest_algorithm = interest_algorithm;
        }

        public String getFastest_time() {
            return fastest_time;
        }

        public void setFastest_time(String fastest_time) {
            this.fastest_time = fastest_time;
        }

        public String getMaximum_amount() {
            return maximum_amount;
        }

        public void setMaximum_amount(String maximum_amount) {
            this.maximum_amount = maximum_amount;
        }

        public int getActivity() {
            return activity;
        }

        public void setActivity(int activity) {
            this.activity = activity;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public List<LabelsBean> getLabels() {
            return labels;
        }

        public void setLabels(List<LabelsBean> labels) {
            this.labels = labels;
        }

        public static class LabelsBean implements Serializable{
            /**
             * id : 1
             * name : 实名手机
             * font : #004080
             * background : #13a3ff
             * number : 1
             * status : 1
             * created_at : 2017-07-26 16:00:00
             * updated_at : 2017-07-27 10:23:00
             * pivot : {"product_id":16,"labels_id":1}
             */

            private int id;
            private String name;
            private String font;
            private String background;
            private int number;
            private int status;
            private String created_at;
            private String updated_at;
            private PivotBean pivot;

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

            public PivotBean getPivot() {
                return pivot;
            }

            public void setPivot(PivotBean pivot) {
                this.pivot = pivot;
            }

            public static class PivotBean implements Serializable{
                /**
                 * product_id : 16
                 * labels_id : 1
                 */

                private int product_id;
                private int labels_id;

                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public int getLabels_id() {
                    return labels_id;
                }

                public void setLabels_id(int labels_id) {
                    this.labels_id = labels_id;
                }
            }
        }
    }
}
