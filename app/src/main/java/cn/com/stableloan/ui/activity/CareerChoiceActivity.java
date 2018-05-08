package cn.com.stableloan.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.utils.StatusBarUtil;
import cn.com.stableloan.view.flowlayout_tag.FlowLayout;
import cn.com.stableloan.view.flowlayout_tag.TagAdapter;
import cn.com.stableloan.view.flowlayout_tag.TagFlowLayout;
import cn.com.stableloan.view.supertextview.SuperButton;

/**
 * @author apple
 *         登陆完善信息
 */
public class CareerChoiceActivity extends AppCompatActivity {

    @Bind(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @Bind(R.id.layoutCheck)
    LinearLayout layoutCheck;
    @Bind(R.id.bt_login)
    SuperButton btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_career_choice);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ArrayList<String>list=new ArrayList<>();
        list.add("有卡");
        list.add("无卡");

        idFlowlayout.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                LinearLayout inflate = (LinearLayout) CareerChoiceActivity.this.getLayoutInflater().inflate(R.layout.tag_item, null);
                TextView mTextView = (TextView) inflate.findViewById(R.id.tv_tag);
                inflate.removeView(mTextView);
                mTextView.setText(s);

                return mTextView;
            }
        });
    }

    @OnClick(R.id.bt_login)
    public void onViewClicked() {
    }
}
