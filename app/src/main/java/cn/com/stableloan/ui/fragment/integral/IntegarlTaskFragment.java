package cn.com.stableloan.ui.fragment.integral;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
        String token = (String) SPUtils.get(getActivity(), Urls.lock.TOKEN, null);
        HashMap<String, String> params = new HashMap<>();
        params.put(Urls.lock.TOKEN, token);
        JSONObject object = new JSONObject(params);
        OkGo.<String>post(Urls.NEW_Ip_url + Urls.Integarl.getAccumulatePoints)
                .upJson(object)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            IntegarlBean bean = gson.fromJson(s, IntegarlBean.class);
                            if (bean.getError_code() == 0) {
                                if(bean.getData().getStatus().getStatus1()==1){
                                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.invite_list_down);
                                    UserInformation.setTextBackground(drawable);
                                    UserInformation.setRightString("已完成");
                                }else {
                                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.invite_list_up);
                                    UserInformation.setRightString("+500");
                                    UserInformation.setRightTextColor(R.color.colorPrimary);
                                    UserInformation.setTextBackground(drawable);
                                }
                                if(bean.getData().getStatus().getStatus2()==1){
                                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.invite_list_down);
                                    inviteFirst.setRightTextColor(Color.WHITE);
                                    inviteFirst.setRightString("已完成");
                                    inviteFirst.setTextBackground(drawable);
                                }else {
                                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.invite_list_up);
                                    inviteFirst.setTextBackground(drawable);
                                    inviteFirst.setRightString("+100");
                                }
                                if(bean.getData().getStatus().getStatus3()==1){
                                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.invite_list_down);
                                    inviteSecond.setTextBackground(drawable);
                                    inviteSecond.setRightString("已完成");

                                }else {
                                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.invite_list_up);
                                    inviteSecond.setTextBackground(drawable);
                                    inviteSecond.setRightString("+150");

                                }
                                if(bean.getData().getStatus().getStatus4()==1){
                                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.invite_list_down);
                                    inviteThird.setRightString("已完成");
                                    inviteThird.setTextBackground(drawable);

                                }else {
                                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.invite_list_up);
                                    inviteThird.setTextBackground(drawable);
                                    inviteThird.setRightString("+250");
                                }
                                EventBus.getDefault().post(new IntregarlEvent(bean.getData().getOffical(), bean.getData().getCredits(), bean.getData().getTopCredits()));
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
            default:
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