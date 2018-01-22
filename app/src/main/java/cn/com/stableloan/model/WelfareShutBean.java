package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/24.
 */

public class WelfareShutBean implements Serializable{

    /**
     * code : 200
     * message : 200
     * data : {"link":"http://h5.jdd.com/common/jump/index.html?data=%7B%22appKey%22%3A%22xianjindai%22%2C%22Uid%22%3A%22XJD48%22%2C%22nickName%22%3A%22%22%2C%22mobilePhone%22%3A%2218500634223%22%2C%22imageUrl%22%3A%22%22%2C%22timeStamp%22%3A1500891536%2C%22attach%22%3A%22%22%2C%22sign%22%3A%22jHLf5pdhPrFiwptM3f1Scxb6z64Ob06Xx1vx1bYjlzHQLKPIXZ9bSe%2BUyl8A6ddsmdH5pm%2BfKCDVYM4c5UZAIKjdz7pw3Ivp7OwbypG0Y22QbcN5w8IAWk%5C%2FrioI6GpqA%2BAkGhCTE6bnV%5C%2FY2L%5C%2FBzh%5C%2FfGcqV1FTyWLMlCI%2BmcOCIs%3D%22%2C%22signType%22%3A%22RSA%22%7D&jump=&frm=xjd","isSuccess":"1"}
     * error_code : 0
     * error_message :
     * time : 2017-07-24 18:18:56
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
        /**
         * link : http://h5.jdd.com/common/jump/index.html?data=%7B%22appKey%22%3A%22xianjindai%22%2C%22Uid%22%3A%22XJD48%22%2C%22nickName%22%3A%22%22%2C%22mobilePhone%22%3A%2218500634223%22%2C%22imageUrl%22%3A%22%22%2C%22timeStamp%22%3A1500891536%2C%22attach%22%3A%22%22%2C%22sign%22%3A%22jHLf5pdhPrFiwptM3f1Scxb6z64Ob06Xx1vx1bYjlzHQLKPIXZ9bSe%2BUyl8A6ddsmdH5pm%2BfKCDVYM4c5UZAIKjdz7pw3Ivp7OwbypG0Y22QbcN5w8IAWk%5C%2FrioI6GpqA%2BAkGhCTE6bnV%5C%2FY2L%5C%2FBzh%5C%2FfGcqV1FTyWLMlCI%2BmcOCIs%3D%22%2C%22signType%22%3A%22RSA%22%7D&jump=&frm=xjd
         * isSuccess : 1
         */

        private String link;
        private String isSuccess;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(String isSuccess) {
            this.isSuccess = isSuccess;
        }
    }
}
