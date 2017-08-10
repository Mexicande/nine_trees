package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.cache.ACache;
import cn.com.stableloan.view.SelfDialog;

/**
 * 设置页面
 */

public class Setting1Activity extends BaseActivity {

    @Bind(R.id.layout_nick)
    SuperTextView layoutNick;
    @Bind(R.id.layout_profession)
    SuperTextView layoutProfession;
    @Bind(R.id.exit)
    SuperTextView exit;
    @Bind(R.id.version)
    TextView version;
    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.layout_go)
    LinearLayout layoutGo;

    private SelfDialog selfDialog;
    private ACache aCache;

    public static void launch(Context context) {

        context.startActivity(new Intent(context, Setting1Activity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting1);
        ButterKnife.bind(this);
        aCache = ACache.get(this);

        initToolbar();

    }

    private void initToolbar() {

        titleName.setText("设置");
        layoutGo.setVisibility(View.VISIBLE);

    }




    @OnClick({R.id.layout_Safe, R.id.layout_nick, R.id.layout_profession, R.id.exit, R.id.layout_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_Safe:
                SafeActivity.launch(this);
                break;
            case R.id.layout_nick:
                UpdateNickActivity.launch(this);
                break;
            case R.id.layout_profession:
                UpdataProfessionActivity.launch(this);
                break;
            case R.id.exit:
                exit();
                break;
            case R.id.layout_go:
                finish();
                break;
        }
    }

    private void exit() {
        selfDialog = new SelfDialog(this);
        selfDialog.setTitle("提示");
        selfDialog.setMessage("确定退出登陆?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfDialog.dismiss();
                SPUtils.clear(Setting1Activity.this);
                TinyDB tinyDB = new TinyDB(Setting1Activity.this);
                tinyDB.clear();
                startActivity(new Intent(Setting1Activity.this, LoginActivity.class).putExtra("from", "user2"));
                finish();
            }
        });
        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }


}
