package cn.com.stableloan.ui.Setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.stableloan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SafeFragment extends Fragment {


    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.safe)
    com.allen.library.SuperTextView safe;
    @Bind(R.id.change_password)
    com.allen.library.SuperTextView changePassword;
    @Bind(R.id.pattern)
    com.allen.library.SuperTextView pattern;

    public SafeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safe, container, false);
        ButterKnife.bind(this, view);
        titleName.setText("账号安全");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
