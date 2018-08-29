package cn.com.cashninetrees.ui.fragment.dialogfragment;


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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.cashninetrees.R;
import cn.com.cashninetrees.api.Urls;
import cn.com.cashninetrees.common.Constants;
import cn.com.cashninetrees.model.integarl.AdvertisingBean;
import cn.com.cashninetrees.ui.activity.HtmlActivity;
import cn.com.cashninetrees.utils.OnClickStatistics;
import cn.com.cashninetrees.utils.SPUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdialogFragment extends DialogFragment {


    @Bind(R.id.fl_content_container)
    ImageView flContentContainer;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.anim_container)
    RelativeLayout animContainer;

    private static String popup=null;

    private AdvertisingBean popupBean;

    public static AdialogFragment newInstance(AdvertisingBean mPopupBean) {
        AdialogFragment instance = new AdialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(popup,mPopupBean);
        instance.setArguments(args);
        return instance;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Base_AlertDialog);

    }

    public AdialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adialog, container, false);
        final Window window = getDialog().getWindow();
        if(window!=null){
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
        ButterKnife.bind(this, view);
        initDialog();

        return view;
    }

    private void initDialog() {
        popupBean = (AdvertisingBean) getArguments().getSerializable(popup);
        if(popupBean!=null&&popupBean.getData().getImg()!=null){
            Glide.with(this)
                    .load(popupBean.getData().getImg())
                    .into(flContentContainer);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.fl_content_container, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_content_container:
                String mToken = SPUtil.getString(getActivity(), Urls.lock.TOKEN);
                OnClickStatistics.buriedStatistics(mToken, Constants.ADIALOG);

                if (!TextUtils.isEmpty(popupBean.getData().getUrl())) {
                    Intent intent = new Intent(getActivity(), HtmlActivity.class);
                    intent.putExtra("link", popupBean.getData().getUrl());
                    intent.putExtra("title", popupBean.getData().getName());
                    startActivity(intent);
                    dismiss();
                }
                break;
            case R.id.iv_close:
                dismiss();

                break;
            default:
                break;
        }
    }
}
