package cn.com.stableloan.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andreabaccega.widget.FormEditText;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.Bank;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.utils.BankUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
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
    @Bind(R.id.et_ValidityTime)
    FormEditText etValidityTime;
    @Bind(R.id.et_ValidityTime1)
    FormEditText etValidityTime1;
    @Bind(R.id.et_SelectBank2)
    FormEditText etSelectBank2;
    @Bind(R.id.et_BankPhone2)
    FormEditText etBankPhone2;

    public BankInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_information, container, false);
        ButterKnife.bind(this, view);
        setListener();

        getDate();
        return view;
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
                                    Bank bank = gson.fromJson(s, Bank.class);

                                    etBankCard1.setText(bank.getBank().getDebit().getDnumber());
                                    etBankPersonName1.setText(bank.getBank().getDebit().getDname());
                                    etBankPhone1.setText(bank.getBank().getDebit().getDphone());
                                    etSelectBank1.setText(bank.getBank().getDebit().getDbank());
                                    etValidityTime1.setText(bank.getBank().getCredit().getCperiod());


                                    etBankCard2.setText(bank.getBank().getCredit().getCnumber());
                                    etBankPersonName2.setText(bank.getBank().getCredit().getCname());
                                    etBankPhone2.setText(bank.getBank().getCredit().getCphone());
                                    etSelectBank2.setText(bank.getBank().getCredit().getCbank());
                                    etValidityTime.setText(bank.getBank().getCredit().getCperiod());
                                 /*   Gson gson = new Gson();
                                    Identity.IdentityBean identity = gson.fromJson(string, Identity.IdentityBean.class);

                                    etName.setText(identity.getName());
                                    etIDCard.setText(identity.getIdcard());
                                    etSex.setText(identity.getSex());
                                    etAge.setText(identity.getAge());
                                    etAddress.setText(identity.getIdaddress());
                                    // etMarriage.setText(bean.getMarriage());
                                    etCity.setText(identity.getCity());*/

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

        etBankCard1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int huoqu = etBankCard1.getText().toString().length();
                if (huoqu >= 6) {
                    String huoqucc = etBankCard1.getText().toString();
                    String name = BankUtils.getNameOfBank(huoqucc);// 获取银行卡的信息
                    etSelectBank1.setText(name);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        etBankCard2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int huoqu = etBankCard2.getText().toString().length();
                if (huoqu >= 6) {
                    String huoqucc = etBankCard2.getText().toString();
                    String name = BankUtils.getNameOfBank(huoqucc);// 获取银行卡的信息
                    etBankPersonName2.setText(name);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.Save)
    public void onViewClicked() {
        Save();
    }

    private void Save() {
        TinyDB tinyDB = new TinyDB(getActivity());
        // identity.getIdentity().setMarriage(etMarriage.getText().toString());
        String token = (String) SPUtils.get(getActivity(), "token", "1");

        Bank bank=new Bank();
        bank.setToken(token);
        Bank.BankBean bean=new Bank.BankBean();
        bean.setBstatus("0");
        Bank.BankBean.CreditBean creditBean=new Bank.BankBean.CreditBean();
        creditBean.setCbank(etSelectBank1.getText().toString());
        creditBean.setCname(etBankPersonName1.getText().toString());
        creditBean.setCnumber(etBankCard1.getText().toString());
        creditBean.setCperiod(etValidityTime.getText().toString());
        creditBean.setCphone(etBankPhone1.getText().toString());

        Bank.BankBean.DebitBean debitBean=new Bank.BankBean.DebitBean();
        debitBean.setDbank(etSelectBank2.getText().toString());
        debitBean.setDname(etBankPersonName2.getText().toString());
        debitBean.setDnumber(etBankCard2.getText().toString());
        debitBean.setDperiod(etValidityTime.getText().toString());
        debitBean.setDphone(etBankPhone2.getText().toString());

        bean.setCredit(creditBean);
        bean.setDebit(debitBean);

        bank.setBank(bean);
        Gson gson=new Gson();
        String json = gson.toJson(bank);


        OkGo.<String>post(Urls.NEW_URL+Urls.Identity.Addbank)
                .tag(this)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object=new JSONObject(s);
                            String isSuccess = object.getString("isSuccess");
                            if("1".equals(isSuccess)){
                                String msg = object.getString("msg");
                                ToastUtils.showToast(getActivity(),msg);
                            }else {
                                String msg = object.getString("msg");
                                ToastUtils.showToast(getActivity(),msg);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
