package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/16.
 */

public class WorkInformation implements Serializable{


    /**
     * code : 200
     * message : 200
     * data : {"isSuccess":"1","occupation":{"student":{"school":"1","address":"进\"\u2018哎\"ueoeow","preTeacherphone":"86","teacherphone":"15611111111"},"company":{"company":"还记得就觉得","location":"那些奖学金","years":"1","email":"123@yahoo.cn","cincome":"2000","preFixedline":"022","fixedline":"1563248"},"business":{"operations":"0","license":"0","bincome":"2"},"freelancer":{"source":"1"},"olass_time":"2017-07-19 12:31:18"},"status":"1"}
     * error_code : 0
     * error_message :
     * time : 2017-08-16 18:52:53
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            private String  status;
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
            }

            public static class BusinessBean implements Serializable{
                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (!(o instanceof BusinessBean)) return false;

                    BusinessBean that = (BusinessBean) o;

                    if (!getOperations().equals(that.getOperations())) return false;
                    return getLicense().equals(that.getLicense());

                }

                @Override
                public int hashCode() {
                    int result = getOperations().hashCode();
                    result = 31 * result + getLicense().hashCode();
                    return result;
                }

                /**
                 * operations : 0
                 * license : 0
                 * bincome : 2
                 */

                private String operations;
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
            }
        }
    }
}
