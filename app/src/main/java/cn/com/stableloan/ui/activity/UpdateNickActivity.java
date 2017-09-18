package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.MessageEvent;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.RegexUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;
import cxy.com.validate.IValidateResult;
import cxy.com.validate.Validate;
import cxy.com.validate.ValidateAnimation;
import cxy.com.validate.annotation.Index;
import cxy.com.validate.annotation.NotNull;
import cxy.com.validate.annotation.Password1;
import cxy.com.validate.annotation.RE;
import okhttp3.Call;
import okhttp3.Response;

public class UpdateNickActivity extends BaseActivity implements IValidateResult {

    private  static final int Flag=2000;

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bt_commit)
    Button btCommit;

    @Index(1)
    @NotNull(msg = "昵称不为能空！")
    @RE(re = RE.number_letter_nick, msg = "昵称格式不正确")
    @Bind(R.id.et_nick)
    EditText etNick;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, UpdateNickActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        Validate.reg(this);

        initView();
    }

    private void initView() {
        titleName.setText("昵称修改");
        ivBack.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.iv_back, R.id.bt_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_commit:
                Validate.check(UpdateNickActivity.this, UpdateNickActivity.this);

               /* String nick = etNick.getText().toString();
                if(!nick.isEmpty()&&RegexUtils.isUsername(etNick.getText().toString())){
                    RegexUtils.isUsername(nick);
                    setResult(Flag, new Intent().putExtra("nick",nick ));

                    TinyDB tinyDB=new TinyDB(this);
                    UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
                    user.setNickname(nick);
                    ToastUtils.showToast(this,"保存成功");
                    finish();
                }else {
                    ToastUtils.showToast(this,"昵称不符合");
                }*/
                break;
            default:
                break;
        }
    }

    @Override
    public void onValidateSuccess() {

        sendUpdateNick();
    }


    @Override
    public void onValidateError(String msg, EditText editText) {
        if (editText != null)
            editText.setFocusable(true);
        ToastUtils.showToast(this,msg);
    }

    @Override
    public Animation onValidateErrorAnno() {
        return ValidateAnimation.horizontalTranslate();
    }
    /**
     *
     * 昵称修改
     */
    private  KProgressHUD hud;
    private void sendUpdateNick() {

        String token = (String) SPUtils.get(this, "token", "1");
        final String nick = etNick.getText().toString();
        if(token!=null){
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("加载中.....")
                    .setCancellable(true)
                    .show();
            HashMap<String, String> params = new HashMap<>();
            params.put("nickname",nick);
            params.put("token",token);
            params.put("status","1");
            JSONObject jsonObject = new JSONObject(params);
            OkGo.post(Urls.puk_URL+Urls.update.UPDATE_NICK)
                    .tag(this)
                    .upJson(jsonObject.toString())
                    .execute( new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            if(s!=null){
                                try {
                                    JSONObject object=new JSONObject(s);
                                    String success = object.getString("isSuccess");
                                    if(success.equals("1")){
                                        hud.dismiss();
                                        ToastUtils.showToast(UpdateNickActivity.this,"修改成功");
                                        EventBus.getDefault().post(new MessageEvent(nick,null));
                                        finish();
                                    }else {
                                        hud.dismiss();
                                        String string = object.getString("msg");
                                        ToastUtils.showToast(UpdateNickActivity.this,string);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                hud.dismiss();

                            }

                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            hud.dismiss();

                            ToastUtils.showToast(UpdateNickActivity.this,"网络异常，请检测网络");

                        }
                    });
        }else {
            ToastUtils.showToast(UpdateNickActivity.this,"系统异常,请稍后再试");

        }
    }

}