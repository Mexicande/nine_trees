package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2017/7/4.
 */

public class SaveBean implements Serializable{

    /**
     * way : 0
     * managed : 6个月
     * period : 0
     * lass_time : 2017-07-04 04:48:11
     * isSuccess : 1
     */

    private String way;
    private String managed;
    private String period;
    private String lass_time;
    private String isSuccess;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getManaged() {
        return managed;
    }

    public void setManaged(String managed) {
        this.managed = managed;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getLass_time() {
        return lass_time;
    }

    public void setLass_time(String lass_time) {
        this.lass_time = lass_time;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }
}
