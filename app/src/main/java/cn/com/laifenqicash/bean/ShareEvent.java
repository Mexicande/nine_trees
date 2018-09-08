package cn.com.laifenqicash.bean;

import java.io.Serializable;

/**
 * Created by apple on 2017/8/14.
 */

public class ShareEvent implements Serializable{
    public final int type;
    public final String shareUrl;

    public ShareEvent(int offical,String url) {
        this.type = offical;
        this.shareUrl = url;
    }
}
