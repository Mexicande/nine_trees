package cn.com.stableloan.ui.activity.settingdate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.model.MsgEvent;
import cn.com.stableloan.model.event.ProfessionalSelectEvent;
import cn.com.stableloan.ui.activity.CreateGestureActivity;
import cn.com.stableloan.ui.activity.GestureLoginActivity;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.ui.activity.integarl.RuleDescActivity;
import cn.com.stableloan.ui.activity.integarl.SafeSettingActivity;
import cn.com.stableloan.ui.activity.integarl.WithdrawalCashActivity;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.view.dialog.CashDialog;
import cn.com.stableloan.view.supertextview.SuperTextView;

public class SwitchPassWordActivity extends AppCompatActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.st_GestureOpen)
    SuperTextView stGestureOpen;
    @Bind(R.id.st_CreateGesture)
    SuperTextView stCreateGesture;
    private ACache aCache;
    private String userPhone="";

    private CashDialog cashDialog;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SwitchPassWordActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_pass_word);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        aCache = ACache.get(this);
        initToolbar();
        setListener();
    }

    private void setListener() {

        String gesturePassword = aCache.getAsString(userPhone);
        String lock = aCache.getAsString("lock");

        if(!"".equals(gesturePassword)&&!"off".equals(lock)){
            stGestureOpen.setSwitchIsChecked(true);
            stCreateGesture.setVisibility(View.VISIBLE);
        }else {
            stGestureOpen.setSwitchIsChecked(false);
        }
        stCreateGesture.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                startActivity(new Intent(SwitchPassWordActivity.this,Verify_PasswordActivity.class).putExtra("from","CreateGesture"));
                //CreateGestureActivity.launch(SwitchPassWordActivity.this);

            }
        });

        stGestureOpen.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                stGestureOpen.setSwitchIsChecked(!isChecked);
                    if(isChecked){
                        startActivity(new Intent(SwitchPassWordActivity.this,Verify_PasswordActivity.class).putExtra("from","CreateGesture"));
                        //CreateGestureActivity.launch(SwitchPassWordActivity.this);
                    }else {
                        RuleDialog();
                    }
            }
        });

    }

    private void initToolbar() {
        titleName.setText("手势");

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    private void RuleDialog() {
        cashDialog = new CashDialog(this);
        cashDialog.setTitle("是否关闭手势解锁？\n");
        cashDialog.setMessage("关闭后，手势数据将被删除\n\n");
        cashDialog.setYesOnclickListener("关闭", new CashDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                cashDialog.dismiss();
                aCache.remove(userPhone);
                stGestureOpen.setSwitchIsChecked(false);

            }
        });
        cashDialog.setNoOnclickListener("否", new CashDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                cashDialog.dismiss();
                stGestureOpen.setSwitchIsChecked(true);

            }
        });
        cashDialog.show();
    }
    @Subscribe
    public  void updateEvent(MsgEvent msgEvent){
        if("ok".equals(msgEvent.msg)){
            stGestureOpen.setSwitchIsChecked(true);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
