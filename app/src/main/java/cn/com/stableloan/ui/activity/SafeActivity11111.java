package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.SaveBean;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.utils.constant.Constant;
import okhttp3.Call;
import okhttp3.Response;

public class SafeActivity11111 extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    private ACache aCache;
    private  SaveBean saveBean;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SafeActivity11111.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_safe);
        ButterKnife.bind(this);
        aCache = ACache.get(this);

        titleName.setText("账号安全");
    }

    @OnClick({R.id.layout_go, R.id.safe, R.id.change_password, R.id.pattern})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_go:
                finish();
                break;
            case R.id.safe:

                final TinyDB tinyDB = new TinyDB(this);
                UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
                String userphone = user.getUserphone();

                String gesturePassword = aCache.getAsString(userphone);
                String lock = aCache.getAsString("lock");

                if(gesturePassword == null || "".equals(gesturePassword)||"off".equals(lock)) {
                    Intent intent=new Intent(this,Verify_PasswordActivity.class).putExtra("from","safe");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, GestureLoginActivity.class).putExtra("from","SettingSafe");
                    startActivity(intent);
                }
                //getDate();
                break;
            case R.id.change_password:
                UpdatePassWordActivity.launch(this);
                break;
            case R.id.pattern:
                Intent intent1=new Intent(this,Verify_PasswordActivity.class).putExtra("from","unLock");
                startActivity(intent1);
                break;
        }
    }
    private void getDate() {

        String token = (String) SPUtils.get(this, "token", "1");

        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_URL + Urls.STATUS.Getsetting)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            String isSuccess = object.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                Gson gson = new Gson();
                                saveBean = gson.fromJson(s, SaveBean.class);
                                String way1 = saveBean.getWay();

                                String gesturePassword = aCache.getAsString(Constant.GESTURE_PASSWORD);

                                if(way1!=null){
                                    if("0".equals(way1)){

                                        Intent intent=new Intent(SafeActivity11111.this,Verify_PasswordActivity.class).putExtra("from","safe");
                                        startActivity(intent);

                                    }else {
                                        if(gesturePassword == null ){
                                            Intent intent = new Intent(SafeActivity11111.this, GestureLoginActivity.class).putExtra("from","SettingSafe");
                                            startActivity(intent);
                                        }else {

                                            Intent intent=new Intent(SafeActivity11111.this,Verify_PasswordActivity.class).putExtra("from","safe");
                                            startActivity(intent);
                                        }

                                    }
                                }

                            }else {
                                String msg = object.getString("msg");
                                ToastUtils.showToast(SafeActivity11111.this,msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });


    }
}
