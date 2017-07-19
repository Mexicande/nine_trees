package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.rong360.app.crawler.CrawlerCallBack;
import com.rong360.app.crawler.CrawlerManager;
import com.rong360.app.crawler.CrawlerStatus;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.utils.LogUtils;

/**
 * 第三方认证
 */
public class CertificationActivity extends BaseActivity {
    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.text_goBack)
    TextView textGoBack;
    @Bind(R.id.layout_go)
    LinearLayout layoutGo;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.alipay)
    SuperTextView alipay;
    @Bind(R.id.jd)
    SuperTextView jd;
    @Bind(R.id.mobile)
    SuperTextView mobile;
    @Bind(R.id.contact)
    SuperTextView contact;
    private CrawlerStatus crawlerStatus = new CrawlerStatus();

    public static void launch(Context context) {
        context.startActivity(new Intent(context, CertificationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", "5");
        crawlerStatus.privatekey = Urls.CreditrePort.PRIVATE_KEY;
        crawlerStatus.merchant_id = Urls.CreditrePort.APP_ID;// "1000053";
        crawlerStatus.appname = Urls.CreditrePort.APP_NAME;//  "CreditReport";
        crawlerStatus.hashMap = params;
        crawlerStatus.taskid = String.valueOf(System.currentTimeMillis());

    }

    @OnClick({R.id.mobile, R.id.contact, R.id.taobao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mobile:

                CrawlerCallBack callBack = new CrawlerCallBack() {
                    @Override
                    public void onStatus(CrawlerStatus crawlerStatus) {
                        LogUtils.i("mobile",crawlerStatus.status);
                        switch (crawlerStatus.status) {
                            case 2:
                                break;
                            case 4:
                                break;
                        }
                    }
                };
                crawlerStatus.type = "mobile";
                CrawlerManager.getInstance(this.getApplication()).startCrawlerByType(callBack, crawlerStatus);
                break;
            case R.id.contact:
                break;
            case R.id.taobao:
                CrawlerCallBack taobaoCallBack = new CrawlerCallBack() {
                    @Override
                    public void onStatus(CrawlerStatus crawlerStatus) {
                        Toast.makeText(CertificationActivity.this,crawlerStatus.status,Toast.LENGTH_SHORT).show();

                        LogUtils.i("taobao",crawlerStatus.status);

                        switch (crawlerStatus.status) {
                            case 2:
                                break;
                            case 4:
                                break;
                        }
                    }
                };
                crawlerStatus.type = "taobao";
                CrawlerManager.getInstance(this.getApplication()).startCrawlerByType(taobaoCallBack, crawlerStatus);

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CrawlerManager.getInstance(this.getApplication()).unregistAllCallBack();
    }
}
