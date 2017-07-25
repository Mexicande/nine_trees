package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/4.
 */

public class WorkBean implements Serializable{

    @Override
    public String toString() {
        return "WorkBean{" +
                "token='" + token + '\'' +
                ", isSuccess='" + isSuccess + '\'' +
                ", occupation=" + occupation +
                '}';
    }

    /**
     * isSuccess : 1
     * occupation : {"student":{"school":"家里蹲","address":"北京市","information":"四年制","freelancer":"0","teacherphone":"15849275060"},"company":{"company":"嗨钱科技","location":"大望路","years":"5","email":"1264506194@qq.com","cincome":"12000w","fixedline":"15849275060"},"business":{"operations":"5","license":"1","bincome":"50w","employment":"牢固","nature":"私企"},"freelancer":{"source":"1"},"olass_time":"08:00:00"}
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



    public static class OccupationBean implements Serializable{
        @Override
        public String toString() {
            return "OccupationBean{" +
                    "status='" + status + '\'' +
                    ", student=" + student +
                    ", company=" + company +
                    ", business=" + business +
                    ", freelancer=" + freelancer +
                    ", olass_time='" + olass_time + '\'' +
                    '}';
        }

        /**
         * student : {"school":"家里蹲","address":"北京市","information":"四年制","freelancer":"0","teacherphone":"15849275060"}
         * company : {"company":"嗨钱科技","location":"大望路","years":"5","email":"1264506194@qq.com","cincome":"12000w","fixedline":"15849275060"}
         * business : {"operations":"5","license":"1","bincome":"50w","employment":"牢固","nature":"私企"}
         * freelancer : {"source":"1"}
         * olass_time : 08:00:00
         */
        private String status ;
        private StudentBean student;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private CompanyBean company;
        private BusinessBean business;
        private FreelancerBean freelancer;
        private String olass_time;

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

        public static class StudentBean {
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof StudentBean)) return false;

                StudentBean that = (StudentBean) o;

                if (!getSchool().equals(that.getSchool())) return false;
                if (!getAddress().equals(that.getAddress())) return false;
                return getTeacherphone().equals(that.getTeacherphone());

            }

            @Override
            public int hashCode() {
                int result = getSchool().hashCode();
                result = 31 * result + getAddress().hashCode();
                result = 31 * result + getTeacherphone().hashCode();
                return result;
            }

            /**
             * school : 家里蹲
             * address : 北京市
             * information : 四年制
             * freelancer : 0
             * teacherphone : 15849275060
             */

            private String school;
            private String address;
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




            public String getTeacherphone() {
                return teacherphone;
            }

            public void setTeacherphone(String teacherphone) {
                this.teacherphone = teacherphone;
            }
        }

        public static class CompanyBean {
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
                return getFixedline().equals(that.getFixedline());

            }

            @Override
            public int hashCode() {
                int result = getCompany().hashCode();
                result = 31 * result + getLocation().hashCode();
                result = 31 * result + getYears().hashCode();
                result = 31 * result + getEmail().hashCode();
                result = 31 * result + getCincome().hashCode();
                result = 31 * result + getFixedline().hashCode();
                return result;
            }

            /**
             * company : 嗨钱科技
             * location : 大望路
             * years : 5
             * email : 1264506194@qq.com
             * cincome : 12000w
             * fixedline : 15849275060
             */

            private String company;
            private String location;
            private String years;
            private String email;
            private String cincome;
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
                        ", fixedline='" + fixedline + '\'' +
                        '}';
            }
        }

        public static class BusinessBean {
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof BusinessBean)) return false;

                BusinessBean that = (BusinessBean) o;

                if (!getOperations().equals(that.getOperations())) return false;
                if (!getLicense().equals(that.getLicense())) return false;
                return getBincome().equals(that.getBincome());

            }

            @Override
            public int hashCode() {
                int result = getOperations().hashCode();
                result = 31 * result + getLicense().hashCode();
                result = 31 * result + getBincome().hashCode();
                return result;
            }

            /**
             * operations : 5
             * license : 1
             * bincome : 50w
             * employment : 牢固
             */

            private String operations;
            private String license;
            private String bincome;

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

            public String getBincome() {
                return bincome;
            }

            public void setBincome(String bincome) {
                this.bincome = bincome;
            }



        }

        public static class FreelancerBean {

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
