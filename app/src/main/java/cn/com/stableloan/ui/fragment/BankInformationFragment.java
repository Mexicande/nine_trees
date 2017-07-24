package cn.com.stableloan.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhuge.analysis.stat.ZhugeSDK;

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
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.utils.BankUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankInformationFragment extends Fragment {


    @Bind(R.id.et_BankCard1)
    FormEditText etBankCard1;
    @Bind(R.id.et_BankPersonName1)
    FormEditText etBankPersonName1;
    @Bind(R.id.et_SelectBank1)
    FormEditText etSelectBank1;
    @Bind(R.id.et_BankPhone1)
    FormEditText etBankPhone1;
    @Bind(R.id.et_BankCard2)
    FormEditText etBankCard2;
    @Bind(R.id.et_BankPersonName2)
    FormEditText etBankPersonName2;
    @Bind(R.id.et_SelectBank2)
    FormEditText etSelectBank2;
    @Bind(R.id.et_BankPhone2)
    FormEditText etBankPhone2;
    @Bind(R.id.et_ValidityTime2)
    TextView etValidityTime2;
    @Bind(R.id.et_ValidityTime1)
    TextView etValidityTime1;
    private TimePickerView pvTime;

    private  Bank bankBean;

    public BankInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        View view = inflater.inflate(R.layout.fragment_bank_information, container, false);
        ButterKnife.bind(this, view);
        initTime();
        setListener();
        getDate();
        return view;
    }

    private void initTime() {
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("persmaterials2", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//记录事件
        ZhugeSDK.getInstance().track(getActivity(), "身份信息",  eventObject);


        pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

                TextView btn = (TextView) v;
                btn.setText(getTime(date));

            }
        })
                .setCancelText("取消")
                .setSubmitText("确定")
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
    }

    private void getDate() {

        Map<String, String> parms = new HashMap<>();
        String token = (String) SPUtils.get(getActivity(), "token", "1");
        String signature = (String) SPUtils.get(getActivity(), "signature", "1");
        parms.put("token", token);
        parms.put("signature", signature);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_URL + Urls.Identity.Getbank)
                .tag(getActivity())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String isSuccess = json.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                Gson gson = new Gson();
                                bankBean = gson.fromJson(s, Bank.class);

                                etBankCard1.setText(bankBean.getBank().getDebit().getDnumber());
                                etBankPersonName1.setText(bankBean.getBank().getDebit().getDname());
                                etBankPhone1.setText(bankBean.getBank().getDebit().getDphone());
                                etSelectBank1.setText(bankBean.getBank().getDebit().getDbank());
                                etValidityTime1.setText(bankBean.getBank().getDebit().getDperiod());


                                etBankCard2.setText(bankBean.getBank().getCredit().getCnumber());
                                etBankPersonName2.setText(bankBean.getBank().getCredit().getCname());
                                etBankPhone2.setText(bankBean.getBank().getCredit().getCphone());
                                etSelectBank2.setText(bankBean.getBank().getCredit().getCbank());
                                etValidityTime2.setText(bankBean.getBank().getCredit().getCperiod());


                            } else {
                                Intent intent = new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "UserInformation");
                                startActivity(intent);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


    private void setListener() {
       /* etSelectBank1.setKeyListener(null);
        etSelectBank2.setKeyListener(null);*/

        etValidityTime1.setFocusableInTouchMode(false);

        etValidityTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    pvTime.show(v);
            }
        });
        etValidityTime2.setFocusableInTouchMode(false);
        etValidityTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    pvTime.show(v);
            }
        });
        etBankCard1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String huoqucc = etBankCard1.getText().toString();
                if (huoqucc.length() > 6) {
                    String name = BankUtils.getNameOfBank(huoqucc);// 获取银行卡的信息
                    etSelectBank1.setText(name);
                }


            }
        });


        etBankCard2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String huoqucc = etBankCard2.getText().toString();
                if (huoqucc.length() > 7) {
                    String name = BankUtils.getNameOfBank(huoqucc);// 获取银行卡的信息
                    etSelectBank2.setText(name);
                }

            }
        });
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
        return format.format(date);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.save)
    public void onViewClicked() {
        Save();
    }

    private void Save() {
        final Bank.BankBean.CreditBean creditBean = new Bank.BankBean.CreditBean();
        creditBean.setCbank(etSelectBank2.getText().toString());
        creditBean.setCname(etBankPersonName2.getText().toString());
        creditBean.setCnumber(etBankCard2.getText().toString());
        creditBean.setCperiod(etValidityTime2.getText().toString());
        creditBean.setCphone(etBankPhone2.getText().toString());


        final Bank.BankBean.DebitBean debitBean = new Bank.BankBean.DebitBean();
        debitBean.setDbank(etSelectBank1.getText().toString());
        debitBean.setDname(etBankPersonName1.getText().toString());
        debitBean.setDnumber(etBankCard1.getText().toString());
        debitBean.setDperiod(etValidityTime1.getText().toString());
        debitBean.setDphone(etBankPhone1.getText().toString());





        if(bankBean.getBank().getCredit().equals(creditBean)&&bankBean.getBank().getDebit().equals(debitBean)){
            ToastUtils.showToast(getActivity(),"无修改内容");


        }else {

            FormEditText[] allFields = {etBankCard1, etBankCard2,  etBankPersonName1, etBankPersonName1,etBankPhone1,etBankPhone2};
            boolean allValid = true;
            for (FormEditText field : allFields) {
                allValid = field.testValidity() && allValid;
            }
            if (allValid) {
                String token = (String) SPUtils.get(getActivity(), "token", "1");
                 Bank bank = new Bank();
                bank.setToken(token);
                Bank.BankBean bean = new Bank.BankBean();
                if(!etBankPhone2.getText().toString().isEmpty()&&!etBankPhone1.getText().toString().isEmpty()
                        &&!etBankPersonName1.getText().toString().isEmpty()&&!etBankPersonName2.getText().toString().isEmpty()
                        &&!etBankCard1.getText().toString().isEmpty()&&!etBankCard2.getText().toString().isEmpty()
                        &&!etSelectBank1.getText().toString().isEmpty()&&!etSelectBank2.getText().toString().isEmpty()
                        &&!etValidityTime1.getText().toString().isEmpty()&&!etValidityTime2.getText().toString().isEmpty()){
                    bean.setBstatus("1");

                }else {
                    bean.setBstatus("0");

                }
                bean.setCredit(creditBean);
                bean.setDebit(debitBean);
                bank.setBank(bean);
                Gson gson = new Gson();
                String json = gson.toJson(bank);
                OkGo.<String>post(Urls.NEW_URL + Urls.Identity.Addbank)
                        .tag(this)
                        .upJson(json)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject object = new JSONObject(s);
                                    String isSuccess = object.getString("isSuccess");
                                    if ("1".equals(isSuccess)) {
                                        String msg = object.getString("msg");
                                        ToastUtils.showToast(getActivity(), msg);
                                        bankBean.getBank().setDebit(debitBean);
                                        bankBean.getBank().setCredit(creditBean);

                                    } else {
                                        String msg = object.getString("msg");
                                        ToastUtils.showToast(getActivity(), msg);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }

        }

    }
   /* @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            //相当于Fragment的onPause
            LogUtils.i("22222","不可见");
        } else {
            // 相当于Fragment的onResume
            System.out.println("界面可见");
        }
    }*/

}
