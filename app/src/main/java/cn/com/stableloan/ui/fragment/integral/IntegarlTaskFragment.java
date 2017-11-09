package cn.com.stableloan.ui.fragment.integral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.bean.IntregarlEvent;
import cn.com.stableloan.model.integarl.IntegarlBean;
import cn.com.stableloan.ui.activity.IdentityinformationActivity;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.activity.integarl.InviteFriendsActivity;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.supertextview.SuperTextView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 积分任务
 */
public class IntegarlTaskFragment extends Fragment {


    @Bind(R.id.User_information)
    SuperTextView UserInformation;
    @Bind(R.id.invite_first)
    SuperTextView inviteFirst;
    @Bind(R.id.invite_second)
    SuperTextView inviteSecond;
    @Bind(R.id.invite_third)
    SuperTextView inviteThird;
    private static final int REQUEST_CODE=100;
    private static final int RESulit_CODE=200;

    public IntegarlTaskFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_integarl_task, container, false);
        ButterKnife.bind(this, view);
        getInDate();
        return view;
    }

    private void getInDate() {
        String token = (String) SPUtils.get(getActivity(), "token", "1");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.Ip_url + Urls.Integarl.getAccumulatePoints)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            IntegarlBean bean = gson.fromJson(s, IntegarlBean.class);
                            if (bean.getError_code() == 0) {
                                EventBus.getDefault().post(new IntregarlEvent(bean.getData().getOffical(), bean.getData().getCredits(), bean.getData().getTopCredits()));
                            } else if (bean.getCode() == 0) {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.putExtra("message", bean.getError_message());
                                intent.putExtra("from", "IntegarlError");
                                startActivity(intent);
                            } else {
                                ToastUtils.showToast(getActivity(), bean.getError_message());
                            }
                        }
                    }
                });


    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @OnClick({R.id.User_information, R.id.invite_first, R.id.invite_second, R.id.invite_third})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.User_information:
                Intent intent=new Intent(getActivity(),IdentityinformationActivity.class);
                intent.putExtra("from","invite");
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.invite_first:
                Intent FriendIntent=new Intent(getActivity(),InviteFriendsActivity.class);
                FriendIntent.putExtra("from","invite");
                startActivityForResult(FriendIntent,REQUEST_CODE);
                break;
            case R.id.invite_second:
                Intent FriendIntent2=new Intent(getActivity(),InviteFriendsActivity.class);
                FriendIntent2.putExtra("from","invite");
                startActivityForResult(FriendIntent2,REQUEST_CODE);
                break;
            case R.id.invite_third:
                Intent FriendIntent3=new Intent(getActivity(),InviteFriendsActivity.class);
                FriendIntent3.putExtra("from","invite");
                startActivityForResult(FriendIntent3,REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            getInDate();
        }
    }
}