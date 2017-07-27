package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/25.
 */

public class GtDateBean implements Serializable{

    /**
     * message : success
     * gtcode : 80hponRwbNuN0JqG2eRwBy5XcDC1ynVX
     */

    private String message;
    private String gtcode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGtcode() {
        return gtcode;
    }

    public void setGtcode(String gtcode) {
        this.gtcode = gtcode;
    }

    @Override
    public String toString() {
        return "GtDateBean{" +
                "message='" + message + '\'' +
                ", gtcode='" + gtcode + '\'' +
                '}';
    }
}
