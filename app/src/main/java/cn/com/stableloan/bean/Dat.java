package cn.com.stableloan.bean;

/**
 * Created by apple on 2017/5/25.
 */

public class Dat {

    /**
     * liveInfo : {"addressProvince":"1","addressCity":"2","addressDistrict":"7","address":"Soho现代城2","tel":"18600885905"}
     */

    private LiveInfoBean liveInfo;

    public LiveInfoBean getLiveInfo() {
        return liveInfo;
    }

    public void setLiveInfo(LiveInfoBean liveInfo) {
        this.liveInfo = liveInfo;
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

        @Override
        public String toString() {
            return "LiveInfoBean{" +
                    "addressProvince='" + addressProvince + '\'' +
                    ", addressCity='" + addressCity + '\'' +
                    ", addressDistrict='" + addressDistrict + '\'' +
                    ", address='" + address + '\'' +
                    ", tel='" + tel + '\'' +
                    '}';
        }
    }
}
