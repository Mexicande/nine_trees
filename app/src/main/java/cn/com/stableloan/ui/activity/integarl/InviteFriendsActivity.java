package cn.com.stableloan.ui.activity.integarl;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.tencent.connect.share.QQShare;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.integarl.InviteFriendList;
import cn.com.stableloan.model.integarl.InviteFriendsBean;
import cn.com.stableloan.ui.activity.LoginActivity;
import cn.com.stableloan.ui.adapter.InviteListAdapter;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.dialog.Qr_Dialog;
import cn.com.stableloan.view.share.QQManager;
import cn.com.stableloan.view.share.QQShareContent;
import cn.com.stableloan.view.share.StateListener;
import cn.com.stableloan.view.share.TPManager;
import cn.com.stableloan.view.share.WXManager;
import cn.com.stableloan.view.share.WXShareContent;
import okhttp3.Call;
import okhttp3.Response;

public class InviteFriendsActivity extends BaseActivity {
    @Bind(R.id.contact_RecyclerView)
    RecyclerView contactRecyclerView;
    @Bind(R.id.goback)
    LinearLayout goback;
    @Bind(R.id.textImage)
    ImageView textImage;
    @Bind(R.id.layout_Share_WeChat)
    LinearLayout layoutShareWeChat;
    @Bind(R.id.layout_Share_WeChatFriend)
    LinearLayout layoutShareWeChatFriend;
    @Bind(R.id.layout_Share_QQ)
    LinearLayout layoutShareQQ;
    @Bind(R.id.layout_RQCODE)
    RelativeLayout layoutRQCODE;
    @Bind(R.id.layout_Send_Contacts)
    RelativeLayout layoutSendContacts;
    @Bind(R.id.nick)
    TextView tv_nick;
    private InviteFriendList friendList;
    private WXManager wxManager;
    private QQManager qqManager;

