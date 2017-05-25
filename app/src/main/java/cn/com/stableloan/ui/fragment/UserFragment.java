package cn.com.stableloan.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.view.SelfDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    @Bind(R.id.User_logo)
    ImageView UserLogo;
    @Bind(R.id.tv_nick)
    TextView tvNick;
    @Bind(R.id.tv_UserPhone)
    TextView tvUserPhone;
    @Bind(R.id.layout_word)
    RelativeLayout layoutWord;
    @Bind(R.id.layout_nick)
    RelativeLayout layoutNick;
    @Bind(R.id.layout_profession)
    RelativeLayout layoutProfession;
    @Bind(R.id.bt_exit)
    Button btExit;

    private SelfDialog selfDialog;


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.layout_word, R.id.layout_nick, R.id.layout_profession, R.id.bt_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_word://密码
                break;
            case R.id.layout_nick://昵称
                break;
            case R.id.layout_profession://职业
                break;
            case R.id.bt_exit: //退出登录
                selfDialog = new SelfDialog(getActivity());
                selfDialog.setTitle("提示");
                selfDialog.setMessage("确定退出应用?");
                selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        selfDialog.dismiss();
                        LoginActivity.launch(getActivity());
                        getActivity().finish();
                    }
                });
                selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        selfDialog.dismiss();
                    }
                });
                selfDialog.show();
                break;
        }
    }
}
