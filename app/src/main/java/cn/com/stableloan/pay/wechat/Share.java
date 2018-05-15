package cn.com.stableloan.pay.wechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.common.Constants;


/**
 *
 * @author tantan
 * @date 2018/4/25
 * 分享
 */

public class Share {
    private Context mContext;
    private IWXAPI api;
    private BottomSheetDialog dialog;
    private Bitmap mBitmap;

    public Share(Context mContext,Bitmap bitmap) {
        this.mContext = mContext;
        // 将该app注册到微信
        this.mBitmap=bitmap;
        api = WXAPIFactory.createWXAPI(mContext, null);
        api.registerApp(Urls.KEY.WEICHAT_APPID);
        initView();
    }

    private void initView() {
        dialog=new BottomSheetDialog(mContext);
        View dialogView= LayoutInflater.from(mContext)
                .inflate(R.layout.silde_charge_pay,null);
        dialog.setContentView(dialogView);

    }

    public void ShareToFriend(boolean isShareFriend){
        if(mBitmap==null){
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = "http://www.baidu.com";
            WXMediaMessage msg = new WXMediaMessage(webpage);

            msg.title = "标题";
            msg.description = "描述信息";
            Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
            msg.thumbData = Util.bmpToByteArray(thumb, true);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = isShareFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            api.sendReq(req);
        }else {
            WXImageObject imgObj = new WXImageObject(mBitmap);
            WXMediaMessage msg = new WXMediaMessage(imgObj);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(mBitmap, 131, 180, true);
            //缩略图大小
            mBitmap.recycle();
            msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
            // 设置缩略图
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("img");
            req.message = msg;
            req.scene = isShareFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            api.sendReq(req);
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void show(){
        dialog.show();
    }
}
