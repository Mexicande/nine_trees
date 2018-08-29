package cn.com.cashninetrees.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/6/7.
 *
 * Banner和热门推荐
 */

public class Banner_HotBean implements Serializable {

    /**
     * recommends : [{"name":"爱啥啥","pictrue":"http://or2eh71ll.bkt.clouddn.com/149680928231464.jpg?e=1496812904&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:5IdJ5oL7XXJUkWqPmCBgIWzYY9U=","app":"productid111","h5":"http://www.baidu.com"},{"name":"莎莎哎","pictrue":"http://or2eh71ll.bkt.clouddn.com/149680935580680.jpg?e=1496812956&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:U_yE0Q7-TfAXoXhTghDUbnSy8bo=","app":"productid27","h5":"http://www.baidu.com"}]
     * advertising : [{"advername":"66666","pictrue":"http://or2eh71ll.bkt.clouddn.com/149680864422878.jpg?e=1496812244&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:X7r5BdO24Vih7j9-rhCfJQDJkVs=","app":"productid27","h5":"http://www.baidu.com"},{"advername":"linzhiling","pictrue":"http://or2eh71ll.bkt.clouddn.com/149680877088660.jpg?e=1496812392&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:rXoH3kCmxYz3ayhDzP8LCiSfOv0=","app":"productid27","h5":"http://www.baidu.com"},{"advername":"000","pictrue":"http://or2eh71ll.bkt.clouddn.com/149672100031727.jpg?e=1496724602&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:ykKthKxSXUq-TwtIwMtmSW6-vzo=","app":"0000","h5":"0000"}]
     * isSuccess : true
     */
    private boolean isSuccess;
    private List<RecommendsBean> recommends;
    private List<AdvertisingBean> advertising;

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public List<RecommendsBean> getRecommends() {
        return recommends;
    }

    public void setRecommends(List<RecommendsBean> recommends) {
        this.recommends = recommends;
    }

    public List<AdvertisingBean> getAdvertising() {
        return advertising;
    }

    public void setAdvertising(List<AdvertisingBean> advertising) {
        this.advertising = advertising;
    }

    public static class RecommendsBean implements Serializable{
        /**
         * name : 爱啥啥
         * pictrue : http://or2eh71ll.bkt.clouddn.com/149680928231464.jpg?e=1496812904&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:5IdJ5oL7XXJUkWqPmCBgIWzYY9U=
         * app : productid111
         * h5 : http://www.baidu.com
         */

        private String name;
        private String pictrue;
        private String app;
        private String h5;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPictrue() {
            return pictrue;
        }

        public void setPictrue(String pictrue) {
            this.pictrue = pictrue;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getH5() {
            return h5;
        }

        public void setH5(String h5) {
            this.h5 = h5;
        }
    }

    public static class AdvertisingBean implements Serializable{
        /**
         * advername : 66666
         * pictrue : http://or2eh71ll.bkt.clouddn.com/149680864422878.jpg?e=1496812244&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:X7r5BdO24Vih7j9-rhCfJQDJkVs=
         * app : productid27
         * h5 : http://www.baidu.com
         */

        private String advername;
        private String pictrue;
        private String app;
        private String h5;

        public String getAdvername() {
            return advername;
        }

        public void setAdvername(String advername) {
            this.advername = advername;
        }

        public String getPictrue() {
            return pictrue;
        }

        public void setPictrue(String pictrue) {
            this.pictrue = pictrue;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getH5() {
            return h5;
        }

        public void setH5(String h5) {
            this.h5 = h5;
        }


    }

}
