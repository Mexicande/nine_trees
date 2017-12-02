package cn.com.stableloan.ui.activity.integarl;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
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
import cn.com.stableloan.bean.CashEvent;
import cn.com.stableloan.model.integarl.AdvertisingBean;
import cn.com.stableloan.model.integarl.CashBean;
import cn.com.stableloan.ui.activity.CertificationActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.dialog.CashDialog;
import cn.com.stableloan.view.keyboard.VirtualKeyboardView;
import cn.com.stableloan.view.supertextview.SuperButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 现金提取
 */
public class WithdrawalCashActivity extends BaseActivity {
    @Bind(R.id.cash_toolbar)
    Toolbar cashToolbar;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.layout_no)
    RelativeLayout layoutNo;
    @Bind(R.id.tv_balance)
    TextView tvBalance;
    @Bind(R.id.arrow)
    ImageView arrow;
    @Bind(R.id.set_account)
    RelativeLayout setAccount;
    @Bind(R.id.layout_visiable)
    RelativeLayout layoutVisiable;
    @Bind(R.id.bt_withdrawal)
    SuperButton btWithdrawal;
    private VirtualKeyboardView virtualKeyboardView;
    private CashDialog cashDialog;
    private GridView gridView;
    private ArrayList<Map<String, String>> valueList;

    private EditText textAmount;

    private Animation enterAnim;
    private Animation exitAnim;
    private CashBean cashBean;

    private static final int RESULT_CODE = 200;
    private static final int WITHDRAW_CODE = 1;

    private static final int REQUESTION_CODE=300;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, WithdrawalCashActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_cash);
        ButterKnife.bind(this);

        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary)
                .statusBarAlpha(0.3f)
                .fitsSystemWindows(true)
                .init();

        getUserPopo();
        initAnim();
        initView();
        valueList = virtualKeyboardView.getValueList();

        cashBean = (CashBean) getIntent().getSerializableExtra("cash");
        if (cashBean != null) {
            address.setText(cashBean.getData().getAccount());
            tvBalance.setText("¥"+cashBean.getData().getTotal());
        }
        setListener();

    }
    private void setListener() {

        textAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s + "";
                if (s1.startsWith(".")) {
                    String s2 = s1.replaceFirst(".", "0.");
                    textAmount.setText(s2);
                }
                if (s.length() >= 1&&!address.getText().toString().isEmpty()) {
                    btWithdrawal.setEnabled(true);
                    btWithdrawal.setUseShape();
                } else {
                    btWithdrawal.setEnabled(false);
                    btWithdrawal.setUseShape();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void getUserPopo() {

        String token = (String) SPUtils.get(this, Urls.TOKEN, "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("position", "2");
        params.put("type", "1");
        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.Ip_url + Urls.Dialog.GETUSERPOPUP)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            AdvertisingBean outBean = gson.fromJson(s, AdvertisingBean.class);
                            if (outBean.getError_code() == 0) {
                                if (("1").equals(outBean.getData().getStatus())) {
                                    RuleDialog(outBean.getData().getName(), outBean.getData().getDescription());
                                }
                            } else if(outBean.getError_code() == 2) {
                                Intent intent = new Intent(WithdrawalCashActivity.this, LoginActivity.class);
                                intent.putExtra("message", cashBean.getError_message());
                                intent.putExtra("from", "CashWithError");
                                startActivityForResult(intent,REQUESTION_CODE);

                            }else if(outBean.getError_code()==Urls.ERROR_CODE.FREEZING_CODE){
                                Intent intent=new Intent(WithdrawalCashActivity.this,LoginActivity.class);
                                intent.putExtra("message","1136");
                                intent.putExtra("from","1136");
                                startActivity(intent);
                                finish();

                            }else {

                                    ToastUtils.showToast(WithdrawalCashActivity.this, outBean.getError_message());
                            }

                        }
                    }
                });
    }

    /**
     * 提现
     */
    private void getDate() {
        double anInt = Double.parseDouble(textAmount.getText().toString());
        int i = (int) anInt;
        String token = (String) SPUtils.get(this, "token", "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("number", String.valueOf(i));
        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.Ip_url + Urls.Integarl.OUTCASH)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            AdvertisingBean outBean = gson.fromJson(s, AdvertisingBean.class);
                            if (outBean.getError_code() == 0) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String data = jsonObject.getString("data");
                                    JSONObject object1 = new JSONObject(data);
                                    String isSucess = object1.getString("isSucess");
                                    if ("1".equals(isSucess)) {
                                        String money = tvBalance.getText().toString();
                                        String substring = money.substring(0, money.length() - 3);
                                        String replace = substring.replace(",", "");
                                        String replace2 = replace.replace("¥", "");
                                        int size = Integer.parseInt(replace2);
                                        int i1 = size - i;
                                        tvBalance.setText(String.valueOf(i1) + ".00");
                                        Intent intent = new Intent();
                                        setResult(RESULT_CODE, intent);
                                        ToastUtils.showToast(WithdrawalCashActivity.this, "提现成功,稍后请查看结果");
                                        finish();
                                    } else {
                                        String error_message = jsonObject.getString("error_message");
                                        ToastUtils.showToast(WithdrawalCashActivity.this, error_message);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else  if(outBean.getError_code()==2){
                                Intent intent=new Intent(WithdrawalCashActivity.this,LoginActivity.class);
                                intent.putExtra("message", cashBean.getError_message());
                                intent.putExtra("from", "CashWithError");
                                startActivityForResult(intent,REQUESTION_CODE);

                            }else if(outBean.getError_code()==Urls.ERROR_CODE.FREEZING_CODE){
                                Intent intent=new Intent(WithdrawalCashActivity.this,LoginActivity.class);
                                intent.putExtra("message","1136");
                                intent.putExtra("from","1136");
                                startActivity(intent);
                                finish();

                            } else{
                                ToastUtils.showToast(WithdrawalCashActivity.this, outBean.getError_message());
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

    private void RuleDialog(String title, String desc) {
        cashDialog = new CashDialog(this);
        cashDialog.setTitle(title);
        cashDialog.setMessage(desc);
        cashDialog.setYesOnclickListener("知道了", new CashDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                cashDialog.dismiss();
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

        String token = (String) SPUtils.get(this, Urls.TOKEN, "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("position", "2");
        params.put("type", "1");
        params.put("userStatus", "1");

        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.Ip_url + Urls.Dialog.GETUSERPOPUP)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            AdvertisingBean outBean = gson.fromJson(s, AdvertisingBean.class);
                            if (outBean.getError_code() == 0) {

                            } else if(outBean.getError_code() == 2){
                                LoginActivity.launch(WithdrawalCashActivity.this);
                            }else {

                                ToastUtils.showToast(WithdrawalCashActivity.this, outBean.getError_message());
                            }

                        }
                    }
                });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();
        setResult(RESULT_CODE, intent);
        finish();
        return super.onKeyDown(keyCode, event);

    }

    @OnClick({R.id.close, R.id.iv_rule, R.id.arrow, R.id.tv_AllWithdrawal,R.id.set_account,R.id.bt_withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:
                Intent intent = new Intent();
                setResult(RESULT_CODE, intent);
                finish();
                break;
            case R.id.iv_rule:
                RuleDescActivity.launch(this);
                break;
            case R.id.arrow:
                int visibility = layoutNo.getVisibility();
                if (visibility == View.VISIBLE) {
                    layoutNo.setVisibility(View.GONE);
                    setAccount.setVisibility(View.GONE);
                    layoutVisiable.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.cash_open);
                } else {
                    layoutNo.setVisibility(View.VISIBLE);
                    setAccount.setVisibility(View.VISIBLE);
                    layoutVisiable.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.cash_close);
                }
                break;
            case R.id.tv_AllWithdrawal:
                String toString = tvBalance.getText().toString();

                int indexOf1 = toString.lastIndexOf(".");
                String substring1 = toString.substring(0, indexOf1);
                String replace2 = substring1.replace("¥", "");

                String replace1 = replace2.replace(",", "");

                double anInt = Double.parseDouble(replace1);
                int i = (int) anInt;
                if (i != 0) {
                    textAmount.setText(String.valueOf(i));
                } else {
                    ToastUtils.showToast(this, "余额不足");
                }
                break;
            case R.id.bt_withdrawal:
                zhuGe();
                String toString1 = tvBalance.getText().toString();

                if(TextUtils.isEmpty(toString1)){

                }else {
                    int indexOf = toString1.lastIndexOf(".");
                    String substring = toString1.substring(0, indexOf);
                    String replace3 = substring.replace("¥", "");
                    String replace = replace3.replace(",", "");
                    int anInt1 = Integer.parseInt(replace);
                    String string = textAmount.getText().toString();
                    double anInt2 = Double.parseDouble(string);
                    if (anInt2 <= anInt1) {
                        double parseInt = Double.parseDouble(string);
                        if (parseInt != 0) {
                            getDate();
                        } else {
                            ToastUtils.showToast(this, "请输入有效金额");
                        }
                    } else {
                        ToastUtils.showToast(this, "请输入有效金额");
                    }
                }
                break;
            case R.id.set_account:
                Intent certifIntent=new Intent(this,CertificationActivity.class);
                certifIntent.putExtra("from","cash");
                startActivityForResult(certifIntent,REQUESTION_CODE);
                break;

        }
    }

    private void zhuGe() {

        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("tixianbutton", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ZhugeSDK.getInstance().track(this, "提现", eventObject);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTION_CODE){
            switch (resultCode){
                case WITHDRAW_CODE:
                    getCashDate();
                    break;
                case 112:
                    getUserPopo();
                    break;
            }
        }
    }


    private void getCashDate() {
        String token = (String) SPUtils.get(this, Urls.TOKEN, "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.Ip_url + Urls.Integarl.GETCASH)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            cashBean = gson.fromJson(s, CashBean.class);
                            if (cashBean.getError_code() == 0) {
                                address.setText(cashBean.getData().getAccount());
                                tvBalance.setText("¥"+cashBean.getData().getTotal());
                            } else if (cashBean.getError_code() == 2) {
                                Intent intent = new Intent(WithdrawalCashActivity.this, LoginActivity.class);
                                intent.putExtra("message", cashBean.getError_message());
                                intent.putExtra("from", "CashWithError");
                                startActivityForResult(intent,REQUESTION_CODE);
                            } else if(cashBean.getError_code()==Urls.ERROR_CODE.FREEZING_CODE){
                                Intent intent=new Intent(WithdrawalCashActivity.this,LoginActivity.class);
                                intent.putExtra("message","1136");
                                intent.putExtra("from","1136");
                                startActivity(intent);
                                finish();

                            }else {
                                ToastUtils.showToast(WithdrawalCashActivity.this, cashBean.getError_message());
                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });
    }

}
