package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.fragment.UserFragment;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

public class UpdataProfessionActivity extends BaseActivity {



    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_work)
    ImageView ivWork;
    @Bind(R.id.tick)
    ImageView tick;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.iv_free)
    ImageView ivFree;
    @Bind(R.id.tick_free)
    ImageView tickFree;
    @Bind(R.id.iv_student)
    ImageView ivStudent;
    @Bind(R.id.tick_student)
    ImageView tickStudent;
    @Bind(R.id.iv_company)
    ImageView ivCompany;
    @Bind(R.id.tick_company)
    ImageView tickCompany;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    private static int Flge = 0;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, UpdataProfessionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_profession);
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        titleName.setText("身份修改");
        TinyDB tinyDB=new TinyDB(this);
        UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
        if(user!=null){
            String identity =  user.getIdentity();
            if(identity!=null){
                ToastUtils.showToast(this,identity+"");
                int i = Integer.parseInt(identity);
                switch (i){
                    case 1:
                        ivWork.setColorFilter(getResources().getColor(R.color.mask));
                        tick.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        ivFree.setColorFilter(getResources().getColor(R.color.mask));
                        tickFree.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        ivStudent.setColorFilter(getResources().getColor(R.color.mask));
                        tickStudent.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        ivCompany.setColorFilter(getResources().getColor(R.color.mask));
                        tickCompany.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }


    }


    @OnClick({R.id.iv_work, R.id.iv_free, R.id.iv_student, R.id.iv_company, R.id.iv_back,R.id.bt_Save})
    public void onViewClicked(View view) {
        SettingProfession();
        SettingTick();
        switch (view.getId()) {
            case R.id.iv_work:
                ivWork.setColorFilter(getResources().getColor(R.color.mask));
                tick.setVisibility(View.VISIBLE);
                Flge=1;
                break;
            case R.id.iv_free:
                ivFree.setColorFilter(getResources().getColor(R.color.mask));
                tickFree.setVisibility(View.VISIBLE);
                Flge=2;
                break;
            case R.id.iv_student:
                ivStudent.setColorFilter(getResources().getColor(R.color.mask));
                tickStudent.setVisibility(View.VISIBLE);
                Flge=3;
                break;
            case R.id.iv_company:
                ivCompany.setColorFilter(getResources().getColor(R.color.mask));
                tickCompany.setVisibility(View.VISIBLE);
                Flge=4;
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_Save:

                updateIdentity(Flge+"");
                LogUtils.i("FLAG",Flge);
               /* UserBean user = (UserBean) SPUtils.get(this, "user",UserBean.class);
                user.setIdentity(""+Flge);
                setResult(-1,new Intent().putExtra("HeadPhoto",Flge));
                finish();*/
                break;
        }

    }

    private void updateIdentity(final String identity ) {

        String token = (String) SPUtils.get(this, "token", "1");
        if(token!=null){
            HashMap<String, String> params = new HashMap<>();
            params.put("identity",identity);
            params.put("token",token);
            params.put("status","2");
            JSONObject jsonObject = new JSONObject(params);
            OkGo.post(Urls.puk_URL+Urls.update.UPDATE_PROFRSSION)
                    .tag(this)
                    .upJson(jsonObject.toString())
                    .execute( new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            LogUtils.i("昵称修改",s);
                            try {
                                JSONObject object=new JSONObject(s);
                                boolean isSuccess = object.getBoolean("isSuccess");
                                if(isSuccess){
                                    setResult(1000,new Intent().putExtra("HeadPhoto",identity));
                                    finish();
                                }else {
                                    String string = object.getString("msg");
                                    ToastUtils.showToast(UpdataProfessionActivity.this,string);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }else {
            ToastUtils.showToast(UpdataProfessionActivity.this,"系统异常,请稍后再试");

        }

    }

    /**
     * 选择对勾
     */
    private void SettingTick() {
        tick.setVisibility(View.GONE);
        tickCompany.setVisibility(View.GONE);
        tickFree.setVisibility(View.GONE);
        tickStudent.setVisibility(View.GONE);
    }

    /**
     * 图片选择添加蒙版
     */
    private void SettingProfession() {
        ivWork.setColorFilter(null);
        ivFree.setColorFilter(null);
        ivStudent.setColorFilter(null);
        ivCompany.setColorFilter(null);
    }

}
