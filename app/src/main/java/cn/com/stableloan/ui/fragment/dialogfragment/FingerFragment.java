package cn.com.stableloan.ui.fragment.dialogfragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.ui.activity.GestureLoginActivity;
import cn.com.stableloan.ui.activity.MainActivity;
import cn.com.stableloan.ui.activity.UserInformationActivity;
import cn.com.stableloan.ui.activity.Verify_PasswordActivity;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.utils.fingerprint.FingerprintIdentify;
import cn.com.stableloan.utils.fingerprint.aosp.BaseFingerprint;

/**
 * A simple {@link Fragment} subclass.
 */
public class FingerFragment extends DialogFragment {
    @Bind(R.id.verify)
    TextView verify;
    @Bind(R.id.iv_finger)
    ImageView ivFinger;
    @Bind(R.id.text)
    TextView text;
    private FingerprintIdentify mFingerprintIdentify;
    public FragmentListener mListener;
    private MyHandler switchHandler;

    public static interface FragmentListener {
        //跳到h5页面
        void verify(int type);
    }

    public FingerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            mListener = ((FragmentListener) context);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_finger, container, false);
        ButterKnife.bind(this, view);
        Looper looper = Looper.myLooper();
        switchHandler = new MyHandler(looper);
        start();
        setListener();
        return view;
    }

    private void setListener() {

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
    }

    public void start() {
        mFingerprintIdentify = new FingerprintIdentify(getActivity(), new BaseFingerprint.FingerprintIdentifyExceptionListener() {
            @Override
            public void onCatchException(Throwable exception) {
            }
        });
        mFingerprintIdentify.startIdentify(3, new BaseFingerprint.FingerprintIdentifyListener() {
            @Override
            public void onSucceed() {
                text.setVisibility(View.GONE);
                verify.setText("指纹识别中...");
                verify.setTextColor(getResources().getColor(R.color.select_text_color));
                switchHandler.sendEmptyMessageDelayed(2, 500);

                switchHandler.sendEmptyMessageDelayed(1, 1000);

              /*  getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        verify.setText("指纹识别中...");
                        switchHandler.sendEmptyMessageDelayed(2, 500);

                        switchHandler.sendEmptyMessageDelayed(1, 1000);

                    }
                });*/


            }

            @Override
            public void onNotMatch(int availableTimes) {
                verify.setText(3-availableTimes+"次验证错误");
                text.setVisibility(View.VISIBLE);
                ivFinger.setImageResource(R.mipmap.finger_fail);
                verify.setTextColor(Color.RED);
                //  ToastUtils.showToast(getActivity(), "指纹不匹配，剩余"+availableTimes+" 次机会");
            }

            @Override
            public void onFailed(boolean isDeviceLocked) {
                Bundle arguments = getArguments();
                verify.setText("解锁次数已达到上限");
                verify.setTextColor(Color.RED);
                if (arguments == null) {
                    String from = getActivity().getIntent().getStringExtra("from");
                    if("userinformation".equals(from)){
                        getActivity().startActivity(new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "userinformation"));
                        getActivity().finish();
                    }else if("splash".equals(from)){
                        getActivity().startActivity(new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "splash"));
                        getActivity().finish();
                    }else if("apply".equals(from)){
                        Intent intent=new Intent(getActivity(),Verify_PasswordActivity.class);
                        intent.putExtra("from", "apply");
                        startActivityForResult(intent,100);
                    }
                } else {
                    mListener.verify(1);
                    getDialog().cancel();

                }
                // ToastUtils.showToast(getActivity(), "验证失败，设备指纹暂时锁定");
            }

            @Override
            public void onStartFailedByDeviceLocked() {
                ToastUtils.showToast(getActivity(), "设备指纹暂时锁定,请使用密码登陆");
                Bundle arguments = getArguments();
                if (arguments == null) {
                    String from = getActivity().getIntent().getStringExtra("from");
                    if("userinformation".equals(from)){
                        getActivity().startActivity(new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "userinformation"));
                        getActivity().finish();
                    }else if("splash".equals(from)){
                        getActivity().startActivity(new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "splash"));
                        getActivity().finish();
                    }else if("apply".equals(from)){
                        Intent intent=new Intent(getActivity(),Verify_PasswordActivity.class);
                        intent.putExtra("from", "apply");
                        startActivityForResult(intent,100);
                    }
                } else {
                    mListener.verify(1);
                    getDialog().cancel();

                }
            }
        });
    }


    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) { // 处理消息
            switch (msg.what) {
                case 1:
                    Bundle arguments = getArguments();
                    if (arguments == null) {
                        mListener.verify(0);
                    }
                    getDialog().cancel();
                    break;
                case 2:
                    ivFinger.setImageResource(R.mipmap.finger_succee);
                    break;

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mFingerprintIdentify.cancelIdentify();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    @OnClick(R.id.cancel)
    public void onViewClicked() {
        getDialog().cancel();
        mListener.verify(1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
                Intent intent = new Intent();
                intent.putExtra("ok", "ok");
                getActivity().setResult(1000, intent);
                getActivity().finish();

        }
    }
}
