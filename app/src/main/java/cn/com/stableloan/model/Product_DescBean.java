package cn.com.stableloan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/6/11.
 */

public class Product_DescBean implements Serializable {


    /**
     * code : 200
     * message : 200
     * data : {"id":5,"pname":"钱有路","pl_id":5,"link":"http://static.namifunds.com/qyl/web/views/invitation.html?iv_code=OVM667382","abbreviation":0,"minimum_amount":"500","maximum_amount":"1000","use":"秒贷现金","crowd":"上班族 学生党 逍遥客","fastest_time":"1分钟","average_time":"","review":"自动审核","index":50,"interest_algorithm":"日利率","min_algorithm":"0.036","max_algorithm":"0.036","min_cycle":"14天","max_cycle":"14天","text_supplement":0,"fee":0,"actual_account":"全部到账","repayment":"等额本息","repayment_channels":"ATM还款","prepayment":"可以","expected_algorithm":"","raiders_connection":"","product_logo":"http://or2eh71ll.bkt.clouddn.com/149760239837032.png?e=1497605998&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:VtXkhA6LKckjpVeHo4Mb0bfBP7I=","product_introduction":"快速审批，当天到账。","product_details":"年龄范围在18-55周岁之间aaa身份证+手机号aaa申请简单、快速下款","username":"admin","time":"2017-06-16 18:50:54","create_time":"2017-06-16 16:39:58","created_at":"2017-06-16 08:39:58","updated_at":"2017-06-16 10:50:54","status":1,"actual_accounts":"","repayments":"","prepayments":"","labels":[{"id":1,"name":"实名手机","font":"#ffffff","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":5,"labels_id":1}},{"id":2,"name":"身份证","font":"#ffffff","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":5,"labels_id":2}}],"collectioStatus":2,"platformdetail":{"pl_name":"钱有路","introduction":"钱有路一款为用户提供小额短期借款的APP。2016年11月拿米金融在小额贷款行业推出移动互联网应用\u201c钱有路\u201dAPP。钱有路始终坚持为用户提供稳定快速的小额现金贷款服务。aaa钱有路始终坚持为用户提供稳定快速的小额现金贷款服务，以信用转换价值，解决急需小额现金用户的燃眉之急。从创立之初，钱有路小额贷款的初衷就一直没有变过，这个信条也一直贯穿钱有路产品研发与服务的始终。","registered_time":"2016-12-17"},"crowds":["0","1","2","3"]}
     * error_code : 0
     * error_message :
     * time : 2017-08-01 13:57:09
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
         * id : 5
         * pname : 钱有路
         * pl_id : 5
         * link : http://static.namifunds.com/qyl/web/views/invitation.html?iv_code=OVM667382
         * abbreviation : 0
         * minimum_amount : 500
         * maximum_amount : 1000
         * use : 秒贷现金
         * crowd : 上班族 学生党 逍遥客
         * fastest_time : 1分钟
         * average_time :
         * review : 自动审核
         * index : 50
         * interest_algorithm : 日利率
         * min_algorithm : 0.036
         * max_algorithm : 0.036
         * min_cycle : 14天
         * max_cycle : 14天
         * text_supplement : 0
         * fee : 0
         * actual_account : 全部到账
         * repayment : 等额本息
         * repayment_channels : ATM还款
         * prepayment : 可以
         * expected_algorithm :
         * raiders_connection :
         * product_logo : http://or2eh71ll.bkt.clouddn.com/149760239837032.png?e=1497605998&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:VtXkhA6LKckjpVeHo4Mb0bfBP7I=
         * product_introduction : 快速审批，当天到账。
         * product_details : 年龄范围在18-55周岁之间aaa身份证+手机号aaa申请简单、快速下款
         * username : admin
         * time : 2017-06-16 18:50:54
         * create_time : 2017-06-16 16:39:58
         * created_at : 2017-06-16 08:39:58
         * updated_at : 2017-06-16 10:50:54
         * status : 1
         * actual_accounts :
         * repayments :
         * prepayments :
         * labels : [{"id":1,"name":"实名手机","font":"#ffffff","background":"#13a3ff","number":1,"status":1,"created_at":"2017-07-26 16:00:00","updated_at":"2017-07-27 10:23:00","pivot":{"product_id":5,"labels_id":1}},{"id":2,"name":"身份证","font":"#ffffff","background":"#13a3ff","number":0,"status":1,"created_at":"2017-07-27 08:41:11","updated_at":"2017-07-27 09:03:15","pivot":{"product_id":5,"labels_id":2}}]
         * collectioStatus : 2
         * platformdetail : {"pl_name":"钱有路","introduction":"钱有路一款为用户提供小额短期借款的APP。2016年11月拿米金融在小额贷款行业推出移动互联网应用\u201c钱有路\u201dAPP。钱有路始终坚持为用户提供稳定快速的小额现金贷款服务。aaa钱有路始终坚持为用户提供稳定快速的小额现金贷款服务，以信用转换价值，解决急需小额现金用户的燃眉之急。从创立之初，钱有路小额贷款的初衷就一直没有变过，这个信条也一直贯穿钱有路产品研发与服务的始终。","registered_time":"2016-12-17"}
         * crowds : ["0","1","2","3"]
         */
        private String ad_image;
        private int id;
        private String pname;
        private int pl_id;

