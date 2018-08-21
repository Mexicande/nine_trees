package cn.com.stableloan.ui.fragment.dialogfragment;


import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.model.integarl.AdvertisingBean;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.supertextview.SuperButton;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 *         微信跳转弹窗
 */
public class WeChatDialogFragment extends DialogFragment {


    @Bind(R.id.apply)
    SuperButton apply;


    public static WeChatDialogFragment newInstance(String name) {
        WeChatDialogFragment instance = new WeChatDialogFragment();
        Bundle args = new Bundle();
        args.putString("name",name);
        instance.setArguments(args);
        return instance;

    }


    public WeChatDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_we_chat_dialog, container, false);
        ButterKnife.bind(this, view);
        final Window window = getDialog().getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Base_AlertDialog);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.apply)
    public void onViewClicked() {
        ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        if (cmb != null) {
            String name = getArguments().getString("name");
            if(!TextUtils.isEmpty(name)){
                cmb.setText(name);
            }else {
                cmb.setText("安稳钱包");
            }
        }
        getWechatApi();

    }
    private void getWechatApi() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
            dismiss();
        } catch (ActivityNotFoundException e) {

            ToastUtils.showToast(getActivity(),"检查到您手机没有安装微信，请安装后使用该功能");
        }
    }
}
