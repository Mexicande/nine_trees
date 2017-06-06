package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.bean.UserBean;
import cn.com.stableloan.utils.RegexUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;

public class UpdateNickActivity extends BaseActivity {

    private  static final int Flag=2000;

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bt_commit)
    Button btCommit;
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
                String nick = etNick.getText().toString();
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
                }
                break;
        }
    }

}