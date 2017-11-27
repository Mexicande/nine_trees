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
        private String app_code;
        private IdentityBean identity;

        public String getApp_code() {
            return app_code;
        }

        public void setApp_code(String app_code) {
            this.app_code = app_code;
        }

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
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof IdentityBean)) return false;

                IdentityBean that = (IdentityBean) o;

                if (getIstatus() != null ? !getIstatus().equals(that.getIstatus()) : that.getIstatus() != null)
                    return false;
                if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
                    return false;
                if (getUserphone() != null ? !getUserphone().equals(that.getUserphone()) : that.getUserphone() != null)
                    return false;
                if (getIdcard() != null ? !getIdcard().equals(that.getIdcard()) : that.getIdcard() != null)
                    return false;
                if (getSex() != null ? !getSex().equals(that.getSex()) : that.getSex() != null)
                    return false;
                if (getAge() != null ? !getAge().equals(that.getAge()) : that.getAge() != null)
                    return false;
                if (getIdaddress() != null ? !getIdaddress().equals(that.getIdaddress()) : that.getIdaddress() != null)
                    return false;
                if (getMarriage() != null ? !getMarriage().equals(that.getMarriage()) : that.getMarriage() != null)
                    return false;
                if (getCity() != null ? !getCity().equals(that.getCity()) : that.getCity() != null)
                    return false;
                if (getIlass_time() != null ? !getIlass_time().equals(that.getIlass_time()) : that.getIlass_time() != null)
                    return false;
                return getContact() != null ? getContact().equals(that.getContact()) : that.getContact() == null;

            }

            @Override
            public int hashCode() {
                int result = getIstatus() != null ? getIstatus().hashCode() : 0;
                result = 31 * result + (getName() != null ? getName().hashCode() : 0);
                result = 31 * result + (getUserphone() != null ? getUserphone().hashCode() : 0);
                result = 31 * result + (getIdcard() != null ? getIdcard().hashCode() : 0);
                result = 31 * result + (getSex() != null ? getSex().hashCode() : 0);
                result = 31 * result + (getAge() != null ? getAge().hashCode() : 0);
                result = 31 * result + (getIdaddress() != null ? getIdaddress().hashCode() : 0);
                result = 31 * result + (getMarriage() != null ? getMarriage().hashCode() : 0);
                result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
                result = 31 * result + (getIlass_time() != null ? getIlass_time().hashCode() : 0);
                result = 31 * result + (getContact() != null ? getContact().hashCode() : 0);
                return result;
            }

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
                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (!(o instanceof ContactBean)) return false;

                    ContactBean that = (ContactBean) o;

                    if (getContact() != null ? !getContact().equals(that.getContact()) : that.getContact() != null)
                        return false;
                    if (getRelation() != null ? !getRelation().equals(that.getRelation()) : that.getRelation() != null)
                        return false;
                    return getUserphone() != null ? getUserphone().equals(that.getUserphone()) : that.getUserphone() == null;

                }

                @Override
                public int hashCode() {
                    int result = getContact() != null ? getContact().hashCode() : 0;
                    result = 31 * result + (getRelation() != null ? getRelation().hashCode() : 0);
                    result = 31 * result + (getUserphone() != null ? getUserphone().hashCode() : 0);
                    return result;
                }

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
