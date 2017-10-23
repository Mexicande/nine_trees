package cn.com.stableloan.model.clsaa_special;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/9/28.
 */

public class Class_Special implements Serializable {

    /**
     * code : 200
     * message : 0
     * data : {"project":[{"id":1,"project_name":"大额度","title":"就是大","body_project":"正文","preface":"引言","body":"正文","product_amount":4615160,"total_amount":4545,"project_logo":"http://www.baidu.com","status":1}],"product":[{"id":15,"pname":"手机贷","product_introduction":"费用超级低，要借钱快点来","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760730533351.png?e=1497610905&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:bT5QXl6sG3OLZ7BWIs2-CCMN7RU=","average_time":"24小时","min_algorithm":"0.3","fastest_time":"24小时","online":1,"activity":0,"labels":[{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","status":1},{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","status":1}]},{"id":14,"pname":"用钱宝","product_introduction":"想不想试试，30分钟拿到钱","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760705756398.png?e=1497610657&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:kKGKKyJ_pe2qHzQ67ZZbjwyw2AM=","average_time":"30分钟","min_algorithm":"0.3","fastest_time":"3分钟","online":1,"activity":0,"labels":[{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","status":1},{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","status":1}]},{"id":13,"pname":"小赢卡贷","product_introduction":"信用卡快速还款，当天到账","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760669746429.png?e=1497610297&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:wYTcdfmH82QgSq1C-JVs46RYpAE=","average_time":"2小时","min_algorithm":"1.4","fastest_time":"24小时","online":1,"activity":0,"labels":[{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","status":1},{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","status":1}]}],"mdse":[{"mdse_ad":"广告","mdse_h5_link":"http://www.baidu.com","id":1}]}
     * error_code : 0
     * error_message :
     * time : 2017-09-28 16:26:57
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
        private List<ProjectBean> project;
        private List<ProductBean> product;
        private List<MdseBean> mdse;
        private String tenant_name;

        public String getTitle() {
            return tenant_name;
        }

        public void setTitle(String title) {
            this.tenant_name = title;
        }

        public List<ProjectBean> getProject() {
            return project;
        }

        public void setProject(List<ProjectBean> project) {
            this.project = project;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public List<MdseBean> getMdse() {
            return mdse;
        }

        public void setMdse(List<MdseBean> mdse) {
            this.mdse = mdse;
        }

        public static class ProjectBean implements Serializable{
            /**
             * id : 1
             * project_name : 大额度
             * title : 就是大
             * body_project : 正文
             * preface : 引言
             * body : 正文
             * product_amount : 4615160
             * total_amount : 4545
             * project_logo : http://www.baidu.com
             * status : 1
             */

            private int id;
            private String project_name;
            private String title;
            private String body_project;
            private String preface;
            private String body;
            private int product_amount;
            private int total_amount;
            private String project_logo;
            private int status;

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

            public String getBody_project() {
                return body_project;
            }

            public void setBody_project(String body_project) {
                this.body_project = body_project;
            }

            public String getPreface() {
                return preface;
            }

            public void setPreface(String preface) {
                this.preface = preface;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public int getProduct_amount() {
                return product_amount;
            }

            public void setProduct_amount(int product_amount) {
                this.product_amount = product_amount;
            }

            public int getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(int total_amount) {
                this.total_amount = total_amount;
            }

            public String getProject_logo() {
                return project_logo;
            }

            public void setProject_logo(String project_logo) {
                this.project_logo = project_logo;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class ProductBean implements Serializable{
            /**
             * id : 15
             * pname : 手机贷
             * product_introduction : 费用超级低，要借钱快点来
             * product_logo : http://or2eh71ll.bkt.clouddn.com/149760730533351.png?e=1497610905&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:bT5QXl6sG3OLZ7BWIs2-CCMN7RU=
             * average_time : 24小时
             * min_algorithm : 0.3
             * fastest_time : 24小时
             * online : 1
             * activity : 0
             * labels : [{"id":1,"name":"实名手机","font":"#004080","background":"#13a3ff","status":1},{"id":2,"name":"身份证","font":"#ff8040","background":"#13a3ff","status":1}]
             */

            private int interest_algorithm;
            private int id;
            private String pname;
            private String product_introduction;

            public int getInterest_algorithm() {
                return interest_algorithm;
            }

            public void setInterest_algorithm(int interest_algorithm) {
                this.interest_algorithm = interest_algorithm;
            }

            private String product_logo;
            private String average_time;
            private String min_algorithm;
            private String fastest_time;
            private int online;
            private int activity;
            private String minimum_amount;
            private String maximum_amount;

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

            private List<LabelsBean> labels;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

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

            public String getProduct_logo() {
                return product_logo;
            }

            public void setProduct_logo(String product_logo) {
                this.product_logo = product_logo;
            }

            public String getAverage_time() {
                return average_time;
            }

            public void setAverage_time(String average_time) {
                this.average_time = average_time;
            }

            public String getMin_algorithm() {
                return min_algorithm;
            }

            public void setMin_algorithm(String min_algorithm) {
                this.min_algorithm = min_algorithm;
            }

            public String getFastest_time() {
                return fastest_time;
            }

            public void setFastest_time(String fastest_time) {
                this.fastest_time = fastest_time;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public int getActivity() {
                return activity;
            }

            public void setActivity(int activity) {
                this.activity = activity;
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
                 * status : 1
                 */

                private int id;
                private String name;
                private String font;
                private String background;
                private int status;

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

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }
        }

        public static class MdseBean implements Serializable{
            /**
             * mdse_ad : 广告
             * mdse_h5_link : http://www.baidu.com
             * id : 1
             */
            private String mdse_name;
            private String mdse_ad;
            private String mdse_h5_link;
            public String getMdse_name() {
                return mdse_name;
            }

            public void setMdse_name(String mdse_name) {
                this.mdse_name = mdse_name;
            }

            private int id;

            public String getMdse_ad() {
                return mdse_ad;
            }

            public void setMdse_ad(String mdse_ad) {
                this.mdse_ad = mdse_ad;
            }

            public String getMdse_h5_link() {
                return mdse_h5_link;
            }

            public void setMdse_h5_link(String mdse_h5_link) {
                this.mdse_h5_link = mdse_h5_link;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
