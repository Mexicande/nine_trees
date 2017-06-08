package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.stableloan.R;
import cn.com.stableloan.utils.LogUtils;
import okhttp3.Call;
import okhttp3.Response;

public class PlatformInfoActivity extends AppCompatActivity {
    @Bind(R.id.test)
    TextView test;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, PlatformInfoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_info);
        ButterKnife.bind(this);

        String url = "http://47.93.197.52:8080/anwendai/Home/Api/GetSlotdetail";
        String pid = getIntent().getStringExtra("pid");


        if (pid != null) {
            LogUtils.i("PlatformInfoActivity", pid);
            HashMap<String, String> params = new HashMap<>();
            params.put("pl_id", pid);

            JSONObject jsonObject = new JSONObject(params);
            OkGo.post(url).tag(this)
                    .upJson(jsonObject.toString())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {

                            test.setText(s);
                            LogUtils.i("-----------", s + "---" + response.toString());
                             /*   PlarformInfo info = gson.fromJson(s, PlarformInfo.class);
                                LogUtils.i("-----------",info.toString());*/

                        }
                    });
        }
    }

}
