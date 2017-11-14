package cn.com.stableloan.ui.fragment.dialogfragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
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
        Looper looper = Looper.myLooper ();
        switchHandler=new MyHandler(looper);
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        verify.setText("指纹识别中...");

                        switchHandler.sendEmptyMessageDelayed(1,1000);

                    }
                });


            }

            @Override
            public void onNotMatch(int availableTimes) {

                //ToastUtils.showToast(getActivity(), "指纹不匹配，剩余"+availableTimes+" 次机会");
            }

            @Override
            public void onFailed(boolean isDeviceLocked) {
                Bundle arguments = getArguments();

                verify.setText("指纹解锁已达到上限...");
                if(arguments==null){
                    getActivity().startActivity(new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "splash"));
                    getActivity().finish();
                }else {
                    mListener.verify(1);
                    getDialog().cancel();

                }
               // ToastUtils.showToast(getActivity(), "验证失败，设备指纹暂时锁定");
            }

            @Override
            public void onStartFailedByDeviceLocked() {
                ToastUtils.showToast(getActivity(), "设备指纹暂时锁定,请使用密码登陆");
                Bundle arguments = getArguments();

                if(arguments==null){
                    getActivity().startActivity(new Intent(getActivity(), Verify_PasswordActivity.class).putExtra("from", "splash"));
                    getActivity().finish();
                }else {
                    mListener.verify(1);
                    getDialog().cancel();

                }
            }
        });
    }


    private class MyHandler extends Handler{
        public MyHandler(Looper looper){
            super (looper);
        }
        @Override
        public void handleMessage(Message msg) { // 处理消息
            switch (msg.what){
                case 1:
                    Bundle arguments = getArguments();
                    if(arguments==null){
                        mListener.verify(0);
                    }
                    getDialog().cancel();
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

}
