package cn.com.stableloan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/6/12.
 */

public class Product {
    /**
     * pname : 111
     * product_introduction : 321312312
     * id : 27
     * product_logo : http://or2eh71ll.bkt.clouddn.com/149681810015188.jpg?e=1496821721&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:aAttKXByEGpqeL75exNiqAxTaGg=
     * labels : [{"id":"3","name":"22","font":"#ffff","background":"#fff","number":"0","status":"0","created_at":"2017-06-06 16:02:05","updated_at":"2017-06-06 16:02:05"}]
     */
    private String pname;
    private String product_introduction;
    private String id;
    private String product_logo;
    private List<Product.LabelsBean> labels;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_logo() {
        return product_logo;
    }

    public void setProduct_logo(String product_logo) {
        this.product_logo = product_logo;
    }

    public List<Product.LabelsBean> getLabels() {
        return labels;
    }

    public void setLabels(List<Product.LabelsBean> labels) {
        this.labels = labels;
    }

    public static class LabelsBean implements Serializable {
        /**
         * id : 3
         * name : 22
         * font : #ffff
         * background : #fff
         * number : 0
         * status : 0
         * created_at : 2017-06-06 16:02:05
         * updated_at : 2017-06-06 16:02:05
         */

        private String id;
        private String name;
        private String font;
        private String background;
        private String number;
        private String status;
        private String created_at;
        private String updated_at;

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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
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
    }
}
