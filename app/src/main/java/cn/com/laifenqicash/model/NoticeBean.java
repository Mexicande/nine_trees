package cn.com.laifenqicash.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/6/9.
 */

public class NoticeBean implements Serializable {
    /**
     * Announcements : [{"name":"1","image":"http://or2eh71ll.bkt.clouddn.com/149674417594360.jpg?e=1496747775&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:cKq9dvN0K6NbakSxEd9c8jHqZps=","content":"1","last_time":"2017-06-06 18:14:58"},{"name":"312321","image":"http://or2eh71ll.bkt.clouddn.com/149682631559978.jpg?e=1496829916&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:zIjq3NhvB0JR2rzNxAhPH_IGAco=","content":"发大水发生的发生发打发第三方的撒","last_time":"2017-06-07 17:05:15"}]
     * isSuccess : true
     */

    private boolean isSuccess;
    private List<AnnouncementsBean> Announcements;

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public List<AnnouncementsBean> getAnnouncements() {
        return Announcements;
    }

    public void setAnnouncements(List<AnnouncementsBean> Announcements) {
        this.Announcements = Announcements;
    }

    public static class AnnouncementsBean implements Serializable{
        /**
         * name : 1
         * image : http://or2eh71ll.bkt.clouddn.com/149674417594360.jpg?e=1496747775&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:cKq9dvN0K6NbakSxEd9c8jHqZps=
         * content : 1
         * last_time : 2017-06-06 18:14:58
         */

        private String name;
        private String image;
        private String content;
        private String last_time;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }
    }
}

