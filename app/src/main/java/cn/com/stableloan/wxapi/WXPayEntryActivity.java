package cn.com.stableloan.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import cn.com.stableloan.bean.PayEvent;
import cn.com.stableloan.common.Constants;
import cn.com.stableloan.utils.ToastUtils;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	

    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.WEICHAT_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		//LogUtils.i("onPayFinish" + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode){
				case 0:
					ToastUtils.showToast(this,"支付成功");
					EventBus.getDefault().post(new PayEvent(1));
					break;
				case -2:
					ToastUtils.showToast(this,"支付取消");
					EventBus.getDefault().post(new PayEvent(0));
					break;
				default:
					ToastUtils.showToast(this,"支付失败");
					EventBus.getDefault().post(new PayEvent(0));
					break;
			}
		}
		finish();
	}
}