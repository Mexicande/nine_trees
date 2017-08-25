package cn.com.stableloan.utils.permission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * Created by apple on 2017/8/24.
 */

public class ContactPermission {


  /*
    private void getPermission(final int i, Context context) {
        AndPermission.with(context)
                .requestCode(100)
                .permission(Manifest.permission.READ_CONTACTS)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(context, rationale)
                                .show();
                    }
                })
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        if (requestCode == 100) {
                            Uri uri = Uri.parse("content://contacts/people");
                            Intent intent = new Intent(Intent.ACTION_PICK, uri);
                            startActivityForResult(intent, i);
                        }
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

                    }
                })
                .start();


    }*/
}
