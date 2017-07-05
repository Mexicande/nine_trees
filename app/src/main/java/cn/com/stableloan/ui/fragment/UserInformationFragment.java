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
import okhttp3.Call;
import okhttp3.Response;

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
    FormEditText etCity;
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
    @Bind(R.id.et_Age)
    FormEditText etAge;
    @Bind(R.id.et_Address)
    FormEditText etAddress;
    @Bind(R.id.et_Contact_name)
    FormEditText etContactName;
    @Bind(R.id.et_Contact_name2)
    FormEditText etContactName2;

    private String[] list1;

    private String[] list;

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
        ToastUtils.showToast(getActivity(),"-------");
        EventBus.getDefault().register(this);
        getDate();
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initSpinner();

        setListenter();
        return view;
    }


    private void setListenter() {

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
              if(idCard.length()==18){
                  boolean idCard18 = RegexUtils.isIDCard18(idCard);
                  if(idCard18){
                      char c = idCard.charAt(16);
                      int i = Integer.parseInt(String.valueOf(c));
                      if(i%2==0){
                          etSex.setText("女");
                      }else {
                          etSex.setText("男");
                      }
                  }
                  int age = IdNOToAge(idCard);
                  etAge.setText(age);
              }
            }
        });
    }

    public static int IdNOToAge(String IdNO){
        int leh = IdNO.length();
        String dates="";
        if (leh == 18) {
            int se = Integer.valueOf(IdNO.substring(leh - 1)) % 2;
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year=df.format(new Date());
            int u=Integer.parseInt(year)-Integer.parseInt(dates);
            return u;
        }else{
            dates = IdNO.substring(6, 8);
            return Integer.parseInt(dates);
        }

    }

    @Subscribe
    public void onMessageEvent(InformationEvent event){
        String message = event.message;
        if("informationStatus".equals(message)){
            getDate();
        }
    }
    private void getDate() {
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
                                    Identity.IdentityBean identity = gson.fromJson(string, Identity.IdentityBean.class);
                                    etName.setText(identity.getName());
                                    etIDCard.setText(identity.getIdcard());

                                    String sex = identity.getSex();
                                    if("0".equals(sex)){
                                        etSex.setText("女");
                                    }else if("1".equals(sex)){
                                        etSex.setText("男");
                                    }
                                    etAge.setText(identity.getAge());
                                    etAddress.setText(identity.getIdaddress());

                                    String marriage = identity.getMarriage();

                                    if(marriage.equals("0")){
                                        etMarriage.setText("未婚");
                                    }else if(marriage.equals("1")){
                                        etMarriage.setText("已婚");

                                    }
                                    etCity.setText(identity.getCity());

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

    @OnClick({R.id.getCity, R.id.getContact1, R.id.getContact2,R.id.save})
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

        FormEditText[] allFields	= { etAge, etIDCard, etSex, etAddress, etCity ,etContact,etContact1};


        boolean allValid = true;
        for (FormEditText field: allFields) {
            allValid = field.testValidity() && allValid;
        }


        if (allValid) {

        } else {

        }




        Identity identity=new Identity();

        Identity.IdentityBean identity1 = new Identity.IdentityBean();

        if(!etContact.getText().toString().isEmpty()&&!etMarriage.getText().toString().isEmpty()&&!etContactName.getText().toString().isEmpty()
                &&!etCity.getText().toString().isEmpty()&&!etAddress.getText().toString().isEmpty()&&!etAge.getText().toString().isEmpty()
                &&!etContact1.getText().toString().isEmpty()&&!etContactName2.getText().toString().isEmpty()&&!etIDCard.getText().toString().isEmpty()
                &&!etSex.getText().toString().isEmpty()&&!etBetween1.getText().toString().isEmpty()&&!etBetween2.getText().toString().isEmpty()){
            identity1.setIstatus("1");
        }else {
            identity1.setIstatus("0");
        }

        identity1.setName(etName.getText().toString());
        identity1.setAge(etAge.getText().toString());
        identity1.setCity(etCity.getText().toString());
        identity1.setIdcard(etIDCard.getText().toString());
        identity1.setIdaddress(etAddress.getText().toString());
        identity1.setUserphone(userPhone.getText().toString());


        String s1 = etSex.getText().toString();
        if(s1.equals("女")){
            identity1.setSex("0");
        }else if(s1.equals("男")){
            identity1.setSex("1");
        }else {
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
        Identity.IdentityBean.ContactBean identity2=new Identity.IdentityBean.ContactBean();
        identity2.setUserphone(etContact.getText().toString());
        identity2.setContact(etContactName.getText().toString());

        identity2.setRelation("");

        String bet = etBetween1.getText().toString();
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(bet)) {
                String s = String.valueOf(i);
                identity2.setRelation(s);
            }
        }


        Identity.IdentityBean.ContactBean identity3=new Identity.IdentityBean.ContactBean();
        identity3.setUserphone(etContact1.getText().toString());
        identity3.setContact(etContactName2.getText().toString());



        identity3.setRelation("");
        String bet1 = etBetween1.getText().toString();
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(bet1)) {
                String s = String.valueOf(i);
                identity3.setRelation(s);
            }
        }

        ArrayList<Identity.IdentityBean.ContactBean>list=new ArrayList<>();
        list.add(identity2);
        list.add(identity3);

        identity1.setContact(list);
        identity.setIdentity(identity1);
        TinyDB tinyDB = new TinyDB(getActivity());
        UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
        // identity.getIdentity().setMarriage(etMarriage.getText().toString());
        String token = (String) SPUtils.get(getActivity(), "token", "1");
        identity.setToken(token);
        identity.setUserphone(user.getUserphone());
        Gson gson=new Gson();
        String json = gson.toJson(identity);
       /* TinyDB tinyDB = new TinyDB(getActivity());
        UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("userphone", user.getUserphone());
        JSONObject jsonObject = new JSONObject(parms);*/
            OkGo.<String>post(Urls.NEW_URL+Urls.Identity.AddIdentity)
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
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        return;
                    } else {
                        //处理返回的data,获取选择的联系人信息
                        Uri uri = data.getData();
                        String[] contacts = getPhoneContacts(uri);
                        etContact.setText(contacts[1]);
                        etContactName.setText(contacts[0]);
                        LogUtils.i("contacts", contacts[0] + "----" + contacts[1]);
                    }
                }
                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        return;
                    } else {
                        //处理返回的data,获取选择的联系人信息
                        Uri uri = data.getData();
                        String[] contacts = getPhoneContacts(uri);
                        if (contacts[1] != null) {
                            etContact1.setText(contacts[1]);
                        }
                        etContactName2.setText(contacts[0]);
                        LogUtils.i("contacts", contacts[0] + "----" + contacts[1]);
                    }
                }
                break;
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