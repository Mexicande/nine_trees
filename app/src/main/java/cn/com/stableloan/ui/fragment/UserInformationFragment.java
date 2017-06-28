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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.andreabaccega.widget.FormEditText;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionActivity;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.security.Permission;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.BetterSpinner;

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

    public UserInformationFragment() {
        // Required empty public constructor
    }
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_information, container, false);
        ButterKnife.bind(this, view);

        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener( myListener );
        initSpinner();


        return view;
    }

    private void initSpinner() {

        String[] list1 = getResources().getStringArray(R.array.spingarr);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list1);
        etMarriage.setAdapter(adapter1);
        String[] list = getResources().getStringArray(R.array.between);
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

    @OnClick({R.id.getCity, R.id.getContact1, R.id.getContact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getCity:
                LogUtils.i("location","button");

                getLocationPermission();
                break;
            case R.id.getContact1:
                getPermission();
                break;
            case R.id.getContact:
                break;
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
                ) .rationale(new RationaleListener() {
            @Override
            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                AndPermission.rationaleDialog(getActivity(), rationale)
                        .show();
            }
        })   .callback(new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                if(requestCode==200){
                    LogUtils.i("location","权限");
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

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        int span=1000;
        option.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIgnoreKillProcess(true);
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }


    private void getPermission() {
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
                        if(requestCode==100){
                            Uri uri = Uri.parse("content://contacts/people");
                            Intent intent = new Intent(Intent.ACTION_PICK, uri);
                            startActivityForResult(intent, 1);
                        }
                    }
                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    }
                })
                .start();


    }
    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            LogUtils.i("city",bdLocation.getCity());
            final String city = bdLocation.getCity();
            if(city!=null){

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etCity.setText(city);
                    }
                });
            }else {
                ToastUtils.showToast(getActivity(),bdLocation.getLocType()+"");
                LogUtils.i("getLocType",bdLocation.getLocType()+"");

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
                    }else {
                        //处理返回的data,获取选择的联系人信息
                        Uri uri = data.getData();
                        String[] contacts = getPhoneContacts(uri);
                        LogUtils.i("contacts",contacts[0]+"----"+contacts[1]);
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
        if (cursor!=null) {
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


}