package cn.com.stableloan.ui.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

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
import cn.com.stableloan.model.Identity;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.RegexUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
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
    Context context;
    @Bind(R.id.et_name)
    FormEditText etName;
    @Bind(R.id.user_phone)
    TextView userPhone;
    @Bind(R.id.et_IDCard)
    FormEditText etIDCard;
    @Bind(R.id.et_Sex)
    FormEditText etSex;
    @Bind(R.id.et_Address)
    FormEditText etAddress;
    @Bind(R.id.et_Contact_name)
    FormEditText etContactName;
    @Bind(R.id.et_Contact_name2)
    FormEditText etContactName2;
    @Bind(R.id.et_Age1)
    FormEditText etAge;
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


    private  UserBean user;
    private String[] list1;

    private String[] list;

    private Identity.IdentityBean identityBean;

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
        EventBus.getDefault().register(this);
        getDate();
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initSpinner();
        setListenter();
        return view;
    }
    private static final int REQUEST_CODE_PICK_CITY = 0;


    private void setListenter() {
        etCity.setFocusableInTouchMode(false);
        etCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
            }
        });

        etSex.setKeyListener(null);
        etAge.setKeyListener(null);
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
                        etAge.setText(String.valueOf(age));
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
        OkGo.<String>post(Urls.NEW_URL + Urls.Identity.GetIdentity)
                .tag(getActivity())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String isSuccess = json.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                String status = json.getString("status");
                                if ("1".equals(status)) {
                                    String string = json.getString("identity");
                                    Gson gson = new Gson();
                                    identityBean = gson.fromJson(string, Identity.IdentityBean.class);
                                    etName.setText(identityBean.getName());
                                    etIDCard.setText(identityBean.getIdcard());
                                    String sex = identityBean.getSex();
                                    if ("0".equals(sex)) {
                                        etSex.setText("女");
                                    } else if ("1".equals(sex)) {
                                        etSex.setText("男");
                                    }
                                    etAge.setText(identityBean.getAge());

                                    etAddress.setText(identityBean.getIdaddress());

                                    String marriage = identityBean.getMarriage();

                                    if (marriage.equals("0")) {
                                        etMarriage.setText("未婚");
                                    } else if (marriage.equals("1")) {
                                        etMarriage.setText("已婚");
                                    }
                                    etCity.setText(identityBean.getCity());
                                    Identity.IdentityBean.ContactBean bean = identityBean.getContact().get(0);
                                    etContact.setText(bean.getUserphone());

                                    etContactName.setText(bean.getContact());

                                    String bet = bean.getRelation();
                                    int i2 = Integer.parseInt(bet);
                                    etBetween1.setText(list[i2]);

                                    Identity.IdentityBean.ContactBean bean1 = identityBean.getContact().get(1);

                                    etContact1.setText(bean1.getUserphone());
                                    etContactName2.setText(bean1.getContact());

                                    String bet2 = bean1.getRelation();
                                    int i1 = Integer.parseInt(bet2);
                                    etBetween2.setText(list[i1]);


                                } else {
                                    Intent intent = new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "UserInformation");
                                    startActivity(intent);
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
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

    @OnClick({R.id.getCity, R.id.getContact1, R.id.getContact2, R.id.save})
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
        }
    }

    private void saveDate() {

        final Identity.IdentityBean identity1=new Identity.IdentityBean();


        String userName = etName.getText().toString();
        String IdCard = etIDCard.getText().toString();
        String address = etAddress.getText().toString();
        String age = etAge.getText().toString();
        String city = etCity.getText().toString();
        String between1 = etBetween1.getText().toString();
        String between2 = etBetween2.getText().toString();
        String contact1 = etContact.getText().toString();
        String contact2 = etContact1.getText().toString();
        String contactName1 = etContactName.getText().toString();
        String contactName2 = etContactName2.getText().toString();

        Identity.IdentityBean.ContactBean identity2 = new Identity.IdentityBean.ContactBean();
        identity2.setUserphone(contact1);
        identity2.setContact(contactName1);
        identity2.setRelation("");

        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(between1)) {
                String s = String.valueOf(i);
                identity2.setRelation(s);
            }
        }

        Identity.IdentityBean.ContactBean identity3 = new Identity.IdentityBean.ContactBean();
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
        if (s1.equals("女")) {
            identity1.setSex("0");
        } else if (s1.equals("男")) {
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

        Identity.IdentityBean bean=new Identity.IdentityBean();
        bean.setSex(identity1.getSex());
        bean.setMarriage(identity1.getMarriage());
        bean.setAge(age);
        bean.setCity(city);
        bean.setIdaddress(address);
        bean.setIdcard(IdCard);
        bean.setName(userName);

        if(identityBean.getMarriage().equals(identity1.getMarriage())
                &&identityBean.getName().equals(userName)
                &&identityBean.getIdcard().equals(IdCard)
                &&identityBean.getIdaddress().equals(address)
                &&identityBean.getAge().equals(age)
                &&identityBean.getSex().equals(identity1.getSex())
                &&identityBean.getCity().equals(city)
                &&identityBean.getContact().get(0).getRelation().equals(identity2.getRelation())
                &&identityBean.getContact().get(0).getUserphone().equals(contact1)
                &&identityBean.getContact().get(0).getContact().equals(contactName1)
                &&identityBean.getContact().get(1).getRelation().equals(identity3.getRelation())
                &&identityBean.getContact().get(1).getUserphone().equals(contact2)
                &&identityBean.getContact().get(1).getContact().equals(contactName2)) {
            ToastUtils.showToast(getActivity(),"无修改内容");
        }else {
            FormEditText[] allFields = {etName, etIDCard,  etContact, etContact1};
            boolean allValid = true;
            for (FormEditText field : allFields) {
                allValid = field.testValidity() && allValid;
            }
            if (allValid) {

                final Identity identity = new Identity();
                if (!etContact.getText().toString().isEmpty() && !etMarriage.getText().toString().isEmpty() && !etContactName.getText().toString().isEmpty()
                        && !etCity.getText().toString().isEmpty() && !etAddress.getText().toString().isEmpty() && !etAge.getText().toString().isEmpty()
                        && !etContact1.getText().toString().isEmpty() && !etContactName2.getText().toString().isEmpty() && !etIDCard.getText().toString().isEmpty()
                        && !etSex.getText().toString().isEmpty() && !etBetween1.getText().toString().isEmpty() && !etBetween2.getText().toString().isEmpty()
                        && !userPhone.getText().toString().isEmpty()) {
                    identity1.setIstatus("1");
                } else {
                    identity1.setIstatus("0");
                }
                identity1.setName(etName.getText().toString());
                identity1.setAge(etAge.getText().toString());
                identity1.setCity(etCity.getText().toString());
                identity1.setIdcard(etIDCard.getText().toString());
                identity1.setIdaddress(etAddress.getText().toString());
                identity1.setUserphone(userPhone.getText().toString());

                ArrayList<Identity.IdentityBean.ContactBean> list = new ArrayList<>();
                list.add(identity2);
                list.add(identity3);
                identity1.setContact(list);
                identity.setIdentity(identity1);


                String token = (String) SPUtils.get(getActivity(), "token", "1");
                identity.setToken(token);
                identity.setUserphone(user.getUserphone());
                Gson gson = new Gson();
                String json = gson.toJson(identity);

                OkGo.<String>post(Urls.NEW_URL + Urls.Identity.AddIdentity)
                        .tag(this)
                        .upJson(json)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject object = new JSONObject(s);
                                    String isSuccess = object.getString("isSuccess");
                                    if ("1".equals(isSuccess)) {
                                        identityBean=identity1;

                                        String msg = object.getString("msg");
                                        ToastUtils.showToast(getActivity(), msg);
                                    } else {
                                        String msg = object.getString("msg");
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
                        }
                        if (contacts != null) {
                            etContactName.setText(contacts[0]);
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
                        }
                        if (contacts != null) {
                            etContactName2.setText(contacts[0]);
                        }
                    }
                }
                break;
            case REQUEST_CODE_PICK_CITY:
                if(resultCode == RESULT_OK){
                    if (data != null){
                        String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                        etCity.setText(city);
                    }
                }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

}