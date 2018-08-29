package cn.com.cashninetrees.ui.activity.vip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.api.ApiService;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.bean.Login;
import cn.com.cashninetrees.interfaceutils.OnRequestDataListener;
import cn.com.cashninetrees.model.Lottery;
import cn.com.cashninetrees.ui.adapter.LotteryAdapter;
import cn.com.cashninetrees.utils.DownAPKService;
import cn.com.cashninetrees.utils.SPUtil;
import cn.com.cashninetrees.utils.StatusBarUtil;
import cn.com.cashninetrees.utils.ToastUtils;

/**
 * @author apple
 *         VIP彩票
 */
public class LotteryActivity extends AppCompatActivity {


    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.lotteryRecycler)
    RecyclerView lotteryRecycler;
    @Bind(R.id.bt_down)
    Button btDown;
    private LotteryAdapter mLotteryAdapter;
    private String token;
    private String downUrl;
    private int status=0;
    private View errorView;
    private View notDataView;
    private ImmersionBar mImmersionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.white)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();
        initView();
        getDate();
    }

    private void getDate() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Urls.lock.TOKEN, token);
            ApiService.GET_SERVICE(Urls.Vip.LOTTERY, jsonObject, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject data) {
                    try {
                        String data1 = data.getString("data");
                        Gson gson = new Gson();

                        Lottery productBeen = gson.fromJson(data1, Lottery.class);
                        if (productBeen.getLottery().size() != 0) {
                            mLotteryAdapter.setNewData(productBeen.getLottery());
                        }else {
                            mLotteryAdapter.setEmptyView(notDataView);
                        }
                        downUrl=productBeen.getLink();
                        status = productBeen.getStatus();
                        if(status==0){
                            btDown.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void requestFailure(int code, String msg) {
                    mLotteryAdapter.setEmptyView(errorView);
                    ToastUtils.showToast(LotteryActivity.this, msg);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        String title = getIntent().getStringExtra("title");
        mTitle.setText(title);

        token = SPUtil.getString(this, Urls.lock.TOKEN);
        mLotteryAdapter = new LotteryAdapter(null);
        lotteryRecycler.setLayoutManager(new LinearLayoutManager(this));
        lotteryRecycler.setAdapter(mLotteryAdapter);
        notDataView = getLayoutInflater().inflate(R.layout.view_empty, (ViewGroup) lotteryRecycler.getParent(), false);
        errorView = getLayoutInflater().inflate(R.layout.view_error, (ViewGroup) lotteryRecycler.getParent(), false);

    }

    @OnClick({R.id.back, R.id.bt_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_down:
                downloadApk("http://p16x205eh.bkt.clouddn.com/001-v1.6.2-20180329-183242.apk");
                break;
            default:
                break;
        }
    }
    /**
     * 应用内拦截下载
     */
    private void downloadApk(String url) {

        Intent intent = new Intent(this, DownAPKService.class);
        intent.putExtra("apk_url", url);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 刷新数据
     * @param event
     */
    @Subscribe
    public void onMessageEvent(Login event) {
        token= SPUtil.getString(this, Urls.lock.TOKEN);
        getDate();
    }
}
