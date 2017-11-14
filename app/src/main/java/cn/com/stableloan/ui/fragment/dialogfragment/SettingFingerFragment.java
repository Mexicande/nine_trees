package cn.com.stableloan.ui.fragment.dialogfragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFingerFragment extends DialogFragment {


    public SettingFingerFragment() {
        // Required empty public constructor
    }
    private static final String ACTION_SETTING = "android.settings.SETTINGS";

    private FragmentListener listener;
    public static interface FragmentListener {

        //跳到h5页面
        void setting();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SettingFingerFragment.FragmentListener) {
            listener = ((SettingFingerFragment.FragmentListener) context);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_finger, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_follow, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_follow:
                Intent intent = new Intent(ACTION_SETTING);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(intent);
                    getDialog().dismiss();
                    listener.setting();
                } catch (Exception e) {

                }
                break;
            case R.id.cancel:
                getDialog().cancel();
                listener.setting();
                break;
        }
    }
}
