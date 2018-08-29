package cn.com.cashninetrees.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.api.ApiService;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.base.BaseActivity;
import cn.com.cashninetrees.interfaceutils.OnRequestDataListener;
import cn.com.cashninetrees.model.NoticeBean;
import cn.com.cashninetrees.ui.adapter.NoticeAdapter;
import cn.com.cashninetrees.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author apple
 * 消息通知
 */
public class NoticeActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.notice_recycler)
    RecyclerView noticeRecycler;
    @Bind(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;

    private NoticeAdapter noticeAdapter;

    public static void launch(Context context) {

        context.startActivity(new Intent(context, NoticeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        initToolbar();
        setListener();
    }

    private void setListener() {
        SwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDate();
                    }
                },1000);
            }

        });

    }

    private void initToolbar() {

        titleName.setText("公告通知");
        ivBack.setVisibility(View.VISIBLE);

        noticeAdapter = new NoticeAdapter(null);
        noticeRecycler.setLayoutManager(new LinearLayoutManager(this));
        noticeRecycler.setAdapter(noticeAdapter);
        getDate();
    }


    private void getDate() {

        OkGo.post( Urls.notice.Announcement)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            try {
                                JSONObject object = new JSONObject(s);
                                String success = object.getString("isSuccess");
                                if("1".equals(success)){
                                    Gson gson = new Gson();
                                    NoticeBean noticeBean = gson.fromJson(s, NoticeBean.class);
                                    noticeAdapter.setNewData(noticeBean.getAnnouncements());

                                } else {
                                    String msg = object.getString("msg");
                                    ToastUtils.showToast(NoticeActivity.this, msg);
                                }
                                SwipeRefreshLayout.setRefreshing(false);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                });

    }

    private void recyclviewDate(NoticeBean noticeBean) {
        noticeAdapter.addData(noticeBean.getAnnouncements());
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
