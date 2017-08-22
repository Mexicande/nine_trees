package cn.com.stableloan.ui.activity.integarl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.com.stableloan.R;

public class InviteFriendsActivity extends AppCompatActivity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, InviteFriendsActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
    }
}
