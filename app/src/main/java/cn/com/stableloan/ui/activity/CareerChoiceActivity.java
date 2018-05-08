package cn.com.stableloan.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

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
    @Bind(R.id.house_flowlayout)
    TagFlowLayout houseFlowlayout;
    @Bind(R.id.car_flowlayout)
    TagFlowLayout carFlowlayout;
    @Bind(R.id.occupation_flowlayout)
    TagFlowLayout occupationFlowlayout;
    @Bind(R.id.checkbox)
    AppCompatCheckBox checkbox;
    private ArrayList<String> list;
    private ArrayList<String> houseList;
    private ArrayList<String> carList;
    private ArrayList<String> workList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_career_choice);
        ButterKnife.bind(this);
        initDate();
        initView();
        setListener();
    }

    private void initDate() {
        list = new ArrayList<>();
        list.add("有卡");
        list.add("无卡");
        houseList = new ArrayList<>();
        houseList.add("有房贷");
        houseList.add("有房无贷");
        houseList.add("无贷");
        carList = new ArrayList<>();
        carList.add("有车贷");
        carList.add("有车无贷");
        carList.add("无车");
        workList = new ArrayList<>();
        workList.add("上班族");
        workList.add("企业主");
        workList.add("自由职业");
        workList.add("其他");
    }

    private void setListener() {
        idFlowlayout.getAdapter().setSelectedList(0);
        houseFlowlayout.getAdapter().setSelectedList(0);
        carFlowlayout.getAdapter().setSelectedList(0);
        occupationFlowlayout.getAdapter().setSelectedList(0);

    }

    private void initView() {
        int isInsure = getIntent().getIntExtra("is_insure", 0);
        if (isInsure == 1) {
            layoutCheck.setVisibility(View.VISIBLE);
        }
        idFlowlayout.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final LayoutInflater mInflater = LayoutInflater.from(CareerChoiceActivity.this);
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        idFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        houseFlowlayout.setAdapter(new TagAdapter<String>(houseList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final LayoutInflater mInflater = LayoutInflater.from(CareerChoiceActivity.this);
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        idFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        carFlowlayout.setAdapter(new TagAdapter<String>(carList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final LayoutInflater mInflater = LayoutInflater.from(CareerChoiceActivity.this);
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        idFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        occupationFlowlayout.setAdapter(new TagAdapter<String>(workList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final LayoutInflater mInflater = LayoutInflater.from(CareerChoiceActivity.this);
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        idFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }

    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        boolean checked = checkbox.isChecked();
        Set<Integer> selectedList = idFlowlayout.getSelectedList();



    }
}
