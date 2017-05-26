package cn.com.stableloan.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.bean.UserBean;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.UpdataProfessionActivity;
import cn.com.stableloan.ui.activity.UpdateNickActivity;
import cn.com.stableloan.ui.activity.UpdatePassWordActivity;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.TinyDB;
import cn.com.stableloan.view.SelfDialog;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends ImmersionFragment {
    private int a;
    public void setList(int a){

    }


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

    private static final int   FLAG_Profession    = 1;
    private static final int   SEND_Profession_    = 1000;


    private static final int   FLAG_NICK    = 2;
    private static final int   SEND_NICK    = 2000;
    private static final int    Moon    = 1;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        TinyDB tinyDB=new TinyDB(getActivity());
        UserBean user = (UserBean) tinyDB.getObject("user", UserBean.class);
        if(user!=null){
            String nickname = user.getNickname();
            String userphone = user.getUserphone();
            if(user.getNickname()!=null){
                tvNick.setText(nickname);
            }else if (userphone!=null){
                tvUserPhone.setText(userphone);
            }
        }else {
            LoginActivity.launch(getActivity());
            getActivity().finish();
        }

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
                UpdatePassWordActivity.launch(getActivity());
                break;
            case R.id.layout_nick://昵称
                startActivityForResult(new Intent(getActivity(),UpdateNickActivity.class),FLAG_NICK);
                break;
            case R.id.layout_profession://职业
                startActivityForResult(new Intent(getActivity(),UpdataProfessionActivity.class),FLAG_Profession);
                break;
            case R.id.bt_exit: //退出登录
                exit();

                break;
        }
    }

    private void exit() {
        selfDialog = new SelfDialog(getActivity());
        selfDialog.setTitle("提示");
        selfDialog.setMessage("确定退出登陆?");
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
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(false)
                .transparentStatusBar()
                .navigationBarColor(R.color.colorStatus)
                .init();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
              /*  case FLAG_Profession:
                    if(SEND_Profession_==resultCode){
                       int headPhoto1 = data.getExtras().getInt("HeadPhoto");
                        LogUtils.i("HeadPhoto",headPhoto1);
                        switch (headPhoto1){
                            case 1:
                                setUserHead(R.mipmap.iv_header01);
                                break;
                            case 2:
                                setUserHead(R.mipmap.iv_header02);
                                break;
                            case 3:
                                setUserHead(R.mipmap.iv_header03);
                                break;
                            case 4:
                                setUserHead(R.mipmap.iv_header04);
                                break;
                        }
                    }
                    break;*/
                //昵称设置
               case FLAG_NICK:
                   if(SEND_NICK==resultCode){
                       String nick = data.getExtras().getString("nick");
                       if(nick!=null){
                           tvNick.setText(nick);
                       }
                   }

            }

    }

    /**
     * 头像职业选择
     * @param imageView
     */
    private void setUserHead( int imageView ) {
        Glide.with(getActivity()).load(imageView)
                .centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new CropCircleTransformation(getActivity())).into(UserLogo);

    }
}
