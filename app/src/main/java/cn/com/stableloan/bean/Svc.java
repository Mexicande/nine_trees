package cn.com.stableloan.bean;

import java.util.List;

/**
 * Created by apple on 2017/5/25.
 */

public class Svc {


    /**
     * value : 0
     * liveInfo : {"addressProvince":"1","addressCity":"2","addressDistrict":"7","address":"Soho现代城2","tel":"18600885905"}
     * jobInfo : {"name":"北京信合","tel":"01057122950","office":"总经理","income":"3","addressProvince":"1","addressCity":"2","addressDistrict":"13","address":"旗舰凯旋","type":"5","jobEmail":"uowarm@163.com","education":"5","marrage":"1"}
     * contactInfo : [{"linkman":"0","name":"王雪","phone":"18943969777","seq":"1"},{"linkman":"1","name":"孙贺","phone":"18611665905","seq":"2"},{"linkman":"3","name":"高哲","phone":"18643969888","seq":"3"},{"linkman":"2","name":"王东","phone":"18612345689","seq":"4"}]
     * userCode : 20170523293
     * userPhone : 18600885905
     * alipayScore : 0
     * info : {"liveinfo":{"province":"北京市","city":"北京市","district":"海淀区"},"jobinfo":{"province":"北京市","city":"北京市","district":"朝阳区"}}
     */

    private String value;
    private LiveInfoBean liveInfo;
    private JobInfoBean jobInfo;
    private String userCode;
    private String userPhone;
    private String alipayScore;
    private InfoBean info;
    private List<ContactInfoBean> contactInfo;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LiveInfoBean getLiveInfo() {
        return liveInfo;
    }

    public void setLiveInfo(LiveInfoBean liveInfo) {
        this.liveInfo = liveInfo;
    }

    public JobInfoBean getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(JobInfoBean jobInfo) {
        this.jobInfo = jobInfo;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAlipayScore() {
        return alipayScore;
    }

    public void setAlipayScore(String alipayScore) {
        this.alipayScore = alipayScore;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<ContactInfoBean> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(List<ContactInfoBean> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public static class LiveInfoBean {
        /**
         * addressProvince : 1
         * addressCity : 2
         * addressDistrict : 7
         * address : Soho现代城2
         * tel : 18600885905
         */

        private String addressProvince;
        private String addressCity;
        private String addressDistrict;
        private String address;
        private String tel;

        public String getAddressProvince() {
            return addressProvince;
        }

        public void setAddressProvince(String addressProvince) {
            this.addressProvince = addressProvince;
        }

        public String getAddressCity() {
            return addressCity;
        }

        public void setAddressCity(String addressCity) {
            this.addressCity = addressCity;
        }

        public String getAddressDistrict() {
            return addressDistrict;
        }

        public void setAddressDistrict(String addressDistrict) {
            this.addressDistrict = addressDistrict;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }

    public static class JobInfoBean {
        /**
         * name : 北京信合
         * tel : 01057122950
         * office : 总经理
         * income : 3
         * addressProvince : 1
         * addressCity : 2
         * addressDistrict : 13
         * address : 旗舰凯旋
         * type : 5
         * jobEmail : uowarm@163.com
         * education : 5
         * marrage : 1
         */

        private String name;
        private String tel;
        private String office;
        private String income;
        private String addressProvince;
        private String addressCity;
        private String addressDistrict;
        private String address;
        private String type;
        private String jobEmail;
        private String education;
        private String marrage;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getOffice() {
            return office;
        }

        public void setOffice(String office) {
            this.office = office;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getAddressProvince() {
            return addressProvince;
        }

        public void setAddressProvince(String addressProvince) {
            this.addressProvince = addressProvince;
        }

        public String getAddressCity() {
            return addressCity;
        }

        public void setAddressCity(String addressCity) {
            this.addressCity = addressCity;
        }

        public String getAddressDistrict() {
            return addressDistrict;
        }

        public void setAddressDistrict(String addressDistrict) {
            this.addressDistrict = addressDistrict;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getJobEmail() {
            return jobEmail;
        }

        public void setJobEmail(String jobEmail) {
            this.jobEmail = jobEmail;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getMarrage() {
            return marrage;
        }

        public void setMarrage(String marrage) {
            this.marrage = marrage;
        }
    }

    public static class InfoBean {
        /**
         * liveinfo : {"province":"北京市","city":"北京市","district":"海淀区"}
         * jobinfo : {"province":"北京市","city":"北京市","district":"朝阳区"}
         */

        private LiveinfoBean liveinfo;
        private JobinfoBean jobinfo;

        public LiveinfoBean getLiveinfo() {
            return liveinfo;
        }

        public void setLiveinfo(LiveinfoBean liveinfo) {
            this.liveinfo = liveinfo;
        }

        public JobinfoBean getJobinfo() {
            return jobinfo;
        }

        public void setJobinfo(JobinfoBean jobinfo) {
            this.jobinfo = jobinfo;
        }

        public static class LiveinfoBean {
            /**
             * province : 北京市
             * city : 北京市
             * district : 海淀区
             */

            private String province;
            private String city;
            private String district;

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }
        }

        public static class JobinfoBean {
            /**
             * province : 北京市
             * city : 北京市
             * district : 朝阳区
             */

            private String province;
            private String city;
            private String district;

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }
        }
    }

    public static class ContactInfoBean {
        /**
         * linkman : 0
         * name : 王雪
         * phone : 18943969777
         * seq : 1
         */

        private String linkman;
        private String name;
        private String phone;
        private String seq;

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }
    }
}
