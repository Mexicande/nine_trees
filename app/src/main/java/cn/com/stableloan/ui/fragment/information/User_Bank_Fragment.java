package cn.com.stableloan.ui.fragment.information;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.bean.IdentitySave;
import cn.com.stableloan.interfaceutils.Identivity_interface;
import cn.com.stableloan.model.Bank;
import cn.com.stableloan.model.BankInformation;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.activity.Camera2Activity;
import cn.com.stableloan.ui.activity.CameraActivity;
import cn.com.stableloan.ui.activity.GestureLoginActivity;
import cn.com.stableloan.ui.activity.IdentityinformationActivity;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.ui.activity.integarl.DateChangeActivity;
import cn.com.stableloan.ui.fragment.dialogfragment.FingerFragment;
import cn.com.stableloan.utils.BankUtils;
import cn.com.stableloan.utils.CommonUtils;
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
 * @author apple
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
    private static final int REQUEST_BANK = 200;

    public static final int DEBIT_CODE = 4;
    public static final int CREDIT_CODE = 5;

    private String APP_CODE="";
    private boolean creditFlag = false;

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

        etValidityTime1.setOnSuperTextViewClickListener(superTextView -> pvTime.show(superTextView));
        etValidityTime2.setOnSuperTextViewClickListener(superTextView -> pvTime.show(superTextView));
        isCredit.setOnSuperTextViewClickListener(superTextView -> {
            int visibility = layoutCredit.getVisibility();
            if (visibility == View.GONE) {
                layoutCredit.setVisibility(View.VISIBLE);
                isCredit.setRightString("有");
            } else {
                layoutCredit.setVisibility(View.GONE);
                isCredit.setRightString("没有");
            }
        });

    }

    private void setSuperText(SuperTextView superText, int type) {
                Intent intent = new Intent(getActivity(), DateChangeActivity.class);
                intent.putExtra("type", type);
        String rightString = superText.getRightString();
        String replace = rightString.replace(" ", "");
        intent.putExtra("hint", replace);
                startActivityForResult(intent, REQUEST_CODE);
    }

    private void initTime() {
        int color = getResources().getColor(R.color.colorPrimary);

      /*  JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("persmaterials2", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//记录事件
        ZhugeSDK.getInstance().track(getActivity(), "身份信息第二页", eventObject);
*/
        pvTime = new TimePickerView.Builder(getActivity(), (date, v) -> {
            SuperTextView btn = (SuperTextView) v;
            btn.setRightString(getTime(date));
        })
                .setCancelText("取消")
                .setTitleColor(color)
                .setSubmitText("确定")
                .setDividerColor(color)
                //设置选中项文字颜色
                .setTextColorCenter(Color.BLACK)
                .setSubmitColor(color)
                .setCancelColor(color)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "")
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                .build();

    }

    @Subscribe
    public void onMessageEvent(InformationEvent event) {
        String message = event.message;
        if ("bankinformation".equals(message)) {
            getDate();
        }
        if("saveBank".equals(message)){
            EventBus.getDefault().post(new IdentitySave(false,changeTest(),false));
        }
        if("exitBank".equals(message)){
            Save(true);
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
                                APP_CODE=information.getData().getApp_code();
                                if (("1").equals(information.getData().getStatus())) {
                                    bankBean = information.getData();

                                    String dnumber = bankBean.getBank().getDebit().getDnumber();
                                    StringBuffer stringBuffer=new StringBuffer(dnumber);

                                        switch (dnumber.length()){
                                            case 16:
                                                stringBuffer.insert(4," ");
                                                stringBuffer.insert(9," ");
                                                stringBuffer.insert(14," ");
                                                break;
                                            case 17:
                                                stringBuffer.insert(6," ");
                                                stringBuffer.insert(11," ");
                                                break;
                                            case 19:
                                                stringBuffer.insert(6," ");
                                                break;
                                            default:
                                                break;
                                        }
                                    etBankCard1.setRightString(stringBuffer);

                                    etBankPersonName1.setRightString(bankBean.getBank().getDebit().getDname());




                                    etBankPhone1.setRightString(bankBean.getBank().getDebit().getDphone());
                                    etSelectBank1.setRightString(bankBean.getBank().getDebit().getDbank());
                                    etValidityTime1.setRightString(bankBean.getBank().getDebit().getDperiod());

                                    int is_credit = bankBean.getBank().getCredit().getIs_credit();

                                    if (is_credit == 1) {
                                        layoutCredit.setVisibility(View.VISIBLE);
                                        isCredit.setRightString("有");
                                    } else {
                                        isCredit.setRightString("没有");
                                    }
                                    String cnumber = bankBean.getBank().getCredit().getCnumber();

                                    StringBuffer buffer = new StringBuffer(cnumber);

                                    switch (cnumber.length()){
                                        case 16:
                                            buffer.insert(4," ");
                                            buffer.insert(9," ");
                                            buffer.insert(14," ");
                                            break;
                                        case 17:
                                            buffer.insert(6," ");
                                            buffer.insert(11," ");
                                            break;
                                        case 19:
                                            buffer.insert(6," ");
                                            break;
                                        default:
                                            break;
                                    }
                                    etBankCard2.setRightString(buffer);

                                    etBankPersonName2.setRightString(bankBean.getBank().getCredit().getCname());
                                    etBankPhone2.setRightString(bankBean.getBank().getCredit().getCphone());
                                    etSelectBank2.setRightString(bankBean.getBank().getCredit().getCbank());
                                    etValidityTime2.setRightString(bankBean.getBank().getCredit().getCperiod());

                                } else {
                                    String userphone = (String) SPUtils.get(getActivity(), Urls.lock.USER_PHONE, "1");

                                    final TinyDB tinyDB = new TinyDB(getActivity());
                                    UserBean user = (UserBean) tinyDB.getObject(userphone, UserBean.class);
                                    String phone = user.getUserphone();
                                    String gesturePassword = aCache.getAsString(phone);
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

    private void Save(boolean b) {
        final Bank.BankBean.CreditBean creditBean = new Bank.BankBean.CreditBean();
        creditBean.setCbank(etSelectBank2.getRightString());
        creditBean.setCname(etBankPersonName2.getRightString());

        String rightString2 = etBankCard2.getRightString();
        String replace2 = rightString2.replace(" ", "");
        creditBean.setCnumber(replace2);


        creditBean.setCperiod(etValidityTime2.getRightString());
        creditBean.setCphone(etBankPhone2.getRightString());

        int visibility = layoutCredit.getVisibility();
        if (visibility == View.GONE) {
            creditBean.setIs_credit(2);
        } else {
            creditBean.setIs_credit(1);
        }

        final Bank.BankBean.DebitBean debitBean = new Bank.BankBean.DebitBean();
        debitBean.setDbank(etSelectBank1.getRightString());
        debitBean.setDname(etBankPersonName1.getRightString());

        String rightString = etBankCard1.getRightString();
        String replace = rightString.replace(" ", "");
        debitBean.setDnumber(replace);

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
                                    if(!b){
                                        ToastUtils.showToast(getActivity(), msg);
                                    }
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
    private boolean changeTest(){
        final Bank.BankBean.CreditBean creditBean = new Bank.BankBean.CreditBean();
        creditBean.setCbank(etSelectBank2.getRightString());
        creditBean.setCname(etBankPersonName2.getRightString());

        String rightString2 = etBankCard2.getRightString();
        String replace2 = rightString2.replace(" ", "");
        creditBean.setCnumber(replace2);


        creditBean.setCperiod(etValidityTime2.getRightString());
        creditBean.setCphone(etBankPhone2.getRightString());

        int visibility = layoutCredit.getVisibility();
        if (visibility == View.GONE) {
            creditBean.setIs_credit(2);
        } else {
            creditBean.setIs_credit(1);
        }

        final Bank.BankBean.DebitBean debitBean = new Bank.BankBean.DebitBean();
        debitBean.setDbank(etSelectBank1.getRightString());
        debitBean.setDname(etBankPersonName1.getRightString());

        String rightString = etBankCard1.getRightString();
        String replace = rightString.replace(" ", "");
        debitBean.setDnumber(replace);

        debitBean.setDperiod(etValidityTime1.getRightString());
        debitBean.setDphone(etBankPhone1.getRightString());
        if (bankBean.getBank().getCredit().equals(creditBean) && bankBean.getBank().getDebit().equals(debitBean)) {
            return false;
        } else {
            return true;
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
                        etBankCard1.setRightString(type);
                        if (type.length() > 6) {
                            // 获取银行卡的信息
                            String name = BankUtils.getNameOfBank(type);
                            etSelectBank1.setRightString(name);
                        }
                    }
                    break;
                case Urls.DateChange.CREDIT_CARD:
                    if (data != null) {
                        String type = data.getStringExtra("type");
                        etBankCard2.setRightString(type);
                        if (type.length() > 6) {
                            // 获取银行卡的信息
                            String name = BankUtils.getNameOfBank(type);
                            etSelectBank2.setRightString(name);
                        }
                    }
                    break;
                case Urls.DateChange.DEBIT_NAME:
                    if (data != null) {
                        String type = data.getStringExtra("type");
                        etBankPersonName1.setRightString(type);
                    }
                    break;
                case Urls.DateChange.CREDIT_NAME:
                    if (data != null) {
                        String type = data.getStringExtra("type");
                        etBankPersonName2.setRightString(type);
                    }
                    break;
                case Urls.DateChange.DEBIT_PHONE:
                    if (data != null) {
                        String type = data.getStringExtra("type");
                        etBankPhone1.setRightString(type);
                    }
                    break;
                case Urls.DateChange.CREDIT_PHONE:
                    if (data != null) {
                        String type = data.getStringExtra("type");
                        etBankPhone2.setRightString(type);
                    }
                    break;
                default:
                    break;

            }
        }else if(requestCode==REQUEST_BANK){
            switch (resultCode){
                case DEBIT_CODE:
                    if (data != null) {
                        String type = data.getStringExtra("card_num");
                        StringBuffer buffer = new StringBuffer(type);
                        switch (type.length()){
                            case 16:
                                buffer.insert(4," ");
                                buffer.insert(9," ");
                                buffer.insert(14," ");
                                break;
                            case 17:
                                buffer.insert(6," ");
                                buffer.insert(11," ");
                                break;
                            case 19:
                                buffer.insert(6," ");
                                break;
                            default:
                                break;
                        }
                        etBankCard1.setRightString(buffer);
                        if (type.length() > 6) {
                            // 获取银行卡的信息
                            String name = BankUtils.getNameOfBank(type);
                            etSelectBank1.setRightString(name);
                        }
                    }
                    break;
                case CREDIT_CODE:
                    if (data != null) {
                        String type = data.getStringExtra("card_num");
                        StringBuffer buffer = new StringBuffer(type);

                        switch (type.length()){
                            case 16:
                                buffer.insert(4," ");
                                buffer.insert(9," ");
                                buffer.insert(14," ");
                                break;
                            case 17:
                                buffer.insert(6," ");
                                buffer.insert(11," ");
                                break;
                            case 19:
                                buffer.insert(6," ");
                                break;
                            default:
                                break;
                        }
                        etBankCard2.setRightString(buffer);
                        if (type.length() > 6) {
                            // 获取银行卡的信息
                            String name = BankUtils.getNameOfBank(type);
                            etSelectBank2.setRightString(name);
                            int visibility = layoutCredit.getVisibility();
                            if (visibility == View.GONE) {
                                layoutCredit.setVisibility(View.VISIBLE);
                                isCredit.setRightString("有");
                            }
                        }
                    }
                    break;
                default:
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


    @OnClick({R.id.debit_camera, R.id.et_BankCard1, R.id.et_BankPersonName1,
            R.id.et_SelectBank1, R.id.et_ValidityTime1, R.id.et_BankPhone1,
            R.id.credit_camera, R.id.is_credit, R.id.et_BankCard2,
            R.id.et_BankPersonName2, R.id.et_BankPhone2, R.id.et_SelectBank2,
            R.id.et_ValidityTime2, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.debit_camera:
                getCameraPermission(DEBIT_CODE);
                break;
            case R.id.et_BankCard1:
                setSuperText(etBankCard1, Urls.DateChange.DEBIT_CARD);
                break;
            case R.id.et_BankPersonName1:
                setSuperText(etBankPersonName1, Urls.DateChange.DEBIT_NAME);
                break;
            case R.id.et_BankPhone1:
                setSuperText(etBankPhone1, Urls.DateChange.DEBIT_PHONE);
                break;
            case R.id.et_SelectBank1:
                break;
            case R.id.credit_camera:
                getCameraPermission(CREDIT_CODE);
                break;
            case R.id.is_credit:
                break;
            case R.id.et_BankCard2:
                setSuperText(etBankCard2, Urls.DateChange.CREDIT_CARD);
                break;
            case R.id.et_BankPersonName2:
                setSuperText(etBankPersonName2, Urls.DateChange.CREDIT_NAME);
                break;
            case R.id.et_BankPhone2:
                setSuperText(etBankPhone2, Urls.DateChange.CREDIT_PHONE);

                break;
            case R.id.et_SelectBank2:
                break;
            case R.id.save:
                if (bankBean != null) {
                    Save(false);
                }
                break;
            default:
                break;
        }

    }

    private void getCameraPermission(int type) {
        AndPermission.with(getActivity())
                .requestCode(type)
                .permission(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .callback(listener)
                .start();

    }
    private File mFile;

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantedPermissions) {
            // 权限申请成功回调。
            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == DEBIT_CODE) {
                Intent intent = new Intent();
                mFile = CommonUtils.createImageFile("mFile");
                //文件保存的路径和名称
                intent.putExtra("file", mFile.toString());
                //拍照时的提示文本
                intent.putExtra("app_code",APP_CODE);
                intent.putExtra("hint", "请将证件放入框内。将裁剪图片，只保留框内区域的图像");
                //是否使用整个画面作为取景区域(全部为亮色区域)
                intent.putExtra("hideBounds", false);
                intent.putExtra("type",DEBIT_CODE);
                //最大允许的拍照尺寸（像素数）
                intent.putExtra("maxPicturePixels", 3840 * 2160);
                //startActivityForResult(intent, AppApplication.TAKE_PHOTO_CUSTOM);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent.setClass(getActivity(), Camera2Activity.class);
                    startActivityForResult(intent,REQUEST_BANK);
                } else {
                    intent.setClass(getActivity(), CameraActivity.class);
                    startActivityForResult(intent,REQUEST_BANK);
                }
            }else if(requestCode==CREDIT_CODE){
                Intent intent = new Intent();
                mFile = CommonUtils.createImageFile("mFile");
                //文件保存的路径和名称
                intent.putExtra("file", mFile.toString());
                //拍照时的提示文本
                intent.putExtra("app_code",APP_CODE);

                intent.putExtra("hint", "请将证件放入框内。将裁剪图片，只保留框内区域的图像");
                //是否使用整个画面作为取景区域(全部为亮色区域)
                intent.putExtra("hideBounds", false);
                intent.putExtra("type",CREDIT_CODE);
                //最大允许的拍照尺寸（像素数）
                intent.putExtra("maxPicturePixels", 3840 * 2160);
                //startActivityForResult(intent, AppApplication.TAKE_PHOTO_CUSTOM);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent.setClass(getActivity(), Camera2Activity.class);
                    startActivityForResult(intent,REQUEST_BANK);
                } else {
                    intent.setClass(getActivity(), CameraActivity.class);
                    startActivityForResult(intent,REQUEST_BANK);
                }
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 500) {
                // TODO ...
                ToastUtils.showToast(getActivity(), "获取权限失败，请在设置中手动添加拍照权限");
            }
        }
    };

}
