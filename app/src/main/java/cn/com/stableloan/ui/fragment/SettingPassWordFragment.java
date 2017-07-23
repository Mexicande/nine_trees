package cn.com.stableloan.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.DesBean;
import cn.com.stableloan.utils.EncryptUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.aes.Des4;
import cn.com.stableloan.utils.editext.PowerfulEditText;
import cn.com.stableloan.utils.ras.RSA;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingPassWordFragment extends Fragment {


    @Bind(R.id.et_SettingPassWord)
    PowerfulEditText etSettingPassWord;

    private String userPhone;

    public SettingPassWordFragment() {
        // Required empty public constructor
    }

    public static SettingPassWordFragment newInstance(String isprivate) {
        SettingPassWordFragment fragment = new SettingPassWordFragment();
        Bundle args = new Bundle();
        args.putString("phone", isprivate);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userPhone = getArguments().getString("phone");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_setting_pass_word, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.layout, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout:
                break;
            case R.id.bt_login:
                goMain();
                break;
        }
    }

    private void goMain() {
        HashMap<String, String> params = new HashMap<>();

        String md5ToString = EncryptUtils.encryptMD5ToString("18500634223");
        params.put("userphone", "18500634223");
        params.put("password", md5ToString);


        JSONObject object = new JSONObject(params);

        String Deskey = null;
        String sign = null;
        String deskey = null;
        try {
            int random = new Random().nextInt(10000000) + 89999999;
            LogUtils.i("random", random);
            Deskey = Des4.encode(object.toString(), String.valueOf(random));
            deskey = RSA.encrypt(String.valueOf(random), Urls.PUCLIC_KEY);

            sign = RSA.sign(deskey, Urls.PRIVATE_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }

        DesBean bean = new DesBean();
        bean.setData(Deskey);
        bean.setDeskey(deskey);
        final Gson gson = new Gson();

        String json = gson.toJson(bean);

        OkGo.<String>post("http://47.94.175.112:8081/v1/set/password")
                .tag(this)
                .headers("sign", sign)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
/*
                                hud.dismiss();
                                UserInfromBean user = new UserInfromBean();
                                int error_code = user.getError_code();
                                if (0 == error_code) {

                                    SPUtils.put(getActivity(), "token", user.getData().getToken());
                                    SPUtils.put(getActivity(), "login", true);
                                    Gson gson1 = new Gson();
                                    UserBean userBean = gson1.fromJson(user.getData().toString(), UserBean.class);
                                    EventBus.getDefault().post(new MessageEvent(userBean.getNickname(), userBean.getUserphone()));
                                    TinyDB tinyDB = new TinyDB(getActivity());
                                    tinyDB.putObject("user", userBean);
                                    String from = getActivity().getIntent().getStringExtra("from");
                                    if (from != null) {
                                        Log.i("from------", "from");
                                        if (from.equals("user")) {
                                            getActivity().setResult(Flag_User, new Intent().putExtra("user", userBean));
                                            getActivity().finish();
                                        } else if (from.equals("123")) {
                                            EventBus.getDefault().post(new InformationEvent("user2"));
                                            getActivity().setResult(LOTTERY_CODE, new Intent().putExtra("Loffery", "123"));
                                            getActivity().finish();
                                        } else if (from.equals("user1")) {
                                            getActivity().setResult(4000, new Intent().putExtra("user", userBean));
                                            getActivity().finish();
                                        } else if (from.equals("user2")) {
                                            getActivity().setResult(4000, new Intent().putExtra("user", userBean));
                                            getActivity().finish();
                                        }
                                    } else {
                                        Log.i("from------", "null");
                                        getActivity().finish();
                                    }


                                } else {
                                    ToastUtils.showToast(getActivity(), user.getError_message());
                                }*/
                        LogUtils.i("Login", s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                              /*  getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hud.dismiss();
                                    }
                                });*/
                    }
                });
    }
}
