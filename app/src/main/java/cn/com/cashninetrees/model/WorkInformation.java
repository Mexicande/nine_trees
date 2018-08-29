package cn.com.cashninetrees.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/16.
 */

public class WorkInformation implements Serializable{

    @Override
    public String toString() {
        return "WorkInformation{" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                ", error_code=" + error_code +
                ", error_message='" + error_message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    /**
     * code : 200
     * message : 200
     * data : {"isSuccess":"1","occupation":{"student":{"school":"1","address":"进\"\u2018哎\"ueoeow","preTeacherphone":"86","teacherphone":"15611111111"},"company":{"company":"还记得就觉得","location":"那些奖学金","years":"1","email":"123@yahoo.cn","cincome":"2000","preFixedline":"022","fixedline":"1563248"},"business":{"operations":"0","license":"0","bincome":"2"},"freelancer":{"source":"1"},"olass_time":"2017-07-19 12:31:18"},"status":"1"}
     * error_code : 0
     * error_message :
     * time : 2017-08-16 18:52:53
     */

    private int code;
    private String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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
        @Override
        public String toString() {
            return "DataBean{" +
                    "token='" + token + '\'' +
                    ", isSuccess='" + isSuccess + '\'' +
                    ", occupation=" + occupation +
                    ", status='" + status + '\'' +
                    ", identity='" + identity + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DataBean)) return false;

            DataBean dataBean = (DataBean) o;

