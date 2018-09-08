package cn.com.laifenqicash.ui.fragment.dialogfragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.laifenqicash.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentitySaveFragment extends DialogFragment {


    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_exit)
    TextView tvExit;

    public IdentitySaveFragment() {
        // Required empty public constructor
    }

    private SaveFragmentListener listener;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_cancel, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                listener.exit(1);
                break;
            case R.id.tv_exit:
                listener.exit(2);
                break;
        }
    }

    public  static  interface SaveFragmentListener {

        void exit(int type);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IdentitySaveFragment.SaveFragmentListener) {
            listener = (IdentitySaveFragment.SaveFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_identity_save, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

}
