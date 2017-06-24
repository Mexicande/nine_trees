package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;

/**
 * 设置页面
 */

public class SettingActivity extends AppCompatActivity {
    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.layout_go)
    LinearLayout layoutGo;
    @Bind(R.id.layout_word)
    SuperTextView layoutWord;
    @Bind(R.id.layout_nick)
    SuperTextView layoutNick;
    @Bind(R.id.layout_profession)
    SuperTextView layoutProfession;
    @Bind(R.id.exit)
    SuperTextView exit;
    @Bind(R.id.version)
    TextView version;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting1);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.layout_word, R.id.layout_nick, R.id.layout_profession, R.id.exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_word:
                break;
            case R.id.layout_nick:
                break;
            case R.id.layout_profession:
                break;
            case R.id.exit:
                break;
        }
    }
}
