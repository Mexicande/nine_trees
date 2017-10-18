package cn.com.stableloan.ui.activity.settingdate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.MsgEvent;
import cn.com.stableloan.model.UserBean;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.view.dialog.CashDialog;
import cn.com.stableloan.view.supertextview.SuperTextView;

public class SwitchPassWordActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.st_CreateGesture)
    SuperTextView stCreateGesture;
    @Bind(R.id.st_GestureOpen)
    SuperTextView stGestureOpen;
    private ACache aCache;
    private String userPhone = "";

    private CashDialog cashDialog;

    private int flag = 0;

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
        final TinyDB tinyDB = new TinyDB(this);
        UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);

        userPhone=user.getUserphone();


        String gesturePassword = aCache.getAsString(userPhone);
        String lock = aCache.getAsString("lock");

        if (gesturePassword != null && !"".equals(gesturePassword)) {
            stGestureOpen.setSwitchIsChecked(true);
            stCreateGesture.setVisibility(View.VISIBLE);
        } else {
            stGestureOpen.setSwitchIsChecked(false);
        }

        stGestureOpen.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flag==0){
                    if (!stGestureOpen.getSwitchIsChecked()) {
                        flag=1;
                        stGestureOpen.setSwitchIsChecked(!stGestureOpen.getSwitchIsChecked());
                        flag=0;
                        RuleDialog();
                    } else {
                        startActivity(new Intent(SwitchPassWordActivity.this, Verify_PasswordActivity.class).putExtra("from", "CreateGesture"));
                        flag=1;
                        stGestureOpen.setSwitchIsChecked(false);
                        flag=0;
                    }
                }

            }
        });


        stCreateGesture.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                startActivity(new Intent(SwitchPassWordActivity.this, Verify_PasswordActivity.class).putExtra("from", "CreateGesture"));

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
        String text="是否关闭手势解锁?";
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#333333"));
        spannableString.setSpan(colorSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(20);
        spannableString.setSpan(absoluteSizeSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);


        cashDialog = new CashDialog(this);
        cashDialog.setTitle("\n"+spannableString+"\n");

        cashDialog.setMessage("关闭后，手势数据将被删除\n\n");
        cashDialog.setYesOnclickListener("关闭", new CashDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                cashDialog.dismiss();
                flag = 1;
                aCache.remove(userPhone);
                final TinyDB tinyDB = new TinyDB(SwitchPassWordActivity.this);
                UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
                user.setCat(Urls.lock.NO_VERIFICATION);
                stGestureOpen.setSwitchIsChecked(false);
                flag=0;
                stCreateGesture.setVisibility(View.GONE);
            }
        });
        cashDialog.setNoOnclickListener("否", new CashDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                cashDialog.dismiss();


            }
        });
        cashDialog.show();
    }

    @Subscribe
    public void updateEvent(MsgEvent msgEvent) {
        if ("ok".equals(msgEvent.msg)) {
            flag=1;
            stGestureOpen.setSwitchIsChecked(true);
            flag=0;
            stCreateGesture.setVisibility(View.VISIBLE);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
