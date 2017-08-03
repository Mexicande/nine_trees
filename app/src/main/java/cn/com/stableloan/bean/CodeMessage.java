package cn.com.stableloan.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/5/23.
 */

public class CodeMessage implements Serializable{
    private int status;
    private String msg;
    private String check;
    private String error;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "CodeMessage{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", check='" + check + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}

