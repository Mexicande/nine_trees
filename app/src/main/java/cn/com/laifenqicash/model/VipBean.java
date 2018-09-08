package cn.com.laifenqicash.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2018/5/10.
 */

public class VipBean implements Serializable{

    /**
     * service : [{"title":"征信报告","logo":"http://or2eh71ll.bkt.clouddn.com/152586834915073.png","desc":"免费查个人信用","explain":"credit"},{"title":"专属10款产品","logo":"http://or2eh71ll.bkt.clouddn.com/152586862847973.png","desc":"10款产品随意贷","explain":"product"},{"title":"专属客服","logo":"http://or2eh71ll.bkt.clouddn.com/152592040616353.png","desc":"vip服务","explain":"service"},{"title":"专属信用卡","logo":"http://or2eh71ll.bkt.clouddn.com/152592048639651.png","desc":"网申通道","explain":"card"},{"title":"双色球12期","logo":"http://or2eh71ll.bkt.clouddn.com/152592053447126.png","desc":"双色球12期免费送","explain":"double"}]
     * link : http://kakajk.com/protocol
     * price : ￥69.00/年
     */

    private String link;
    private String price;
    private List<ServiceBean> service;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<ServiceBean> getService() {
        return service;
    }

    public void setService(List<ServiceBean> service) {
        this.service = service;
    }

    public static class ServiceBean implements Serializable{
        /**
         * title : 征信报告
         * logo : http://or2eh71ll.bkt.clouddn.com/152586834915073.png
         * desc : 免费查个人信用
         * explain : credit
         */

        private String title;
        private String logo;
        private String desc;
        private String explain;
        private String link;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }
    }
}
