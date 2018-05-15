package cn.com.stableloan.ui.activity.vip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.ApiService;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.interfaceutils.OnRequestDataListener;
import cn.com.stableloan.model.CreditBean;
import cn.com.stableloan.ui.activity.HtmlActivity;
import cn.com.stableloan.ui.adapter.CreditAdapter;
import cn.com.stableloan.utils.StatusBarUtil;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.MyDecoration;

/**
 * @author apple
 *         vip产品
 */
public class VipCreditActivity extends AppCompatActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.creditRecycler)
    RecyclerView creditRecycler;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private CreditAdapter mCreditAdapter;
    private View errorView;
    private View notDataView;
    private ImmersionBar mImmersionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_product);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.white)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();
        initView();
        getData();
        setListener();
    }

    private void initView() {
        String title = getIntent().getStringExtra("title");
        mTitle.setText(title);
        mCreditAdapter = new CreditAdapter(null);
        creditRecycler.setLayoutManager(new LinearLayoutManager(this));
        creditRecycler.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        creditRecycler.setAdapter(mCreditAdapter);
        View inflate = getLayoutInflater().inflate(R.layout.vip_credit_header, null, false);
        mCreditAdapter.addHeaderView(inflate);

        notDataView = getLayoutInflater().inflate(R.layout.view_empty, (ViewGroup) creditRecycler.getParent(), false);
        errorView = getLayoutInflater().inflate(R.layout.view_error, (ViewGroup) creditRecycler.getParent(), false);


    }

    private void getData() {

        ApiService.GET_SERVICE(Urls.Vip.CREDIT, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    String data1 = data.getString("data");
                    Gson gson = new Gson();
                    CreditBean[] productBeen = gson.fromJson(data1, CreditBean[].class);
                    if (productBeen.length != 0) {
                        mCreditAdapter.setNewData(Arrays.asList(productBeen));
                    } else {
                        mCreditAdapter.setEmptyView(notDataView);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(VipCreditActivity.this, msg);
                mCreditAdapter.setEmptyView(errorView);

            }
        });


    }

    private void setListener() {
        mCreditAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<CreditBean> data = mCreditAdapter.getData();
                CreditBean creditBean = data.get(position);
                Intent intent = new Intent(VipCreditActivity.this, HtmlActivity.class);
                intent.putExtra("link", creditBean.getLink());
                intent.putExtra("title", creditBean.getName());
                startActivity(intent);
            }
        });
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        swipeRefresh.setRefreshing(false);
                    }
                }, 1000);

            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
