package cn.com.cashninetrees.ui.activity.integarl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.base.BaseActivity;
import cn.com.cashninetrees.utils.RegexUtils;
import cn.com.cashninetrees.utils.ToastUtils;
import cn.com.cashninetrees.view.EmailAutoCompleteTextView;

public class DateChangeActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.et_name)
    FormEditText etName;
    @Bind(R.id.et_idCard)
    FormEditText etIdCard;
    @Bind(R.id.et_age)
    FormEditText etAge;
    @Bind(R.id.et_ContactPhone)
    FormEditText etContactPhone;
    @Bind(R.id.et_Address)
    FormEditText etAddress;
    @Bind(R.id.et_BankCard1)
    FormEditText etBankCard1;
    @Bind(R.id.et_ChineseName)
    FormEditText etChineseName;
    @Bind(R.id.et_TelePhoneArea)
    FormEditText etTelePhoneArea;
    @Bind(R.id.et_TelePhone)
    FormEditText etTelePhone;
    @Bind(R.id.et_Salary)
    FormEditText etSalary;
    @Bind(R.id.et_Email)
    EmailAutoCompleteTextView etEmail;

    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_change);
        ButterKnife.bind(this);
        initToolbar();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

    private void initToolbar() {
        type = getIntent().getIntExtra("type", 0);
        String hint = getIntent().getStringExtra("hint");
        switch (type) {
            case Urls.DateChange.NAME:
                titleName.setText("姓名");
                if(hint!=null){
                    etName.setText(hint);
                }
                etName.setVisibility(View.VISIBLE);
                break;
            case Urls.DateChange.AGE:
                titleName.setText("年龄");
                etAge.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etAge.setText(hint);
                }
                break;
            case Urls.DateChange.CONTACT_PHONE1:
            case Urls.DateChange.CONTACT_PHONE2:
                titleName.setText("联系人电话");
                etContactPhone.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etContactPhone.setText(hint);
                }
                break;
            case Urls.DateChange.IDCARD:
                titleName.setText("身份证号码");
                etIdCard.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etIdCard.setText(hint);
                }
                break;
            case Urls.DateChange.ADDRESS:
                titleName.setText("身份证地址");
                etAddress.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etAddress.setText(hint);
                }
                break;
            case Urls.DateChange.DEBIT_CARD:
                titleName.setText("借记卡卡号");
                etBankCard1.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etBankCard1.setText(hint);
                }
                break;
            case Urls.DateChange.CREDIT_CARD:
                titleName.setText("信用卡卡号");
                etBankCard1.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etBankCard1.setText(hint);
                }
                break;
            case Urls.DateChange.DEBIT_NAME:
            case Urls.DateChange.CREDIT_NAME:
                titleName.setText("持卡人姓名");
                etName.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etName.setText(hint);
                }
                break;
            case Urls.DateChange.DEBIT_PHONE:
                titleName.setText("银行绑定手机号");
                etContactPhone.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etContactPhone.setText(hint);
                }
                break;
            case Urls.DateChange.CREDIT_PHONE:
                titleName.setText("银行绑定手机号");
                etContactPhone.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etContactPhone.setText(hint);
                }
                break;
            case Urls.DateChange.STUHENT_NAME:
                titleName.setText("学校名称");
                etChineseName.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etChineseName.setText(hint);
                }
                break;
            case Urls.DateChange.COMPANY_NAME:
            case Urls.DateChange.BUSSINESS_NAME:
                titleName.setText("单位名称");
                etChineseName.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etChineseName.setText(hint);
                }
                break;
            case Urls.DateChange.STUHENT_ADRESS:
                titleName.setText("学校地址");
                etAddress.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etAddress.setText(hint);
                }
                break;
            case Urls.DateChange.COMPANY_ADRESS:
            case Urls.DateChange.BUSSINESS_ADRESS:
                titleName.setText("单位地址");
                etAddress.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etAddress.setText(hint);
                }
                break;
            case Urls.DateChange.STUHENT_TELEPHONE:
            case Urls.DateChange.COMPANY_TELEPHONE:
            case Urls.DateChange.BUSSINESS_TELEPHONE:
                titleName.setText("固定电话");
                etTelePhone.setVisibility(View.VISIBLE);
                etTelePhoneArea.setVisibility(View.VISIBLE);

                break;
            case Urls.DateChange.COMPANY_EMAIL:
            case Urls.DateChange.BUSSINESS_EMAIL:
                titleName.setText("邮箱地址");
                etEmail.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etEmail.setText(hint);
                }
                break;
            case Urls.DateChange.BUSSINESS_SALART:
            case Urls.DateChange.COMPANY_SALART:
                titleName.setText("月收入(税后)");
                etSalary.setVisibility(View.VISIBLE);
                if(hint!=null){
                    etSalary.setText(hint);
                }
                break;

        }

    }

    @OnClick({R.id.iv_back, R.id.iv_save,R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_save:
            case R.id.bt_save:
                commit_Date();
                break;
        }
    }

    private void commit_Date() {
        FormEditText[] allFields;
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case Urls.DateChange.NAME:
                allFields = new FormEditText[]{etName};
                backFinish(allFields, Urls.DateChange.NAME, etName);
                break;
            case Urls.DateChange.AGE:
                allFields = new FormEditText[]{etAge};
                backFinish(allFields, Urls.DateChange.AGE, etAge);
                break;
            case Urls.DateChange.CONTACT_PHONE1:
                allFields = new FormEditText[]{etContactPhone};
                backFinish(allFields, Urls.DateChange.CONTACT_PHONE1, etContactPhone);
                break;
            case Urls.DateChange.CONTACT_PHONE2:
                allFields = new FormEditText[]{etContactPhone};
                backFinish(allFields, Urls.DateChange.CONTACT_PHONE2, etContactPhone);
                break;
            case Urls.DateChange.IDCARD:
                allFields = new FormEditText[]{etIdCard};
                backFinish(allFields, Urls.DateChange.IDCARD, etIdCard);
                break;
            case Urls.DateChange.ADDRESS:
                allFields = new FormEditText[]{etAddress};
                backFinish(allFields, Urls.DateChange.ADDRESS, etAddress);
                break;
            case Urls.DateChange.CREDIT_CARD:
                allFields = new FormEditText[]{etBankCard1};
                backFinish(allFields, Urls.DateChange.CREDIT_CARD, etBankCard1);
                break;
            case Urls.DateChange.DEBIT_CARD:
                allFields = new FormEditText[]{etBankCard1};
                backFinish(allFields, Urls.DateChange.DEBIT_CARD, etBankCard1);
                break;
            case Urls.DateChange.DEBIT_PHONE:
                allFields = new FormEditText[]{etContactPhone};
                backFinish(allFields, Urls.DateChange.DEBIT_PHONE, etContactPhone);
                break;
            case Urls.DateChange.CREDIT_PHONE:
                allFields = new FormEditText[]{etContactPhone};
                backFinish(allFields, Urls.DateChange.CREDIT_PHONE, etContactPhone);
                break;
            case Urls.DateChange.DEBIT_NAME:
                allFields = new FormEditText[]{etName};
                backFinish(allFields, Urls.DateChange.DEBIT_NAME, etName);
                break;
            case Urls.DateChange.CREDIT_NAME:
                allFields = new FormEditText[]{etName};
                backFinish(allFields, Urls.DateChange.CREDIT_NAME, etName);
                break;
            case Urls.DateChange.STUHENT_NAME:
                allFields = new FormEditText[]{etChineseName};
                backFinish(allFields, Urls.DateChange.STUHENT_NAME, etChineseName);
                break;
            case Urls.DateChange.STUHENT_ADRESS:
                allFields = new FormEditText[]{etAddress};
                backFinish(allFields, Urls.DateChange.STUHENT_ADRESS, etAddress);
                break;
            case Urls.DateChange.STUHENT_TELEPHONE:
                allFields = new FormEditText[]{etTelePhone};
                boolean allValid = true;
                for (FormEditText field : allFields) {
                    allValid = field.testValidity() && allValid;
                }
                Bundle bundle = new Bundle();
                bundle.putString("are", etTelePhoneArea.getText().toString());
                bundle.putString("telephone", etTelePhone.getText().toString());
                if (allValid) {
                    setResult(Urls.DateChange.STUHENT_TELEPHONE, this.getIntent().putExtra("type", bundle));
                    finish();
                }
                break;
            case Urls.DateChange.COMPANY_TELEPHONE:
                allFields = new FormEditText[]{etTelePhone};
                boolean allValid1 = true;
                for (FormEditText field : allFields) {
                    allValid1 = field.testValidity() && allValid1;
                }
                Bundle bundle1 = new Bundle();
                bundle1.putString("are", etTelePhoneArea.getText().toString());
                bundle1.putString("telephone", etTelePhone.getText().toString());
                if (allValid1) {
                    setResult(Urls.DateChange.COMPANY_TELEPHONE, this.getIntent().putExtra("type", bundle1));
                    finish();
                }
                break;
            case Urls.DateChange.BUSSINESS_TELEPHONE:
                allFields = new FormEditText[]{etTelePhone};
                boolean allValid2 = true;
                for (FormEditText field : allFields) {
                    allValid2 = field.testValidity() && allValid2;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("are", etTelePhoneArea.getText().toString());
                bundle2.putString("telephone", etTelePhone.getText().toString());
                if (allValid2) {
                    setResult(Urls.DateChange.BUSSINESS_TELEPHONE, this.getIntent().putExtra("type", bundle2));
                    finish();
                }
                break;
            case Urls.DateChange.COMPANY_NAME:
                allFields = new FormEditText[]{etChineseName};
                backFinish(allFields, Urls.DateChange.COMPANY_NAME, etChineseName);
                break;
            case Urls.DateChange.BUSSINESS_NAME:
                allFields = new FormEditText[]{etChineseName};
                backFinish(allFields, Urls.DateChange.BUSSINESS_NAME, etChineseName);
                break;
            case Urls.DateChange.COMPANY_ADRESS:
                allFields = new FormEditText[]{etAddress};
                backFinish(allFields, Urls.DateChange.COMPANY_ADRESS, etAddress);
                break;
            case Urls.DateChange.BUSSINESS_ADRESS:
                allFields = new FormEditText[]{etAddress};
                backFinish(allFields, Urls.DateChange.BUSSINESS_ADRESS, etAddress);
                break;
            case Urls.DateChange.COMPANY_EMAIL:
                String emailStr = etEmail.getText().toString();
                if (!emailStr.isEmpty() && RegexUtils.isEmail(emailStr)) {
                    setResult(Urls.DateChange.COMPANY_EMAIL, this.getIntent().putExtra("type", emailStr));
                    finish();
                } else {
                    ToastUtils.showToast(this, "邮箱地址格式错误");
                }
                break;
            case Urls.DateChange.BUSSINESS_EMAIL:
                String bussiness_emailStr = etEmail.getText().toString();
                if (!bussiness_emailStr.isEmpty() && RegexUtils.isEmail(bussiness_emailStr)) {
                    setResult(Urls.DateChange.BUSSINESS_EMAIL, this.getIntent().putExtra("type", bussiness_emailStr));
                    finish();
                } else {
                    ToastUtils.showToast(this, "邮箱地址格式错误");
                }
                break;
            case Urls.DateChange.COMPANY_SALART:
                allFields = new FormEditText[]{etSalary};
                backFinish(allFields, Urls.DateChange.COMPANY_SALART, etSalary);
                break;
            case Urls.DateChange.BUSSINESS_SALART:
                allFields = new FormEditText[]{etSalary};
                backFinish(allFields, Urls.DateChange.BUSSINESS_SALART, etSalary);
                break;


        }
    }

    /**
     * @param allFields  效验 editText
     * @param resultCode
     * @param editText
     */
    private void backFinish(FormEditText[] allFields, int resultCode, EditText editText) {
        boolean allValid = true;
        for (FormEditText field : allFields) {
            allValid = field.testValidity() && allValid;
        }
        if (allValid) {
            setResult(resultCode, this.getIntent().putExtra("type", editText.getText().toString()));
            finish();
        }
    }
}
