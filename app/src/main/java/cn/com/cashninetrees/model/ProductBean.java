package cn.com.cashninetrees.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2018/6/22.
 */

public class ProductBean implements Serializable {

    /**
     * code : 200
     * message : 200
     * data : [{"id":56,"name":"征信查询","link":"https://activity.kongapi.com/zxcx/?channel=xjd4zxapp&utm_source=zhengxindaoliu&utm_medium=xjd4zxapp ","product_logo":"http://or2eh71ll.bkt.clouddn.com/151685513347748.png","product_introduction":"低门槛、极速放款、费率低","interest_algorithm":"未知","min_algorithm":"0.150%","max_algorithm":"0.150%","maximum_amount":1000000,"minimum_amount":3000,"repayment_total":3004.5,"apply":10000},{"id":48,"name":"百度有钱花","link":"https://icash.baidu.com/cloan/operation/activity?activityName=channelBrand&CH=jmall&fr=jmall_list213","product_logo":"http://or2eh71ll.bkt.clouddn.com/151563938488109.png","product_introduction":"极速大额现金贷，低息审批特别快","interest_algorithm":"未知","min_algorithm":"1.500%","max_algorithm":"1.500%","maximum_amount":200000,"minimum_amount":500,"repayment_total":507.5,"apply":12000},{"id":5,"name":"钱站","link":"http://app-h5.iqianzhan.com/sources/outsideRegister.html?qd=xjd4","product_logo":"http://or2eh71ll.bkt.clouddn.com/150130115333537.png?e=1501304753&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:5wgVKaBhHbHXqpTY5nJlNs3FgVI=","product_introduction":"快速审批，当天到账。","interest_algorithm":"未知","min_algorithm":"0.830%","max_algorithm":"0.830%","maximum_amount":100000,"minimum_amount":2000,"repayment_total":2016.6,"apply":4000},{"id":38,"name":"借你花","link":"https://t.ee6e.cn/wap/sms_sem?id=523&pic_id=4","product_logo":"http://or2eh71ll.bkt.clouddn.com/151357123314787.jpg","product_introduction":"审核快低门槛借款，纯线上审核，放款快快快！","interest_algorithm":"未知","min_algorithm":"0.030%","max_algorithm":"0.030%","maximum_amount":200000,"minimum_amount":500,"repayment_total":500.15,"apply":7000},{"id":64,"name":"秒白条","link":"https://m.miaobt.com/wap/w/login/tg1/channel/xianjindai3","product_logo":"http://or2eh71ll.bkt.clouddn.com/152040963261476.png","product_introduction":"全自动化的审批、放款，最快审批59秒通过，审批成功最快18秒到账！","interest_algorithm":"日利率","min_algorithm":"0.010%","max_algorithm":"1.000%","maximum_amount":6000,"minimum_amount":1000,"repayment_total":1000.1,"apply":14000},{"id":71,"name":"来花呗","link":"https://www.ljbei.cn/app/view/channel/into.html#/channelRegister?channel=53285203a2ee4548b7f92d8227435711","product_logo":"http://or2eh71ll.bkt.clouddn.com/152083886193063.png","product_introduction":"仅凭身份证，3分钟下款3000元","interest_algorithm":"未知","min_algorithm":"0.010%","max_algorithm":"0.010%","maximum_amount":3000,"minimum_amount":500,"repayment_total":500.05,"apply":52000},{"id":1,"name":"米米贷","link":"http://www.mimidai.com/expand/xjdslj2","product_logo":"http://or2eh71ll.bkt.clouddn.com/150131715167829.png?e=1501320751&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:DcwqOuHKq8MTwaxqV_TEmb1jbTg=","product_introduction":"极速贷款800、秒批通过后当日到账","interest_algorithm":"日利率","min_algorithm":"0.900%","max_algorithm":"0.900%","maximum_amount":5000,"minimum_amount":500,"repayment_total":504.5,"apply":4000},{"id":23,"name":"51用钱","link":"https://b.jianbing.com/hd/20170905_yongqian/index.php?channel=yqxjd52","product_logo":"http://or2eh71ll.bkt.clouddn.com/150959908211249.png?e=1509602682&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:FCFS6F1bUY_Qj6ow7KyGDVSinV8=","product_introduction":"小额纯信用，15分钟放款","interest_algorithm":"日利率","min_algorithm":"0.070%","max_algorithm":"1.000%","maximum_amount":1000,"minimum_amount":500,"repayment_total":500.35,"apply":11000},{"id":62,"name":"金通财富","link":"http://jtcf.fanzhoutech.com/mobile/applyform?par=8AE2305070A80295DA653C74B2D76CA4","product_logo":"http://or2eh71ll.bkt.clouddn.com/152033027330690.png","product_introduction":"新用户秒过，1000-20000额度5分钟到账","interest_algorithm":"未知","min_algorithm":"0.010%","max_algorithm":"0.010%","maximum_amount":20000,"minimum_amount":1000,"repayment_total":1000.1,"apply":1000},{"id":21,"name":"贝勒爷","link":"http://t.cn/RpUmolh","product_logo":"http://or2eh71ll.bkt.clouddn.com/150953415246048.png?e=1509537752&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:ZM-tZ-fwtIikDvY8wmdIjWqQzlY=","product_introduction":"越借越有钱，越借越容易","interest_algorithm":"未知","min_algorithm":"0.050%","max_algorithm":"0.050%","maximum_amount":5000,"minimum_amount":1000,"repayment_total":1000.5,"apply":0},{"id":63,"name":"银码头","link":"http://8jth.com/ymt/g157","product_logo":"http://or2eh71ll.bkt.clouddn.com/152040565226848.jpg","product_introduction":"一款专业、快捷、方便的借款服务APP，3000额度秒下款。","interest_algorithm":"未知","min_algorithm":"0.030%","max_algorithm":"0.030%","maximum_amount":3000,"minimum_amount":1000,"repayment_total":1000.3,"apply":1000},{"id":9,"name":"51返呗","link":"https://app.51fanbei.com/app/user/channelRegister?channelCode=Sxjdjdd&pointCode=Sxjd704","product_logo":"http://or2eh71ll.bkt.clouddn.com/150131392870015.png?e=1501317529&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:VpQBqosFA5SU2GWaMWEHm_FPhbY=","product_introduction":"身份证借款，最快5分钟到账","interest_algorithm":"未知","min_algorithm":"0.200%","max_algorithm":"0.200%","maximum_amount":5000,"minimum_amount":3000,"repayment_total":3006,"apply":1000},{"id":52,"name":"借立得","link":"http://www.yywwjj.cn/oper/regist?s=xianjindai3","product_logo":"http://or2eh71ll.bkt.clouddn.com/151616260545713.png","product_introduction":"芝麻480秒下款","interest_algorithm":"未知","min_algorithm":"0.300%","max_algorithm":"0.300%","maximum_amount":8000,"minimum_amount":1500,"repayment_total":1504.5,"apply":0},{"id":53,"name":"易兴无忧","link":"http://www.esafe.net.cn/Api/Zt/registerWeb?aid=24","product_logo":"http://or2eh71ll.bkt.clouddn.com/151633668387674.png","product_introduction":"一分钟认证，十分钟审核，半小时放款","interest_algorithm":"未知","min_algorithm":"0.040%","max_algorithm":"0.040%","maximum_amount":10000,"minimum_amount":500,"repayment_total":500.2,"apply":0},{"id":8,"name":"身份贷","link":"https://m.rong360.com/express?from=sem32&utm_source=youyu&utm_medium=cpa&utm_campaign=sem32_xinhe2","product_logo":"http://or2eh71ll.bkt.clouddn.com/150131872622787.png?e=1501322326&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:CS23zu44fz-YSBaTyIbPtBKU_tk=","product_introduction":"借款额度范围大，类型齐全","interest_algorithm":"日利率","min_algorithm":"0.870%","max_algorithm":"4.500%","maximum_amount":500000,"minimum_amount":1000,"repayment_total":1008.7,"apply":46000},{"id":27,"name":"薪动钱包","link":"https://weisudai.prod.creditharmony.cn/MBSPcreditharmony-SJDWeb/common/JSJK/JSJK_NEW.html?channelCode=xd144&channelNickname=xianjind7&mediumType=VERTICAL","product_logo":"http://or2eh71ll.bkt.clouddn.com/151082347270397.png","product_introduction":"低门槛、易操作、低息信用借款，快至30分钟放款","interest_algorithm":"未知","min_algorithm":"0.560%","max_algorithm":"0.560%","maximum_amount":5000,"minimum_amount":1000,"repayment_total":1005.6,"apply":2000}]
     * error_code : 0
     * error_message :
     * time : 2018-06-22 12:11:33
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

    public static class DataBean implements Serializable {
        /**
         * id : 56
         * name : 征信查询
         * link : https://activity.kongapi.com/zxcx/?channel=xjd4zxapp&utm_source=zhengxindaoliu&utm_medium=xjd4zxapp 
         * product_logo : http://or2eh71ll.bkt.clouddn.com/151685513347748.png
         * product_introduction : 低门槛、极速放款、费率低
         * interest_algorithm : 未知
         * min_algorithm : 0.150%
         * max_algorithm : 0.150%
         * maximum_amount : 1000000
         * minimum_amount : 3000
         * repayment_total : 3004.5
         * apply : 10000
         */

        private String id;
        private String name;
        private String link;
        private String product_logo;
        private String product_introduction;
        private String interest_algorithm;
        private String min_algorithm;
        private String max_algorithm;
        private String maximum_amount;
        private String minimum_amount;
        private String repayment_total;
        private String apply;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getInterest_algorithm() {
            return interest_algorithm;
        }

        public void setInterest_algorithm(String interest_algorithm) {
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

        public String getMaximum_amount() {
            return maximum_amount;
        }

        public void setMaximum_amount(String maximum_amount) {
            this.maximum_amount = maximum_amount;
        }

        public String getMinimum_amount() {
            return minimum_amount;
        }

        public void setMinimum_amount(String minimum_amount) {
            this.minimum_amount = minimum_amount;
        }

        public String getRepayment_total() {
            return repayment_total;
        }

        public void setRepayment_total(String repayment_total) {
            this.repayment_total = repayment_total;
        }

        public String getApply() {
            return apply;
        }

        public void setApply(String apply) {
            this.apply = apply;
        }
    }
}
