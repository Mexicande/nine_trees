package cn.com.stableloan.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.stableloan.R;
import cn.com.stableloan.ui.activity.IdentityinformationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeElementsFragment extends DialogFragment {

    @Bind(R.id.tv_go)
    TextView tvGo;

    public static ThreeElementsFragment newInstance() {
        Bundle bundle = new Bundle();
        ThreeElementsFragment contentFragment = new ThreeElementsFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    public ThreeElementsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three_elements, container, false);
        ButterKnife.bind(this, view);
        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    dismiss();
                    getActivity().finish();
                    return true;
                }else {
                    //这里注意当不是返回键时需将事件扩散，否则无法处理其他点击事件
                    return false;
                }
            }
        });
        setLisenter();
        return view;
    }

    private void setLisenter() {
        tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdentityinformationActivity.launch(getActivity());
                dismiss();
                getActivity().finish();
            }
        });
    }


    @Override
    public void dismiss() {
        super.dismiss();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
