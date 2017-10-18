package cn.com.stableloan.ui.fragment.information;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.Bank;
import cn.com.stableloan.model.BankInformation;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.activity.GestureLoginActivity;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.ui.activity.integarl.DateChangeActivity;
import cn.com.stableloan.utils.BankUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class User_Bank_Fragment extends Fragment {

    @Bind(R.id.et_BankCard1)
    SuperTextView etBankCard1;
    @Bind(R.id.et_BankPersonName1)
    SuperTextView etBankPersonName1;
    @Bind(R.id.et_BankPhone1)
    SuperTextView etBankPhone1;
    @Bind(R.id.et_SelectBank1)
    SuperTextView etSelectBank1;
    @Bind(R.id.et_ValidityTime1)
    SuperTextView etValidityTime1;
    @Bind(R.id.is_credit)
    SuperTextView isCredit;
    @Bind(R.id.et_BankCard2)
    SuperTextView etBankCard2;
    @Bind(R.id.et_BankPersonName2)
    SuperTextView etBankPersonName2;
    @Bind(R.id.et_BankPhone2)
    SuperTextView etBankPhone2;
    @Bind(R.id.et_SelectBank2)
    SuperTextView etSelectBank2;
    @Bind(R.id.et_ValidityTime2)
    SuperTextView etValidityTime2;
    @Bind(R.id.layout_credit)
    LinearLayout layoutCredit;
    private TimePickerView pvTime;

    private Bank bankBean;
    private ACache aCache;

    private static final int REQUEST_CODE = 20000;

    private boolean creditFlag=false;
    public User_Bank_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user__bank, container, false);
        ButterKnife.bind(this, view);
        aCache = ACache.get(getActivity());
        EventBus.getDefault().register(this);

        initTime();
        setListener();
        getDate();
        return view;
    }

    private void setListener() {
        setSuperText(etBankCard1, Urls.DateChange.DEBIT_CARD);
        setSuperText(etBankCard2, Urls.DateChange.CREDIT_CARD);

        setSuperText(etBankPersonName1, Urls.DateChange.DEBIT_NAME);
        setSuperText(etBankPersonName2, Urls.DateChange.CREDIT_NAME);

        setSuperText(etBankPhone1, Urls.DateChange.DEBIT_PHONE);
        setSuperText(etBankPhone2, Urls.DateChange.CREDIT_PHONE);

       /* setSuperText(etSelectBank1, Urls.DateChange.DEBIT_BANK);
        setSuperText(etSelectBank2, Urls.DateChange.CREDIT_BANK);*/
        etValidityTime1.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                pvTime.show(superTextView);
            }
        });
        etValidityTime2.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                pvTime.show(superTextView);
            }
        });
        isCredit.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                int visibility = layoutCredit.getVisibility();
                if(visibility==View.GONE){
                    layoutCredit.setVisibility(View.VISIBLE);
                    isCredit.setRightString("有");
                }else {
                    layoutCredit.setVisibility(View.GONE);
                    isCredit.setRightString("没有");
                }
            }
        });

    }

    private void setSuperText(SuperTextView superText, int type) {
        superText.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                Intent intent = new Intent(getActivity(), DateChangeActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("hint",superText.getRightString());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    private void initTime() {
        int color = getResources().getColor(R.color.colorPrimary);

        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("persmaterials2", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//记录事件
        ZhugeSDK.getInstance().track(getActivity(), "身份信息", eventObject);

        pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SuperTextView btn = (SuperTextView) v;
                btn.setRightString(getTime(date));
            }
        })
                .setCancelText("取消")
                .setTitleColor(color)
                .setSubmitText("确定")
                .setDividerColor(color)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setSubmitColor(color)
                .setCancelColor(color)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();

    }

    @Subscribe
    public void onMessageEvent(InformationEvent event) {
        String message = event.message;
        if ("bankinformation".equals(message)) {
            getDate();
        }
    }

    private void getDate() {

        Map<String, String> parms = new HashMap<>();
        String token = (String) SPUtils.get(getActivity(), "token", "1");
        String signature = (String) SPUtils.get(getActivity(), "signature", "1");
        parms.put("token", token);
        parms.put("signature", signature);
        parms.put("source", "");

        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.Ip_url + Urls.Identity.Getbank)
                .tag(getActivity())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        if (s != null) {
                            Gson gson = new Gson();
                            BankInformation information = gson.fromJson(s, BankInformation.class);
                            if (information.getError_code() == 0) {
                                if (information.getData().getStatus().equals("1")) {
                                    bankBean = information.getData();
                                    etBankCard1.setRightString(bankBean.getBank().getDebit().getDnumber());
                                    etBankPersonName1.setRightString(bankBean.getBank().getDebit().getDname());
                                    etBankPhone1.setRightString(bankBean.getBank().getDebit().getDphone());
                                    etSelectBank1.setRightString(bankBean.getBank().getDebit().getDbank());
                                    etValidityTime1.setRightString(bankBean.getBank().getDebit().getDperiod());

                                    int is_credit = bankBean.getBank().getCredit().getIs_credit();

                                    if(is_credit==1){
                                        layoutCredit.setVisibility(View.VISIBLE);
                                        isCredit.setRightString("有");
                                    }else {
                                        isCredit.setRightString("没有");
                                    }

                                    etBankCard2.setRightString(bankBean.getBank().getCredit().getCnumber());
                                    etBankPersonName2.setRightString(bankBean.getBank().getCredit().getCname());
                                    etBankPhone2.setRightString(bankBean.getBank().getCredit().getCphone());
                                    etSelectBank2.setRightString(bankBean.getBank().getCredit().getCbank());
                                    etValidityTime2.setRightString(bankBean.getBank().getCredit().getCperiod());

                                } else {

                                    final TinyDB tinyDB = new TinyDB(getActivity());
                                    UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
                                    String userphone = user.getUserphone();
                                    String gesturePassword = aCache.getAsString(userphone);
                                    String lock = aCache.getAsString("lock");

                                    if (gesturePassword == null || "".equals(gesturePassword) || "off".equals(lock)) {
                                        Intent intent = new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "bankinformation");
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(getActivity(), GestureLoginActivity.class).putExtra("from", "bankinformation");
                                        startActivity(intent);
                                    }
                                }
                            } else {
                                ToastUtils.showToast(getActivity(), information.getError_message());
                            }
                        }
                    }
                });
    }

    private String getTime(Date date) {
        //可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
        return format.format(date);
    }

    private void Save() {
        final Bank.BankBean.CreditBean creditBean = new Bank.BankBean.CreditBean();
        creditBean.setCbank(etSelectBank2.getRightString());
        creditBean.setCname(etBankPersonName2.getRightString());
        creditBean.setCnumber(etBankCard2.getRightString());
        creditBean.setCperiod(etValidityTime2.getRightString());
        creditBean.setCphone(etBankPhone2.getRightString());

        int visibility = layoutCredit.getVisibility();
        if(visibility==View.GONE){
            creditBean.setIs_credit(2);
        }else {
            creditBean.setIs_credit(1);
        }

        final Bank.BankBean.DebitBean debitBean = new Bank.BankBean.DebitBean();
        debitBean.setDbank(etSelectBank1.getRightString());
        debitBean.setDname(etBankPersonName1.getRightString());
        debitBean.setDnumber(etBankCard1.getRightString());
        debitBean.setDperiod(etValidityTime1.getRightString());
        debitBean.setDphone(etBankPhone1.getRightString());


        if (bankBean.getBank().getCredit().equals(creditBean) && bankBean.getBank().getDebit().equals(debitBean)) {
            ToastUtils.showToast(getActivity(), "无修改内容");

        } else {
            String token = (String) SPUtils.get(getActivity(), "token", "1");
            Bank bank = new Bank();
            bank.setToken(token);
            Bank.BankBean bean = new Bank.BankBean();
            if (!etBankPhone1.getRightString().isEmpty()
                    && !etBankPersonName1.getRightString().isEmpty()
                    && !etBankCard1.getRightString().isEmpty()
                    && !etSelectBank1.getRightString().isEmpty()
                    && !etValidityTime1.getRightString().isEmpty()) {
                bean.setBstatus("1");
            } else {
                bean.setBstatus("0");
            }
            bean.setCredit(creditBean);
            bean.setDebit(debitBean);
            bank.setBank(bean);
            Gson gson = new Gson();
            String json = gson.toJson(bank);
            OkGo.<String>post(Urls.Ip_url + Urls.Identity.Addbank)
                    .tag(this)
                    .upJson(json)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject object = new JSONObject(s);
                                int isSuccess = object.getInt("error_code");
                                if (isSuccess == 0) {
                                    EventBus.getDefault().post(new InformationEvent("informationStatus"));
                                    String data = object.getString("data");
                                    JSONObject object1 = new JSONObject(data);
                                    String msg = object1.getString("msg");
                                    ToastUtils.showToast(getActivity(), msg);
                                    bankBean.getBank().setDebit(debitBean);
                                    bankBean.getBank().setCredit(creditBean);
                                } else {
                                    String msg = object.getString("error_message");
                                    ToastUtils.showToast(getActivity(), msg);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case Urls.DateChange.DEBIT_CARD:
                    if (data != null) {
                        String type = data.getStringExtra("type");
                        LogUtils.i("name===", type);
                        etBankCard1.setRightString(type);
                        if (type.length() > 6) {
                            String name = BankUtils.getNameOfBank(type);// 获取银行卡的信息
                            etSelectBank1.setRightString(name);
                        }

                    }
                    break;
                case Urls.DateChange.CREDIT_CARD:
                    if (data != null) {
                        String type = data.getStringExtra("type");
                        LogUtils.i("name===", type);
                        etBankCard2.setRightString(type);
                        if (type.length() > 6) {
                            String name = BankUtils.getNameOfBank(type);// 获取银行卡的信息
                            etSelectBank2.setRightString(name);
                        }
                    }
                    break;
                case Urls.DateChange.DEBIT_NAME:
                    if (data != null) {
                        String type = data.getStringExtra("type");
                        LogUtils.i("name===", type);
                        etBankPersonName1.setRightString(type);
                    }
                    break;
                case Urls.DateChange.CREDIT_NAME:
                    if (data != null) {
                        String type = data.getStringExtra("type");
                        LogUtils.i("name===", type);
                        etBankPersonName2.setRightString(type);
                    }
                    break;
                case Urls.DateChange.DEBIT_PHONE:
                    if (data != null) {
                        String type = data.getStringExtra("type");
                        LogUtils.i("name===", type);
                        etBankPhone1.setRightString(type);
                    }
                    break;
                case Urls.DateChange.CREDIT_PHONE:
                    if (data != null) {
                        String type = data.getStringExtra("type");
                        LogUtils.i("name===", type);
                        etBankPhone2.setRightString(type);
                    }
                    break;
            }
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @OnClick(R.id.save)
    public void onViewClicked() {
        Save();
    }
}