            if (getToken() != null ? !getToken().equals(dataBean.getToken()) : dataBean.getToken() != null)
                return false;
            if (getIsSuccess() != null ? !getIsSuccess().equals(dataBean.getIsSuccess()) : dataBean.getIsSuccess() != null)
                return false;
            if (getOccupation() != null ? !getOccupation().equals(dataBean.getOccupation()) : dataBean.getOccupation() != null)
                return false;
            if (getStatus() != null ? !getStatus().equals(dataBean.getStatus()) : dataBean.getStatus() != null)
                return false;
            return getIdentity() != null ? getIdentity().equals(dataBean.getIdentity()) : dataBean.getIdentity() == null;

        }

        @Override
        public int hashCode() {
            int result = getToken() != null ? getToken().hashCode() : 0;
            result = 31 * result + (getIsSuccess() != null ? getIsSuccess().hashCode() : 0);
            result = 31 * result + (getOccupation() != null ? getOccupation().hashCode() : 0);
            result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
            result = 31 * result + (getIdentity() != null ? getIdentity().hashCode() : 0);
            return result;
        }

        /**
         * isSuccess : 1
         * occupation : {"student":{"school":"1","address":"进\"\u2018哎\"ueoeow","preTeacherphone":"86","teacherphone":"15611111111"},"company":{"company":"还记得就觉得","location":"那些奖学金","years":"1","email":"123@yahoo.cn","cincome":"2000","preFixedline":"022","fixedline":"1563248"},"business":{"operations":"0","license":"0","bincome":"2"},"freelancer":{"source":"1"},"olass_time":"2017-07-19 12:31:18"}
         * status : 1
         */
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        private String isSuccess;
        private OccupationBean occupation;
        private String status;
        private String identity;

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(String isSuccess) {
            this.isSuccess = isSuccess;
        }

        public OccupationBean getOccupation() {
            return occupation;
        }

        public void setOccupation(OccupationBean occupation) {
            this.occupation = occupation;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public static class OccupationBean implements Serializable{
            @Override
            public String toString() {
                return "OccupationBean{" +
                        "student=" + student +
                        ", company=" + company +
                        ", business=" + business +
                        ", freelancer=" + freelancer +
                        ", olass_time='" + olass_time + '\'' +
                        ", status='" + status + '\'' +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof OccupationBean)) return false;

                OccupationBean that = (OccupationBean) o;

                if (getStudent() != null ? !getStudent().equals(that.getStudent()) : that.getStudent() != null)
                    return false;
                if (getCompany() != null ? !getCompany().equals(that.getCompany()) : that.getCompany() != null)
                    return false;
                if (getBusiness() != null ? !getBusiness().equals(that.getBusiness()) : that.getBusiness() != null)
                    return false;
                if (getFreelancer() != null ? !getFreelancer().equals(that.getFreelancer()) : that.getFreelancer() != null)
                    return false;
                if (getOlass_time() != null ? !getOlass_time().equals(that.getOlass_time()) : that.getOlass_time() != null)
                    return false;
                return getStatus() != null ? getStatus().equals(that.getStatus()) : that.getStatus() == null;

            }

            @Override
            public int hashCode() {
                int result = getStudent() != null ? getStudent().hashCode() : 0;
                result = 31 * result + (getCompany() != null ? getCompany().hashCode() : 0);
                result = 31 * result + (getBusiness() != null ? getBusiness().hashCode() : 0);
                result = 31 * result + (getFreelancer() != null ? getFreelancer().hashCode() : 0);
                result = 31 * result + (getOlass_time() != null ? getOlass_time().hashCode() : 0);
                result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
                return result;
            }

            /**
             * student : {"school":"1","address":"进\"\u2018哎\"ueoeow","preTeacherphone":"86","teacherphone":"15611111111"}
             * company : {"company":"还记得就觉得","location":"那些奖学金","years":"1","email":"123@yahoo.cn","cincome":"2000","preFixedline":"022","fixedline":"1563248"}
             * business : {"operations":"0","license":"0","bincome":"2"}
             * freelancer : {"source":"1"}
             * olass_time : 2017-07-19 12:31:18
             */

            private StudentBean student;
            private CompanyBean company;
            private BusinessBean business;
            private FreelancerBean freelancer;
            private String olass_time;

            private String  status;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
            public StudentBean getStudent() {
                return student;
            }

            public void setStudent(StudentBean student) {
                this.student = student;
            }

            public CompanyBean getCompany() {
                return company;
            }

            public void setCompany(CompanyBean company) {
                this.company = company;
            }

            public BusinessBean getBusiness() {
                return business;
            }

            public void setBusiness(BusinessBean business) {
                this.business = business;
            }

            public FreelancerBean getFreelancer() {
                return freelancer;
            }

            public void setFreelancer(FreelancerBean freelancer) {
                this.freelancer = freelancer;
            }

            public String getOlass_time() {
                return olass_time;
            }

            public void setOlass_time(String olass_time) {
                this.olass_time = olass_time;
            }

            public static class StudentBean implements Serializable{
                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (!(o instanceof StudentBean)) return false;

                    StudentBean that = (StudentBean) o;

                    if (!getSchool().equals(that.getSchool())) return false;
                    if (!getAddress().equals(that.getAddress())) return false;
                    if (!getPreTeacherphone().equals(that.getPreTeacherphone())) return false;
                    return getTeacherphone().equals(that.getTeacherphone());

                }

                @Override
                public int hashCode() {
                    int result = getSchool().hashCode();
                    result = 31 * result + getAddress().hashCode();
                    result = 31 * result + getPreTeacherphone().hashCode();
                    result = 31 * result + getTeacherphone().hashCode();
                    return result;
                }

                /**
                 * school : 1
                 * address : 进"‘哎"ueoeow
                 * preTeacherphone : 86
                 * teacherphone : 15611111111
                 */

                private String school;
                private String address;
                private String preTeacherphone;
                private String teacherphone;

                public String getSchool() {
                    return school;
                }

                public void setSchool(String school) {
                    this.school = school;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getPreTeacherphone() {
                    return preTeacherphone;
                }

                public void setPreTeacherphone(String preTeacherphone) {
                    this.preTeacherphone = preTeacherphone;
                }

                public String getTeacherphone() {
                    return teacherphone;
                }

                public void setTeacherphone(String teacherphone) {
                    this.teacherphone = teacherphone;
                }

                @Override
                public String toString() {
                    return "StudentBean{" +
                            "school='" + school + '\'' +
                            ", address='" + address + '\'' +
                            ", preTeacherphone='" + preTeacherphone + '\'' +
                            ", teacherphone='" + teacherphone + '\'' +
                            '}';
                }
            }

            public static class CompanyBean implements Serializable{
                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (!(o instanceof CompanyBean)) return false;

                    CompanyBean that = (CompanyBean) o;

                    if (!getCompany().equals(that.getCompany())) return false;
                    if (!getLocation().equals(that.getLocation())) return false;
                    if (!getYears().equals(that.getYears())) return false;
                    if (!getEmail().equals(that.getEmail())) return false;
                    if (!getCincome().equals(that.getCincome())) return false;
                    if (!getPreFixedline().equals(that.getPreFixedline())) return false;
                    return getFixedline().equals(that.getFixedline());

                }

                @Override
                public int hashCode() {
                    int result = getCompany().hashCode();
                    result = 31 * result + getLocation().hashCode();
                    result = 31 * result + getYears().hashCode();
                    result = 31 * result + getEmail().hashCode();
                    result = 31 * result + getCincome().hashCode();
                    result = 31 * result + getPreFixedline().hashCode();
                    result = 31 * result + getFixedline().hashCode();
                    return result;
                }

                /**
                 * company : 还记得就觉得
                 * location : 那些奖学金
                 * years : 1
                 * email : 123@yahoo.cn
                 * cincome : 2000
                 * preFixedline : 022
                 * fixedline : 1563248
                 */

                private String company;
                private String location;
                private String years;
                private String email;
                private String cincome;
                private String preFixedline;
                private String fixedline;

                public String getCompany() {
                    return company;
                }

                public void setCompany(String company) {
                    this.company = company;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getYears() {
                    return years;
                }

                public void setYears(String years) {
                    this.years = years;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getCincome() {
                    return cincome;
                }

                public void setCincome(String cincome) {
                    this.cincome = cincome;
                }

                public String getPreFixedline() {
                    return preFixedline;
                }

                public void setPreFixedline(String preFixedline) {
                    this.preFixedline = preFixedline;
                }

                public String getFixedline() {
                    return fixedline;
                }

                public void setFixedline(String fixedline) {
                    this.fixedline = fixedline;
                }

                @Override
                public String toString() {
                    return "CompanyBean{" +
                            "company='" + company + '\'' +
                            ", location='" + location + '\'' +
                            ", years='" + years + '\'' +
                            ", email='" + email + '\'' +
                            ", cincome='" + cincome + '\'' +
                            ", preFixedline='" + preFixedline + '\'' +
                            ", fixedline='" + fixedline + '\'' +
                            '}';
                }
            }

            public static class BusinessBean implements Serializable{

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (!(o instanceof BusinessBean)) return false;

                    BusinessBean that = (BusinessBean) o;

                    if (!getBcompany().equals(that.getBcompany())) return false;
                    if (!getBlocation().equals(that.getBlocation())) return false;
                    if (!getByears().equals(that.getByears())) return false;
                    if (!getBemail().equals(that.getBemail())) return false;
                    if (!getBcincome().equals(that.getBcincome())) return false;
                    if (!getBpreFixedline().equals(that.getBpreFixedline())) return false;
                    if (!getBfixedline().equals(that.getBfixedline())) return false;
                    if (!getOperations().equals(that.getOperations())) return false;
                    return getLicense().equals(that.getLicense());

                }

                @Override
                public int hashCode() {
                    int result = getBcompany().hashCode();
                    result = 31 * result + getBlocation().hashCode();
                    result = 31 * result + getByears().hashCode();
                    result = 31 * result + getBemail().hashCode();
                    result = 31 * result + getBcincome().hashCode();
                    result = 31 * result + getBpreFixedline().hashCode();
                    result = 31 * result + getBfixedline().hashCode();
                    result = 31 * result + getOperations().hashCode();
                    result = 31 * result + getLicense().hashCode();
                    return result;
                }

                /**
                 * operations : 0
                 * license : 0
                 * bincome : 2
                 */
                private String bcompany;
                private String blocation;
                private String byears;
                private String bemail;
                private String bcincome;
                private String bpreFixedline;
                private String bfixedline;
                private String operations;

                public String getBcompany() {
                    return bcompany;
                }

                public void setBcompany(String bcompany) {
                    this.bcompany = bcompany;
                }

                public String getBlocation() {
                    return blocation;
                }

                public void setBlocation(String blocation) {
                    this.blocation = blocation;
                }

                public String getByears() {
                    return byears;
                }

                public void setByears(String byears) {
                    this.byears = byears;
                }

                public String getBemail() {
                    return bemail;
                }

                public void setBemail(String bemail) {
                    this.bemail = bemail;
                }

                public String getBcincome() {
                    return bcincome;
                }

                public void setBcincome(String bcincome) {
                    this.bcincome = bcincome;
                }

                public String getBpreFixedline() {
                    return bpreFixedline;
                }

                public void setBpreFixedline(String bpreFixedline) {
                    this.bpreFixedline = bpreFixedline;
                }

                public String getBfixedline() {
                    return bfixedline;
                }

                public void setBfixedline(String bfixedline) {
                    this.bfixedline = bfixedline;
                }

                private String license;

                public String getOperations() {
                    return operations;
                }

                public void setOperations(String operations) {
                    this.operations = operations;
                }

                public String getLicense() {
                    return license;
                }

                @Override
                public String toString() {
                    return "BusinessBean{" +
                            "bcompany='" + bcompany + '\'' +
                            ", blocation='" + blocation + '\'' +
                            ", byears='" + byears + '\'' +
                            ", bemail='" + bemail + '\'' +
                            ", bcincome='" + bcincome + '\'' +
                            ", bpreFixedline='" + bpreFixedline + '\'' +
                            ", bfixedline='" + bfixedline + '\'' +
                            ", operations='" + operations + '\'' +
                            ", license='" + license + '\'' +
                            '}';
                }

                public void setLicense(String license) {
                    this.license = license;
                }

            }

            public static class FreelancerBean implements Serializable {
                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (!(o instanceof FreelancerBean)) return false;

                    FreelancerBean that = (FreelancerBean) o;

                    return getSource().equals(that.getSource());

                }

                @Override
                public int hashCode() {
                    return getSource().hashCode();
                }

                /**
                 * source : 1
                 */

                private String source;

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                @Override
                public String toString() {
                    return "FreelancerBean{" +
                            "source='" + source + '\'' +
                            '}';
                }
            }
        }
    }
}
