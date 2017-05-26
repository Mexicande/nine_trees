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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.bean.UserBean;
import cn.com.stableloan.ui.fragment.UserFragment;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.utils.ToastUtils;

public class UpdataProfessionActivity extends AppCompatActivity {



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
        UserBean userBean= (UserBean) tinyDB.getObject("user", UserBean.class);
        String identity = userBean.getIdentity();


        if(identity!=null){
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
                LogUtils.i("FLAG",Flge);
                TinyDB tinyDB=new TinyDB(this);
                UserBean userBean= (UserBean) tinyDB.getObject("user", UserBean.class);
                userBean.setIdentity(""+Flge);
                setResult(-1,new Intent().putExtra("HeadPhoto",Flge));
                finish();
                break;
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
