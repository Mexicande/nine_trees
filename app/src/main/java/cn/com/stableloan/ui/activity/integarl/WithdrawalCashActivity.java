package cn.com.stableloan.ui.activity.integarl;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.CardBean;
import cn.com.stableloan.model.integarl.AdvertisingBean;
import cn.com.stableloan.model.integarl.CashBean;
import cn.com.stableloan.model.integarl.CashOutBean;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.dialog.CashDialog;
import cn.com.stableloan.view.keyboard.VirtualKeyboardView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 现金提取
 */
public class WithdrawalCashActivity extends BaseActivity {
    /* @Bind(R.id.Spinner_way)
     BetterSpinner SpinnerWay;*/
    @Bind(R.id.cash_toolbar)
    Toolbar cashToolbar;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.open)
    RelativeLayout open;
    @Bind(R.id.layout_no)
    RelativeLayout layoutNo;
    private VirtualKeyboardView virtualKeyboardView;
    private CashDialog cashDialog;
    private GridView gridView;

    private ArrayList<Map<String, String>> valueList;

    private EditText textAmount;

    private Animation enterAnim;

    private Animation exitAnim;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, WithdrawalCashActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_cash);
        ButterKnife.bind(this);

        ImmersionBar.with(this).statusBarColor(R.color.cash_toolbar)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();

        getUserPopo();
     /*   ImmersionBar.with(this)
                .titleBar(cashToolbar)
                .transparentStatusBar()
                .statusBarAlpha(0.3f)
                .init();
        ImmersionBar.with(this)
                .titleBar(view) //指定标题栏view,xml里的标题的高度不能指定为warp_content，如果是自定义xml实现标题栏的话，最外层节点不能为RelativeLayout
                .init();*/
        initAnim();
        initView();
        valueList = virtualKeyboardView.getValueList();
    }

    private void getUserPopo() {

        String token = (String) SPUtils.get(this, "token", "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("position","1");
        params.put("type","1");
        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.Ip_url + Urls.Dialog.GETUSERPOPUP)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            AdvertisingBean outBean = gson.fromJson(s, AdvertisingBean.class);
                            if(outBean.getError_code()==0){
                                RuleDialog(outBean.getData().getName(),outBean.getData().getDescription());

                            }else {
                                ToastUtils.showToast(WithdrawalCashActivity.this,outBean.getError_message());
                            }

                        }
                    }
                });


    }

    /**
     * 提现
     *
     */
    private void getDate() {
        String token = (String) SPUtils.get(this, "token", "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.Ip_url + Urls.Integarl.OUTCASH)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            AdvertisingBean outBean = gson.fromJson(s, AdvertisingBean.class);
                            if(outBean.getError_code()==0){

                            }else {
                                ToastUtils.showToast(WithdrawalCashActivity.this,outBean.getError_message());
                            }

                        }
                    }
                });
    }


    private void initAnim() {

        enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
    }

    private void initView() {

        textAmount = (EditText) findViewById(R.id.textAmount);

        // 设置不调用系统键盘
        if (Build.VERSION.SDK_INT <= 10) {
            textAmount.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(textAmount, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        virtualKeyboardView = (VirtualKeyboardView) findViewById(R.id.virtualKeyboardView);
        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virtualKeyboardView.startAnimation(exitAnim);
                virtualKeyboardView.setVisibility(View.GONE);
            }
        });

        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);

        textAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                virtualKeyboardView.setFocusable(true);
                virtualKeyboardView.setFocusableInTouchMode(true);

                virtualKeyboardView.startAnimation(enterAnim);
                virtualKeyboardView.setVisibility(View.VISIBLE);
            }
        });

    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (position < 11 && position != 9) {    //点击0~9按钮
                String amount = textAmount.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");
                textAmount.setText(amount);
                Editable ea = textAmount.getText();
                textAmount.setSelection(ea.length());
            } else {

                if (position == 9) {      //点击退格键
                    String amount = textAmount.getText().toString().trim();
                    if (!amount.contains(".")) {
                        amount = amount + valueList.get(position).get("name");
                        textAmount.setText(amount);

                        Editable ea = textAmount.getText();
                        textAmount.setSelection(ea.length());
                    }
                }

                if (position == 11) {      //点击退格键
                    String amount = textAmount.getText().toString().trim();
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        textAmount.setText(amount);

                        Editable ea = textAmount.getText();
                        textAmount.setSelection(ea.length());
                    }
                }
            }
        }
    };

    private void RuleDialog(String title,String desc) {
        cashDialog = new CashDialog(this);
        cashDialog.setTitle(title);
        cashDialog.setMessage(desc);
        cashDialog.setYesOnclickListener("知道了", new CashDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {

                cashDialog.dismiss();
               /* SPUtils.clear(WithdrawalCashActivity.this);
                TinyDB tinyDB = new TinyDB(WithdrawalCashActivity.this);
                tinyDB.clear();
                startActivity(new Intent(WithdrawalCashActivity.this, LoginActivity.class).putExtra("from", "user2"));
                finish();*/
               userKnow();
            }
        });
        cashDialog.setNoOnclickListener("查看规则", new CashDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                RuleDescActivity.launch(WithdrawalCashActivity.this);
            }
        });
        cashDialog.show();
    }

    private void userKnow() {


    }


    @OnClick({R.id.close, R.id.iv_rule, R.id.open,R.id.bt_withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.iv_rule:
                RuleDescActivity.launch(this);
                break;
            case R.id.open:
                int visibility = layoutNo.getVisibility();
                if (visibility == View.VISIBLE) {
                    layoutNo.setVisibility(View.GONE);
                } else {

                    layoutNo.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.bt_withdrawal:
                getDate();
                break;
        }
    }


}
