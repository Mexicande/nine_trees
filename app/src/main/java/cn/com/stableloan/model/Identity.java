package cn.com.stableloan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/7/3.
 */

public class Identity implements Serializable {

    /**
     * code : 200
     * message : 200
     * data : {"identity":{"name":"命苦","userphone":"15600575837","idcard":"61012419921228393X","sex":"1","age":"25","idaddress":"beijing","marriage":"0","city":"beijing","contact":[{"contact":"第一","relation":"1","userphone":"15600575837"},{"contact":"第二","relation":"1","userphone":"18500634223"}],"ilass_time":"2017-08-15 05:27:34"},"isSuccess":"1","status":"1"}
     * error_code : 0
     * error_message :
     * time : 2017-08-16 12:30:03
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
         * identity : {"name":"命苦","userphone":"15600575837","idcard":"61012419921228393X","sex":"1","age":"25","idaddress":"beijing","marriage":"0","city":"beijing","contact":[{"contact":"第一","relation":"1","userphone":"15600575837"},{"contact":"第二","relation":"1","userphone":"18500634223"}],"ilass_time":"2017-08-15 05:27:34"}
         * isSuccess : 1
         * status : 1
         */

        private IdentityBean identity;

        private String isSuccess;
        private String status;
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public IdentityBean getIdentity() {
            return identity;
        }

        public void setIdentity(IdentityBean identity) {
            this.identity = identity;
        }

        public String getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(String isSuccess) {
            this.isSuccess = isSuccess;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public static class IdentityBean implements Serializable {
            @Override
            public String toString() {
                return "{" +
                        "istatus='" + istatus + '\'' +
                        ", name='" + name + '\'' +
                        ", userphone='" + userphone + '\'' +
                        ", idcard='" + idcard + '\'' +
                        ", sex='" + sex + '\'' +
                        ", age='" + age + '\'' +
                        ", idaddress='" + idaddress + '\'' +
                        ", marriage='" + marriage + '\'' +
                        ", city='" + city + '\'' +
                        ", ilass_time='" + ilass_time + '\'' +
                        ", contact=" + contact +
                        '}';
            }

            /**
                     * name : 命苦
                     * userphone : 15600575837
                     * idcard : 61012419921228393X
                     * sex : 1
                     * age : 25
                     * idaddress : beijing
                     * marriage : 0
                     * city : beijing
                     * contact : [{"contact":"第一","relation":"1","userphone":"15600575837"},{"contact":"第二","relation":"1","userphone":"18500634223"}]
                     * ilass_time : 2017-08-15 05:27:34
                     */
            private String istatus;
            public String getIstatus() {
                return istatus;
            }
            public void setIstatus(String istatus) {
                this.istatus = istatus;
            }
            private String name;
            private String userphone;
            private String idcard;
            private String sex;
            private String age;
            private String idaddress;
            private String marriage;
            private String city;
            private String ilass_time;
            private List<ContactBean> contact;

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

            public String getMarriage() {
                return marriage;
            }

            public void setMarriage(String marriage) {
                this.marriage = marriage;
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

            public static class ContactBean implements Serializable {
                /**
                 * contact : 第一
                 * relation : 1
                 * userphone : 15600575837
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
}