    private InviteListAdapter listAdapter;
    private String base_str;
    private Qr_Dialog qr_dialog;
    private static final  int REQUEST_CODE=100;
    private static final int TOKEN_FAIL = 120;
    private static final int INVITE_CODE=200;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, InviteFriendsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        ButterKnife.bind(this);
        initView();
        String nick = getIntent().getStringExtra("nick");
        if(nick!=null){
            tv_nick.setText("Hi, "+nick);
        }
        getInviteList();
        setListener();
    }

    private void initView() {
        listAdapter = new InviteListAdapter(null);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactRecyclerView.setAdapter(listAdapter);
      /*  View view = getLayoutInflater().inflate(R.layout.invite_layout_friend, null);
        listAdapter.addHeaderView(view,0);*/

    }

    /**
     * 邀请好友列表
     */
    private void getInviteList() {
        String token = (String) SPUtils.get(this, "token", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.post(Urls.Ip_url + Urls.Invite.INVITE_LIST)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            friendList = gson.fromJson(s, InviteFriendList.class);
                            if (friendList.getError_code() == 0) {
                                listAdapter.addData(friendList.getData().getInviteLog());
                                base_str = friendList.getData().getQrCode();
                            }else if(friendList.getError_code()==2){
                                Intent intent=new Intent(InviteFriendsActivity.this, LoginActivity.class);
                                intent.putExtra("message",friendList.getError_message());
                                intent.putExtra("from","Friend");
                                startActivityForResult(intent,REQUEST_CODE);
                            }else if(friendList.getError_code()==1136){
                                Intent intent=new Intent(InviteFriendsActivity.this,LoginActivity.class);
                                intent.putExtra("message","1136");
                                intent.putExtra("from","1136");
                                startActivity(intent);
                                finish();

                            }else {
                                ToastUtils.showToast(InviteFriendsActivity.this, friendList.getError_message());
                            }

                        }
                    }
                });

    }

    private void setListener() {

        TPManager.getInstance().initAppConfig(Urls.KEY.WEICHAT_APPID, null, Urls.KEY.QQ_APPID, null);
        wxManager = new WXManager(this);
        qqManager = new QQManager(this);

        StateListener<String> wxStateListener = new StateListener<String>() {
            @Override
            public void onComplete(String s) {
                ToastUtils.showToast(InviteFriendsActivity.this, s);
            }

            @Override
            public void onError(String err) {
                ToastUtils.showToast(InviteFriendsActivity.this, err);
            }

            @Override
            public void onCancel() {
                ToastUtils.showToast(InviteFriendsActivity.this, "取消分享");
            }
        };

        wxManager.setListener(wxStateListener);

        StateListener<String> qqStateListener = new StateListener<String>() {

            @Override
            public void onComplete(String s) {

            }

            @Override
            public void onError(String err) {
                ToastUtils.showToast(InviteFriendsActivity.this, err);

            }

            @Override
            public void onCancel() {
                ToastUtils.showToast(InviteFriendsActivity.this, "取消分享");
            }
        };
        qqManager.setListener(qqStateListener);

    }

    @OnClick({R.id.goback, R.id.layout_Share_WeChat, R.id.layout_Share_WeChatFriend, R.id.layout_Share_QQ, R.id.layout_RQCODE, R.id.layout_Send_Contacts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goback:
                String from = getIntent().getStringExtra("from");
                if(from!=null&&"cash".equals(from)){
                    Intent intent=new Intent();
                    setResult(INVITE_CODE,intent);
                }else {
                    Intent intent=new Intent();
                    setResult(100,intent);
                }
                finish();

                break;
            case R.id.layout_Share_WeChat:
                shareWechat(WXShareContent.WXSession);

                break;
            case R.id.layout_Share_WeChatFriend:
                shareWechat(WXShareContent.WXTimeline);

                break;
            case R.id.layout_Share_QQ:
                QQShareContent contentQQ = new QQShareContent();
                contentQQ.setShareType(QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
                        .setTitle("安稳钱包")
                        .setTarget_url(Urls.KEY.SHARE_INCODE + friendList.getData().getInviteCode()+"go_home=1")
                        .setImage_url("http://orizavg5s.bkt.clouddn.com/logo.png")
                        .setSummary("只需身份证,无需抵押无需面审急速放款!");
                qqManager.share(contentQQ);
                break;
            case R.id.layout_RQCODE:
                String replace = base_str.replace("data:image/png;base64,", "");
                qr_dialog = new Qr_Dialog(this, replace);
                qr_dialog.show();

                break;
            case R.id.layout_Send_Contacts:
                getPermission(1);
                break;
        }
    }

    /**
     * 通讯录权限
     *
     * @param i
     */
    private void getPermission(final int i) {
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(InviteFriendsActivity.this, rationale)
                                .show();
                    }
                })
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        if (requestCode == 100) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.PICK");
                            intent.setType("vnd.android.cursor.dir/phone_v2");
                            startActivityForResult(intent, i);
                        }
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    }
                })
                .start();


    }

    private void shareWechat(int scence) {

        WXShareContent contentWX = new WXShareContent();
        contentWX.setScene(scence)
                .setType(WXShareContent.share_type.WebPage)
                .setWeb_url(Urls.KEY.SHARE_INCODE + friendList.getData().getInviteCode()+"go_home=1")
                .setTitle("安稳钱包")
                .setDescription("只需身份证,无需抵押无需面审急速放款!")
                .setImage_url("http://orizavg5s.bkt.clouddn.com/logo.png");
        wxManager.share(contentWX);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    Uri uri = data.getData();
                    Log.i("uri", "联系人：" + uri + "");
                    String num = null;
                    String name = null;
                    // 创建内容解析者
                    ContentResolver contentResolver = getContentResolver();
                    Cursor cursor = contentResolver.query(uri,
                            null, null, null, null);
                    while (cursor.moveToNext()) {

                        num = cursor.getString(cursor.getColumnIndex("data1"));
                        Log.i("num", "phone：" + num + "");

                        int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                        name = cursor.getString(nameFieldColumnIndex);

                        Log.i("num", "name：" + name + "");

                    }
                    cursor.close();
                    if (num != null) {
                        num = num.replaceAll("-", "");//替换的操作,555-6 -> 5556
                        Log.i("num", "联系人：" + num + "---" + name);
                        String replace = num.replace(" ", "");
                        inviteFriends(replace);
                    }
                    break;

                }
                break;
            case REQUEST_CODE:
                    getInviteList();
                break;

        }
    }

    /**
     * 邀请好友
     */
    private void inviteFriends(String phone) {

        LogUtils.i("invite--phone", phone);
        String token = (String) SPUtils.get(this, "token", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("invitePhone", phone);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.post(Urls.Ip_url + Urls.Invite.INVITE_FRIENDS)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s != null) {
                            Gson gson = new Gson();
                            InviteFriendsBean friendsBean = gson.fromJson(s, InviteFriendsBean.class);
                            if (friendsBean.getError_code() == 0) {
                                ToastUtils.showToast(InviteFriendsActivity.this, friendsBean.getData().getMsg());
                            }else if(friendsBean.getError_code()==1136){
                                Intent intent=new Intent(InviteFriendsActivity.this,LoginActivity.class);
                                intent.putExtra("message","1136");
                                intent.putExtra("from","1136");
                                startActivity(intent);
                            } else {
                                ToastUtils.showToast(InviteFriendsActivity.this, friendsBean.getError_message());
                            }
                        }
                    }
                });


    }


    private String[] getPhoneContacts(Uri uri) {
        String[] contact = new String[2];
        //得到ContentResolver对象
        ContentResolver cr = this.getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if (phone.getCount() > 0) {
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        } else

        {
            return null;
        }
        return contact;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            String from = getIntent().getStringExtra("from");
            if(from!=null&&"cash".equals(from)){
                Intent intent=new Intent();
                setResult(INVITE_CODE,intent);
            }else {
                    Intent intent=new Intent();
                    setResult(100,intent);
            }
            finish();

        }
        return super.onKeyDown(keyCode, event);

    }
}
