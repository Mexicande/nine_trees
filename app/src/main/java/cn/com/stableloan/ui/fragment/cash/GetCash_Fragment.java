package cn.com.stableloan.ui.fragment.cash;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.stableloan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetCash_Fragment extends Fragment {


    public GetCash_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_cash_, container, false);
    }

}
