package cn.com.stableloan.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andreabaccega.widget.FormEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.stableloan.R;
import cn.com.stableloan.utils.BankUtils;
import cn.com.stableloan.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankInformationFragment extends Fragment {


    @Bind(R.id.et_BankCard1)
    FormEditText etBankCard1;
    @Bind(R.id.et_BankPersonName1)
    FormEditText etBankPersonName1;

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

        return view;
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
                    etBankPersonName1.setText(name);
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
}
