package cn.com.stableloan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/6/12.
 */

public class Class_ListProductBean implements Serializable{

    /**
     * image : http://or2eh71ll.bkt.clouddn.com/149715014330539.png?e=1497153764&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:FbwRiNcuYzvh-urGLyDDVSynMo0=
     * product : [{"pname":"秒啦","product_introduction,":"30秒申请，秒批结果，快至2小时到款","id":"27","product_logo":"http://or2eh71ll.bkt.clouddn.com/149701120799042.png?e=1497014807&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:fCT6ea-Je57Jp2FFwgv8t6nr0x8=","average_time":"4小时","min_algorithm":"0.4%","lables":[{"id":"27","labels":[{"id":"3","name":"2","font":"#FF0","background":"#888","number":"0","status":"0","created_at":"2017-06-06 16:03:12","updated_at":"2017-06-06 16:03:12"}]}]},{"pname":"林志玲","product_introduction,":"喏翻墙翻墙缫䗄启发启发 ","id":"28","product_logo":"http://or2eh71ll.bkt.clouddn.com/149681756348774.jpg?e=1496821167&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:pr9qT9M5O3bRT9zFBw_bq48Ss9E=","average_time":"1小时","min_algorithm":"0.1","lables":[{"id":"28","labels":[]}]},{"pname":"DADADA","product_introduction,":"fdsafdsafasfdafdsafdsaf","id":"29","product_logo":"http://or2eh71ll.bkt.clouddn.com/149682450718849.jpg?e=1496828111&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:k76kLFZFwJewANlT9DoDQlh2JAE=","average_time":"范德萨","min_algorithm":"1","lables":[{"id":"29","labels":[]}]},{"pname":"任信用","product_introduction,":"信用卡极速贷,极速借款任性用","id":"30","product_logo":"http://or2eh71ll.bkt.clouddn.com/149701326470477.png?e=1497016864&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:g_bKERwo6ZgCckbckkQl58k2y-I=","average_time":"4小时","min_algorithm":"0.05","lables":[{"id":"30","labels":[{"id":"7","name":"一次还清","font":"e25a5a","background":"fbe8e8","number":"0","status":"1","created_at":"2017-06-09 20:29:56","updated_at":"2017-06-09 20:29:56"}]}]},{"pname":"学子速贷","product_introduction,":"大学生专属，通过率高，高效快速放款","id":"32","product_logo":"http://or2eh71ll.bkt.clouddn.com/149701337726906.png?e=1497016999&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:sHqFDfi7Y9mPdqAbO4pyvTu7EKg=","average_time":"12小时","min_algorithm":"0.5","lables":[{"id":"32","labels":[{"id":"7","name":"一次还清","font":"e25a5a","background":"fbe8e8","number":"0","status":"1","created_at":"2017-06-09 20:29:56","updated_at":"2017-06-09 20:29:56"},{"id":"3","name":"2","font":"#FF0","background":"#888","number":"0","status":"0","created_at":"2017-06-06 16:03:12","updated_at":"2017-06-06 16:03:12"}]}]}]
     * isSuccess : true
     */
    private String image;
    private boolean isSuccess;
    private ArrayList<ProductBean> product;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ArrayList<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<ProductBean> product) {
        this.product = product;
    }

    public static class ProductBean implements Serializable{
        /**
         * pname : 秒啦
         * product_introduction, : 30秒申请，秒批结果，快至2小时到款
         * id : 27
         * product_logo : http://or2eh71ll.bkt.clouddn.com/149701120799042.png?e=1497014807&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:fCT6ea-Je57Jp2FFwgv8t6nr0x8=
         * average_time : 4小时
         * min_algorithm : 0.4%
         * lables : [{"id":"27","labels":[{"id":"3","name":"2","font":"#FF0","background":"#888","number":"0","status":"0","created_at":"2017-06-06 16:03:12","updated_at":"2017-06-06 16:03:12"}]}]
         */
        private String fastest_time;
        private String pname;
        private String product_introduction; // FIXME check this code
        private String id;
        private String product_logo;
        private String average_time;
        private String min_algorithm;
        private String link;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        private ArrayList<LabelsBean> labels;

        public String getFastest_time() {
            return fastest_time;
        }

        public void setFastest_time(String fastest_time) {
            this.fastest_time = fastest_time;
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

        public ArrayList<LabelsBean> getLabels() {
            return labels;
        }

        public void setLabels(ArrayList<LabelsBean> labels) {
            this.labels = labels;
        }

        public static class LabelsBean implements Serializable{
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
}
