package cn.com.stableloan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/7/3.
 */

public class Identity implements Serializable {


    /**
     * userphone : 18500634223
     * isSuccess : 1
     * identity : {"name":"","userphone":"","idcard":"","sex":"","age":"","idaddress":"","city":"","contact":[{"contact":"","relation":"","userphone":""},{"contact":"","relation":"","userphone":""}],"ilass_time":""}
     * status : 1
     */

    private String userphone;
    private String isSuccess;
    private IdentityBean identity;
    private String status;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public IdentityBean getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityBean identity) {
        this.identity = identity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class IdentityBean {

        /**
         * name :
         * userphone :
         * idcard :
         * sex :
         * age :
         * idaddress :
         * city :
         * contact : [{"contact":"","relation":"","userphone":""},{"contact":"","relation":"","userphone":""}]
         * ilass_time :
         */

        private String name;
        private String userphone;
        private String idcard;
        private String sex;
        private String age;



        public String getMarriage() {
            return marriage;
        }

        public void setMarriage(String marriage) {
            this.marriage = marriage;
        }

        private String marriage;
        private String idaddress;
        private String city;
        private String ilass_time;
        private List<ContactBean> contact;
        private String istatus;

        public String getIstatus() {
            return istatus;
        }

        public void setIstatus(String istatus) {
            this.istatus = istatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserphone() {
            return userphone;
        }

        public void setUserphone(String userphone) {
            this.userphone = userphone;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getIdaddress() {
            return idaddress;
        }

        public void setIdaddress(String idaddress) {
            this.idaddress = idaddress;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getIlass_time() {
            return ilass_time;
        }

        public void setIlass_time(String ilass_time) {
            this.ilass_time = ilass_time;
        }

        public List<ContactBean> getContact() {
            return contact;
        }

        public void setContact(List<ContactBean> contact) {
            this.contact = contact;
        }

        @Override
        public String toString() {
            return "IdentityBean{" +
                    "name='" + name + '\'' +
                    ", userphone='" + userphone + '\'' +
                    ", idcard='" + idcard + '\'' +
                    ", sex='" + sex + '\'' +
                    ", age='" + age + '\'' +
                    ", marriage='" + marriage + '\'' +
                    ", idaddress='" + idaddress + '\'' +
                    ", city='" + city + '\'' +
                    ", ilass_time='" + ilass_time + '\'' +
                    ", contact=" + contact +
                    ", istatus='" + istatus + '\'' +
                    '}';
        }

        public static class ContactBean {

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }

            /**
             * contact :
             * relation :
             * userphone :
             */

            private String contact;
            private String relation;
            private String userphone;

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getRelation() {
                return relation;
            }

            public void setRelation(String relation) {
                this.relation = relation;
            }

            public String getUserphone() {
                return userphone;
            }

            public void setUserphone(String userphone) {
                this.userphone = userphone;
            }

            @Override
            public String toString() {
                return "ContactBean{" +
                        "contact='" + contact + '\'' +
                        ", relation='" + relation + '\'' +
                        ", userphone='" + userphone + '\'' +
                        '}';
            }
        }
    }
}