        public String getAd_image() {
            return ad_image;
        }

        public void setAd_image(String ad_image) {
            this.ad_image = ad_image;
        }

        private String link;
        private int abbreviation;
        private String minimum_amount;
        private String maximum_amount;
        private String use;
        private String crowd;
        private String fastest_time;
        private String average_time;
        private String review;
        private int index;
        private int interest_algorithm;
        private String min_algorithm;
        private String max_algorithm;
        private String min_cycle;
        private String max_cycle;
        private int text_supplement;
        private int fee;
        private String actual_account;
        private String repayment;
        private String repayment_channels;
        private String prepayment;
        private String expected_algorithm;
        private String raiders_connection;
        private String product_logo;
        private String product_introduction;
        private String product_details;
        private String username;
        private String time;
        private String create_time;
        private String created_at;
        private String updated_at;
        private int status;
        private String actual_accounts;
        private String repayments;
        private String prepayments;
        private int collectioStatus;
        private PlatformdetailBean platformdetail;
        private List<LabelsBean> labels;
        private List<String> crowds;

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

        public int getPl_id() {
            return pl_id;
        }

        public void setPl_id(int pl_id) {
            this.pl_id = pl_id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(int abbreviation) {
            this.abbreviation = abbreviation;
        }

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

        public String getUse() {
            return use;
        }

        public void setUse(String use) {
            this.use = use;
        }

        public String getCrowd() {
            return crowd;
        }

        public void setCrowd(String crowd) {
            this.crowd = crowd;
        }

        public String getFastest_time() {
            return fastest_time;
        }

        public void setFastest_time(String fastest_time) {
            this.fastest_time = fastest_time;
        }

        public String getAverage_time() {
            return average_time;
        }

        public void setAverage_time(String average_time) {
            this.average_time = average_time;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getInterest_algorithm() {
            return interest_algorithm;
        }

        public void setInterest_algorithm(int interest_algorithm) {
            this.interest_algorithm = interest_algorithm;
        }

        public String getMin_algorithm() {
            return min_algorithm;
        }

        public void setMin_algorithm(String min_algorithm) {
            this.min_algorithm = min_algorithm;
        }

        public String getMax_algorithm() {
            return max_algorithm;
        }

        public void setMax_algorithm(String max_algorithm) {
            this.max_algorithm = max_algorithm;
        }

        public String getMin_cycle() {
            return min_cycle;
        }

        public void setMin_cycle(String min_cycle) {
            this.min_cycle = min_cycle;
        }

        public String getMax_cycle() {
            return max_cycle;
        }

        public void setMax_cycle(String max_cycle) {
            this.max_cycle = max_cycle;
        }

        public int getText_supplement() {
            return text_supplement;
        }

        public void setText_supplement(int text_supplement) {
            this.text_supplement = text_supplement;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public String getActual_account() {
            return actual_account;
        }

        public void setActual_account(String actual_account) {
            this.actual_account = actual_account;
        }

        public String getRepayment() {
            return repayment;
        }

        public void setRepayment(String repayment) {
            this.repayment = repayment;
        }

        public String getRepayment_channels() {
            return repayment_channels;
        }

        public void setRepayment_channels(String repayment_channels) {
            this.repayment_channels = repayment_channels;
        }

        public String getPrepayment() {
            return prepayment;
        }

        public void setPrepayment(String prepayment) {
            this.prepayment = prepayment;
        }

        public String getExpected_algorithm() {
            return expected_algorithm;
        }

        public void setExpected_algorithm(String expected_algorithm) {
            this.expected_algorithm = expected_algorithm;
        }

        public String getRaiders_connection() {
            return raiders_connection;
        }

        public void setRaiders_connection(String raiders_connection) {
            this.raiders_connection = raiders_connection;
        }

        public String getProduct_logo() {
            return product_logo;
        }

        public void setProduct_logo(String product_logo) {
            this.product_logo = product_logo;
        }

        public String getProduct_introduction() {
            return product_introduction;
        }

        public void setProduct_introduction(String product_introduction) {
            this.product_introduction = product_introduction;
        }

        public String getProduct_details() {
            return product_details;
        }

        public void setProduct_details(String product_details) {
            this.product_details = product_details;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getActual_accounts() {
            return actual_accounts;
        }

        public void setActual_accounts(String actual_accounts) {
            this.actual_accounts = actual_accounts;
        }

        public String getRepayments() {
            return repayments;
        }

        public void setRepayments(String repayments) {
            this.repayments = repayments;
        }

        public String getPrepayments() {
            return prepayments;
        }

        public void setPrepayments(String prepayments) {
            this.prepayments = prepayments;
        }

        public int getCollectioStatus() {
            return collectioStatus;
        }

        public void setCollectioStatus(int collectioStatus) {
            this.collectioStatus = collectioStatus;
        }

        public PlatformdetailBean getPlatformdetail() {
            return platformdetail;
        }

        public void setPlatformdetail(PlatformdetailBean platformdetail) {
            this.platformdetail = platformdetail;
        }

        public List<LabelsBean> getLabels() {
            return labels;
        }

        public void setLabels(List<LabelsBean> labels) {
            this.labels = labels;
        }

        public List<String> getCrowds() {
            return crowds;
        }

        public void setCrowds(List<String> crowds) {
            this.crowds = crowds;
        }

        public static class PlatformdetailBean implements Serializable{
            /**
             * pl_name : 钱有路
             * introduction : 钱有路一款为用户提供小额短期借款的APP。2016年11月拿米金融在小额贷款行业推出移动互联网应用“钱有路”APP。钱有路始终坚持为用户提供稳定快速的小额现金贷款服务。aaa钱有路始终坚持为用户提供稳定快速的小额现金贷款服务，以信用转换价值，解决急需小额现金用户的燃眉之急。从创立之初，钱有路小额贷款的初衷就一直没有变过，这个信条也一直贯穿钱有路产品研发与服务的始终。
             * registered_time : 2016-12-17
             */

            private String pl_name;
            private String introduction;
            private String registered_time;

            public String getPl_name() {
                return pl_name;
            }

            public void setPl_name(String pl_name) {
                this.pl_name = pl_name;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getRegistered_time() {
                return registered_time;
            }

            public void setRegistered_time(String registered_time) {
                this.registered_time = registered_time;
            }
        }

        public static class LabelsBean implements Serializable{
            /**
             * id : 1
             * name : 实名手机
             * font : #ffffff
             * background : #13a3ff
             * number : 1
             * status : 1
             * created_at : 2017-07-26 16:00:00
             * updated_at : 2017-07-27 10:23:00
             * pivot : {"product_id":5,"labels_id":1}
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
                 * product_id : 5
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

