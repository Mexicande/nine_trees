package cn.com.stableloan.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionFragment;

import cn.com.stableloan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LotteryFragment extends ImmersionFragment {


    public LotteryFragment() {
        // Required empty public constructor
    }

    @Override
    protected void immersionInit() {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(false)
                .navigationBarColor(R.color.colorPrimary)
                .init();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lottery, container, false);
    }

}
