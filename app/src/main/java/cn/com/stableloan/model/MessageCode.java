package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/14.
 */

public class MessageCode  implements Serializable{

    /**
     * code : 200
     * message :
     * data : {"isSuccess":"0"}
     * error_code :
     * error_message :
     */

    private int code;
    private String message;
    private GtDateBean data;
    private int error_code;
    private String error_message;

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

    public GtDateBean getData() {
        return data;
    }

    public void setData(GtDateBean data) {
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

    @Override
    public String toString() {
        return "MessageCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", error_code=" + error_code +
                ", error_message='" + error_message + '\'' +
                '}';
    }
}
