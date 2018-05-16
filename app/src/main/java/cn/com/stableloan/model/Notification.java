package cn.com.stableloan.model;

import java.io.Serializable;

/**
 * Created by apple on 2018/4/14.
 * 首页通知
 */

public class Notification implements Serializable {

    /**
     * body : VIP尊享京东卡，专享500元限量供应！
     */

    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
