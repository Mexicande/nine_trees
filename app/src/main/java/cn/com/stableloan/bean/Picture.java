package cn.com.stableloan.bean;

/**
 * Created by apple on 2017/6/5.
 */

public class Picture {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Picture() {
    }

    @Override
    public String toString() {
        return "Picture{" +
                "url='" + url + '\'' +
                '}';
    }
}
