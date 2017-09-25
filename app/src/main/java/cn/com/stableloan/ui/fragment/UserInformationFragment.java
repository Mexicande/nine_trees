package cn.com.stableloan.ui.fragment;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zaaach.citypicker.CityPickerActivity;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.bean.CameraEvent;
import cn.com.stableloan.model.CardBean;
import cn.com.stableloan.model.Identity;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.activity.Camera2Activity;
import cn.com.stableloan.ui.activity.GestureLoginActivity;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.utils.CommonUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.RegexUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.view.BetterSpinner;
import cn.com.stableloan.view.RoundButton;
import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInformationFragment extends Fragment {


    @Bind(R.id.et_Marriage)
    BetterSpinner etMarriage;
    @Bind(R.id.et_Between1)
    BetterSpinner etBetween1;
    @Bind(R.id.et_Between2)
    BetterSpinner etBetween2;
    @Bind(R.id.et_City)
    TextView etCity;
    @Bind(R.id.et_Contact1)
    FormEditText etContact1;
    @Bind(R.id.et_Contact)
    FormEditText etContact;
    @Bind(R.id.et_name)
    FormEditText etName;
    @Bind(R.id.user_phone)
    TextView userPhone;
    @Bind(R.id.et_IDCard)
    FormEditText etIDCard;
    @Bind(R.id.et_Address)
    FormEditText etAddress;
    @Bind(R.id.et_Contact_name)
    FormEditText etContactName;
    @Bind(R.id.et_Contact_name2)
    FormEditText etContactName2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.getCity)
    RoundButton getCity;
    @Bind(R.id.layout)
    LinearLayout layout;
    @Bind(R.id.getContact1)
    RoundButton getContact1;
    @Bind(R.id.layout4)
    LinearLayout layout4;
    @Bind(R.id.layout1)
    LinearLayout layout1;
    @Bind(R.id.getContact2)
    RoundButton getContact2;
    @Bind(R.id.layout5)
    LinearLayout layout5;
    @Bind(R.id.layout3)
    LinearLayout layout3;
    @Bind(R.id.et_Sex)
    TextView etSex;
    @Bind(R.id.et_Age1)
    TextView etAge1;
    @Bind(R.id.save)
    RoundButton save;
    private ACache aCache;


    private UserBean user;
    private String[] list1;

    private String[] list;

    File mFile;


    private Identity.DataBean.IdentityBean identityBean;

    public UserInformationFragment() {
        // Required empty public constructor
    }

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.fragment_user_information, container, false);
        ButterKnife.bind(this, view);
        aCache = ACache.get(getActivity());
        EventBus.getDefault().register(this);
        getDate();
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initSpinner();
        setListenter();

        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(getActivity().getCurrentFocus()!=null && getActivity().getCurrentFocus().getWindowToken()!=null){
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });

        return view;
    }

    private static final int REQUEST_CODE_PICK_CITY = 0;


    private void setListenter() {

        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("persmaterials1", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//记录事件
        ZhugeSDK.getInstance().track(getActivity(), "身份信息", eventObject);

        etCity.setFocusableInTouchMode(false);
        etCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
            }
        });

        etSex.setKeyListener(null);
        etAge1.setKeyListener(null);
        etIDCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String idCard = etIDCard.getText().toString();
                if (idCard.length() == 18) {
                    boolean idCard18 = RegexUtils.isIDCard18(idCard);
                    if (idCard18) {
                        char c = idCard.charAt(16);
                        int i = Integer.parseInt(String.valueOf(c));
                        if (i % 2 == 0) {
                            etSex.setText("女");
                        } else {
                            etSex.setText("男");
                        }
                        int age = IdNOToAge(idCard);
                        etAge1.setText(String.valueOf(age));
                    }
                }
            }
        });
    }

    public static int IdNOToAge(String IdNO) {
        int leh = IdNO.length();
        String dates = "";
        if (leh == 18) {
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(new Date());
            int u = Integer.parseInt(year) - Integer.parseInt(dates);
            return u;
        } else {
            dates = IdNO.substring(6, 8);

            return Integer.parseInt(dates);
        }

    }

    @Subscribe
    public void onMessageEvent(InformationEvent event) {
        String message = event.message;
        if ("ok".equals(message)) {
            getDate();
        }
    }

    @Subscribe
    public void onCameraEvent(CameraEvent event) {
        CardBean.OutputsBean.OutputValueBean.DataValueBean value = event.value;
        if (value != null) {
            LogUtils.i("111111", value.toString());
            if (value.getName() != null) {
                etName.setText(value.getName());
            }
            if (value.getNum() != null) {
                etIDCard.setText(value.getNum());
            }
            if (value.getSex() != null) {
                etSex.setText(value.getSex());
            }
            if (value.getAddress() != null) {
                etAddress.setText(value.getAddress());
            }
        }
    }

    private void getDate() {

        TinyDB tinyDB = new TinyDB(getActivity());
        user = (UserBean) tinyDB.getObject("user", UserBean.class);

        userPhone.setText(user.getUserphone());

        Map<String, String> parms = new HashMap<>();
        String token = (String) SPUtils.get(getActivity(), "token", "1");
        String signature = (String) SPUtils.get(getActivity(), "signature", "1");
        parms.put("token", token);
        parms.put("signature", signature);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.Ip_url + Urls.Identity.GetIdentity)
                .tag(getActivity())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        if(s!=null){
                            Gson gson=new Gson();
                            Identity identity = gson.fromJson(s, Identity.class);
                            if(identity.getError_code()==0){
                                if(identity.getData().getIsSuccess().equals("1")){
                                    if(identity.getData().getStatus().equals("1")){
                                        identityBean = identity.getData().getIdentity();
                                        etName.setText(identityBean.getName());
                                        etIDCard.setText(identityBean.getIdcard());
                                        String sex = identityBean.getSex();
                                        if ("0".equals(sex)) {
                                            etSex.setText("女");
                                        } else if ("1".equals(sex)) {
                                            etSex.setText("男");
                                        }
                                        etAge1.setText(identityBean.getAge());

                                        etAddress.setText(identityBean.getIdaddress());
                                        String marriage = identityBean.getMarriage();
                                        if ("0".equals(marriage)) {
                                            etMarriage.setText("未婚");
                                        } else if ("1".equals(marriage)) {
                                            etMarriage.setText("已婚");
                                        }
                                        etCity.setText(identityBean.getCity());
                                        Identity.DataBean.IdentityBean.ContactBean bean = identityBean.getContact().get(0);
                                        etContact.setText(bean.getUserphone());

                                        etContactName.setText(bean.getContact());

                                        String bet = bean.getRelation();

                                        if (!bet.isEmpty()) {
                                            int i2 = Integer.parseInt(bet);
                                            etBetween1.setText(list[i2]);
                                        }

                                        Identity.DataBean.IdentityBean.ContactBean bean1 = identityBean.getContact().get(1);

                                        etContact1.setText(bean1.getUserphone());
                                        etContactName2.setText(bean1.getContact());

                                        String bet2 = bean1.getRelation();

                                        if (!bet2.isEmpty()) {
                                            int i1 = Integer.parseInt(bet2);
                                            etBetween2.setText(list[i1]);
                                        }
                                    }else {


                                        final TinyDB tinyDB = new TinyDB(getActivity());
                                        UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
                                        String userphone = user.getUserphone();
                                        String gesturePassword = aCache.getAsString(userphone);
                                        String lock = aCache.getAsString("lock");

                                        if(gesturePassword == null || "".equals(gesturePassword)||"off".equals(lock)) {
                                            Intent intent = new Intent(getActivity(), GestureLoginActivity.class).putExtra("from", "UserInformation");
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "UserInformation");
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }else {
                                ToastUtils.showToast(getActivity(),identity.getError_message());
                            }
                        }

                    }
                });

    }

    private void initSpinner() {

        list1 = getResources().getStringArray(R.array.spingarr);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list1);
        etMarriage.setAdapter(adapter1);
        list = getResources().getStringArray(R.array.between);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list);
        etBetween1.setAdapter(adapter2);

        etBetween2.setAdapter(adapter2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.getCity, R.id.getContact1, R.id.getContact2, R.id.save, R.id.layout_camera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getCity:
                getLocationPermission();
                break;
            case R.id.getContact1:
                getPermission(1);
                break;
            case R.id.getContact2:
                getPermission(2);
                break;
            case R.id.save:
                saveDate();
                break;
            case R.id.layout_camera:
                getCameraPermission();
                break;
        }
    }

    private void getCameraPermission() {
        AndPermission.with(getActivity())
                .requestCode(500)
                .permission(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .callback(listener)
                .start();

    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 500) {
                Intent intent = new Intent(getActivity(), Camera2Activity.class);
                mFile = CommonUtils.createImageFile("mFile");
                //文件保存的路径和名称
                intent.putExtra("file", mFile.toString());
                //拍照时的提示文本
                intent.putExtra("hint", "请将证件放入框内。将裁剪图片，只保留框内区域的图像");
                //是否使用整个画面作为取景区域(全部为亮色区域)
                intent.putExtra("hideBounds", false);
                //最大允许的拍照尺寸（像素数）
                intent.putExtra("maxPicturePixels", 3840 * 2160);
                //startActivityForResult(intent, AppApplication.TAKE_PHOTO_CUSTOM);
                startActivity(intent);
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 500) {
                // TODO ...
            }
        }
    };

    private void saveDate() {
        final Identity.DataBean.IdentityBean identity1 = new Identity.DataBean.IdentityBean();
        String userName = etName.getText().toString();
        String IdCard = etIDCard.getText().toString();
        String address = etAddress.getText().toString();
        String age = etAge1.getText().toString();
        String city = etCity.getText().toString();
        String between1 = etBetween1.getText().toString();
        String between2 = etBetween2.getText().toString();
        String contact1 = etContact.getText().toString();
        String contact2 = etContact1.getText().toString();
        String contactName1 = etContactName.getText().toString();
        String contactName2 = etContactName2.getText().toString();

        Identity.DataBean.IdentityBean.ContactBean identity2 = new Identity.DataBean.IdentityBean.ContactBean();
        identity2.setUserphone(contact1);
        identity2.setContact(contactName1);
        identity2.setRelation("");

        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(between1)) {
                String s = String.valueOf(i);
                identity2.setRelation(s);
            }
        }

        Identity.DataBean.IdentityBean.ContactBean identity3 = new Identity.DataBean.IdentityBean.ContactBean();
        identity3.setUserphone(contact2);
        identity3.setContact(contactName2);
        identity3.setRelation("");

        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(between2)) {
                String s = String.valueOf(i);
                identity3.setRelation(s);
            }
        }
        String s1 = etSex.getText().toString();
        if ("女".equals(s1)) {
            identity1.setSex("0");
        } else if ("男".equals(s1)) {
            identity1.setSex("1");
        } else {
            identity1.setSex("");
        }

        String marr = etMarriage.getText().toString();
        identity1.setMarriage("");
        for (int i = 0; i < list1.length; i++) {
            if (list1[i].equals(marr)) {
                String s = String.valueOf(i);
                identity1.setMarriage(s);
            }
        }

        Identity.DataBean.IdentityBean bean = new Identity.DataBean.IdentityBean();
        bean.setSex(identity1.getSex());
        bean.setMarriage(identity1.getMarriage());
        bean.setAge(age);
        bean.setCity(city);
        bean.setIdaddress(address);
        bean.setIdcard(IdCard);
        bean.setName(userName);

        if (!changeTest()) {
            commitChange(identity1, identity2, identity3);
        } else {
            ToastUtils.showToast(getActivity(), "无修改内容");
        }

    }

    /**
     * 修改提交
     *
     * @param identity2
     * @param identity3
     */
    private void commitChange(final Identity.DataBean.IdentityBean identity1, Identity.DataBean.IdentityBean.ContactBean identity2,
                              Identity.DataBean.IdentityBean.ContactBean identity3) {

        FormEditText[] allFields = {etName, etIDCard, etContact, etContact1};
        boolean allValid = true;
        for (FormEditText field : allFields) {
            allValid = field.testValidity() && allValid;
        }
        if (allValid) {
            if (!etContact.getText().toString().isEmpty() && !etMarriage.getText().toString().isEmpty() && !etContactName.getText().toString().isEmpty()
                    && !etCity.getText().toString().isEmpty() && !etAddress.getText().toString().isEmpty() && !etAge1.getText().toString().isEmpty()
                    && !etContact1.getText().toString().isEmpty() && !etContactName2.getText().toString().isEmpty() && !etIDCard.getText().toString().isEmpty()
                    && !etSex.getText().toString().isEmpty() && !etBetween1.getText().toString().isEmpty() && !etBetween2.getText().toString().isEmpty()
                    && !userPhone.getText().toString().isEmpty() && !etName.getText().toString().isEmpty()) {
                identity1.setIstatus("1");
            } else {
                identity1.setIstatus("0");
            }
            identity1.setName(etName.getText().toString());
            identity1.setAge(etAge1.getText().toString());
            identity1.setCity(etCity.getText().toString());
            identity1.setIdcard(etIDCard.getText().toString());
            identity1.setIdaddress(etAddress.getText().toString());
            identity1.setUserphone(userPhone.getText().toString());

            ArrayList<Identity.DataBean.IdentityBean.ContactBean> list = new ArrayList<>();
            list.add(identity2);
            list.add(identity3);
            identity1.setContact(list);
            String token = (String) SPUtils.get(getActivity(), "token", "1");
            Identity.DataBean date=new Identity.DataBean();
            date.setIdentity(identity1);
            date.setToken(token);
           // identity.setData(date);
            /*identity.getData().getIdentity().setToken(token);*/
            identity1.setUserphone(user.getUserphone());

            JSONObject object=new JSONObject();
            try {
                object.put("identity",identity1);
                object.put("token",token);
            } catch (JSONException e) {
                e.printStackTrace();
            }
/*
            HashMap<String, String> params = new HashMap<>();
            params.put("identity", identity1.toString());
            params.put("token", token);
            JSONObject jsonObject=new JSONObject(params);*/
            Gson gson=new Gson();
            String json = gson.toJson(date);

            OkGo.<String>post(Urls.Ip_url + Urls.Identity.AddIdentity)
                    .tag(this)
                    .upJson(json)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {


                            try {
                                JSONObject object = new JSONObject(s);
                                int isSuccess = object.getInt("error_code");
                                if (isSuccess==0) {
                                    identityBean = identity1;
                                    EventBus.getDefault().post(new InformationEvent("informationStatus"));
                                    String data = object.getString("data");
                                    JSONObject object1 = new JSONObject(data);
                                    String msg = object1.getString("msg");
                                    ToastUtils.showToast(getActivity(), msg);
                                } else {
                                    String msg = object.getString("error_message");
                                    ToastUtils.showToast(getActivity(), msg);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } else {

        }

    }

    /**
     * 内容有无修改
     *
     * @return
     */
    private boolean changeTest() {
        boolean flag;

        final Identity.DataBean.IdentityBean identity1 = new Identity.DataBean.IdentityBean();
        String userName = etName.getText().toString();
        String IdCard = etIDCard.getText().toString();
        String address = etAddress.getText().toString();
        String age = etAge1.getText().toString();
        String city = etCity.getText().toString();
        String between1 = etBetween1.getText().toString();
        String between2 = etBetween2.getText().toString();
        String contact1 = etContact.getText().toString();
        String contact2 = etContact1.getText().toString();
        String contactName1 = etContactName.getText().toString();
        String contactName2 = etContactName2.getText().toString();


        Identity.DataBean.IdentityBean.ContactBean identity2 = new Identity.DataBean.IdentityBean.ContactBean();
        identity2.setUserphone(contact1);
        identity2.setContact(contactName1);
        identity2.setRelation("");

        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(between1)) {
                String s = String.valueOf(i);
                identity2.setRelation(s);
            }
        }

        Identity.DataBean.IdentityBean.ContactBean identity3 = new Identity.DataBean.IdentityBean.ContactBean();
        identity3.setUserphone(contact2);
        identity3.setContact(contactName2);
        identity3.setRelation("");

        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(between2)) {
                String s = String.valueOf(i);
                identity3.setRelation(s);
            }
        }

        if (identityBean.getMarriage().equals(identity1.getMarriage())
                && identityBean.getName().equals(userName)
                && identityBean.getIdcard().equals(IdCard)
                && identityBean.getIdaddress().equals(address)
                && identityBean.getAge().equals(age)
                && identityBean.getSex().equals(identity1.getSex())
                && identityBean.getCity().equals(city)
                && identityBean.getContact().get(0).getRelation().equals(identity2.getRelation())
                && identityBean.getContact().get(0).getUserphone().equals(contact1)
                && identityBean.getContact().get(0).getContact().equals(contactName1)
                && identityBean.getContact().get(1).getRelation().equals(identity3.getRelation())
                && identityBean.getContact().get(1).getUserphone().equals(contact2)
                && identityBean.getContact().get(1).getContact().equals(contactName2)) {
            return true;
        }
        return false;
    }

    private void getLocationPermission() {
        AndPermission.with(getActivity())
                .requestCode(200)
                .permission(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).rationale(new RationaleListener() {
            @Override
            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                AndPermission.rationaleDialog(getActivity(), rationale)
                        .show();

            }
        }).callback(new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                if (requestCode == 200) {
                    LogUtils.i("location", "权限");
                    initLocation();
                    mLocationClient.start();


                }
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);

            }
        })
                .start();


    }

    /**
     * 城市定位
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        int span = 1000;
        option.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIgnoreKillProcess(true);
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }

    private void getPermission(final int i) {
        AndPermission.with(getActivity())
                .requestCode(100)
                .permission(Manifest.permission.READ_CONTACTS)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(getActivity(), rationale)
                                .show();
                    }
                })
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        if (requestCode == 100) {
                            Uri uri = Uri.parse("content://contacts/people");
                            Intent intent = new Intent(Intent.ACTION_PICK, uri);
                            startActivityForResult(intent, i);
                        }
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    }
                })
                .start();


    }


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            LogUtils.i("city", bdLocation.getCity());
            final String city = bdLocation.getCity();
            if (city != null) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etCity.setText(city);
                    }
                });
            } else {
                ToastUtils.showToast(getActivity(), bdLocation.getLocType() + "");
                LogUtils.i("getLocType", bdLocation.getLocType() + "");

            }


        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        return;
                    } else {
                        //处理返回的data,获取选择的联系人信息
                        Uri uri = data.getData();
                        String[] contacts = getPhoneContacts(uri);
                        if (contacts != null && contacts.length > 1) {
                            etContact1.setText(contacts[1]);
                        } else {
                            ToastUtils.showToast(getActivity(), "获取号码失败，请手动添加");
                        }
                        if (contacts != null) {
                            etContactName.setText(contacts[0]);
                        } else {
                            ToastUtils.showToast(getActivity(), "获取联系人失败，请手动添加");
                        }
                    }
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        return;
                    } else {
                        //处理返回的data,获取选择的联系人信息
                        Uri uri = data.getData();
                        String[] contacts = getPhoneContacts(uri);
                        if (contacts != null && contacts.length > 1) {
                            etContact.setText(contacts[1]);
                        } else {
                            ToastUtils.showToast(getActivity(), "获取号码失败，请手动添加");
                        }
                        if (contacts != null) {
                            etContactName2.setText(contacts[0]);
                        } else {
                            ToastUtils.showToast(getActivity(), "获取联系人失败，请手动添加");

                        }
                    }
                }
                break;
            case REQUEST_CODE_PICK_CITY:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                        etCity.setText(city);
                    }
                }
                break;
        /*    case AppApplication.TAKE_PHOTO_CUSTOM:
                mFile = new File(data.getStringExtra("file"));
                Flowable.just(mFile)
                        //将File解码为Bitmap
                        .map(file -> BitmapUtils.compressToResolution(file, 1920 * 1080))
                        //裁剪Bitmap
                        .map(BitmapUtils::crop)
                        //将Bitmap写入文件
                        .map(bitmap -> BitmapUtils.writeBitmapToFile(bitmap, "mFile"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(file -> {
                            mFile = file;
                            Uri uri = Uri.parse("file://" + mFile.toString());
                            *//*ImagePipeline imagePipeline = Fresco.getImagePipeline();
                            //清除该Uri的Fresco缓存. 若不清除，对于相同文件名的图片，Fresco会直接使用缓存而使得Drawee得不到更新.
                            imagePipeline.evictFromMemoryCache(uri);
                            imagePipeline.evictFromDiskCache(uri);
                            FrescoUtils.load("file://" + mFile.toString()).resize(240, 164).into(mImageView);
                            mBtnTakePicture.setText("重新拍照");
                            mHasSelectedOnce = true;*//*
                        });
                break;*/

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private String[] getPhoneContacts(Uri uri) {
        String[] contact = new String[2];
        //得到ContentResolver对象
        ContentResolver cr = getActivity().getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if (phone.getCount() > 0) {
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        } else

        {
            return null;
        }
        return contact;
    }

 /*   @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            //相当于Fragment的onPause
            CreateDialogDialogFragment fragment = CreateDialogDialogFragment.newInstance();
            fragment.show(getChildFragmentManager(), "dialog1");
                   *//* if(!changeTest()){

                    }*//*
            LogUtils.i("1","不可见");
        } else {
            // 相当于Fragment的onResume
            System.out.println("界面可见");
        }
    }*/

    @Override
    public void onDestroy() {
        LogUtils.i("1-onDestroy()", "11111");
        super.onDestroy();

        EventBus.getDefault().unregister(this);

    }








}