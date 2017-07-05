package cn.com.stableloan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.base.BaseActivity;
import cn.com.stableloan.model.InformationEvent;
import cn.com.stableloan.model.PicStatusEvent;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtils;
import cn.com.stableloan.utils.ToastUtils;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import okhttp3.Call;
import okhttp3.Response;

public class BankUploadActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.fiv)
    ImageView fiv;
    @Bind(R.id.bt_takePhoto)
    Button btTakePhoto;
    @Bind(R.id.bt_Picture)
    Button btPicture;
    @Bind(R.id.progress)
    RingProgressBar progress;
    private String UserToken;
    private String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_upload);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getPictrue();
        initToolbar();

    }

    private void getPictrue() {
        UserToken = (String) SPUtils.get(this, "token", "1");
        String signature = (String) SPUtils.get(this, "signature", "1");
        Map<String, String> parms1 = new HashMap<>();
        parms1.put("var", "1");
        parms1.put("token", UserToken);
        parms1.put("signature", signature);
        JSONObject json = new JSONObject(parms1);
        OkGo.<String>post(Urls.NEW_URL + Urls.Pictrue.Get_Pictrue)
                .tag(this)
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            String isSuccess = jsonObject1.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                String status = jsonObject1.getString("status");
                                if ("1".equals(status)) {
                                    getToken();
                                    String photo1 = jsonObject1.getString("debit_photo");
                                    if (photo1 != null) {
                                        text.setVisibility(View.GONE);
                                    }
                                    RequestOptions options = new RequestOptions()
                                            .centerInside()
                                            .placeholder(R.mipmap.ic_default_bank)
                                            .error(R.mipmap.ic_default_bank)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

                                    Glide.with(BankUploadActivity.this).load(photo1).apply(options).into(fiv);
                                } else {
                                    Intent intent = new Intent(BankUploadActivity.this, Verify_PasswordActivity.class).putExtra("from", "BankUpload");
                                    startActivity(intent);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });
    }

    private void getToken() {
        UserToken = (String) SPUtils.get(this, "token", "1");
        String signature = (String) SPUtils.get(this, "signature", "1");
        Map<String, String> parms = new HashMap<>();
        parms.put("token", UserToken);
        parms.put("signature", signature);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_URL + Urls.Pictrue.GET_QINIUTOKEN)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            String isSuccess = object.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                String status = object.getString("status");
                                if ("1".equals(status)) {
                                    token = object.getString("token");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                });


    }

    private void initToolbar() {

        titleName.setText("银行卡照片");
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_back, R.id.bt_takePhoto, R.id.bt_Picture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                EventBus.getDefault().post(new PicStatusEvent("update"));
                finish();
                break;
            case R.id.bt_takePhoto:
                takePhoto();

                break;
            case R.id.bt_Picture:
                takePicture();

                break;
        }
    }

    private void takePicture() {

        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isZoomAnim(true)
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(false)// 是否压缩
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                //.hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .previewEggs(true)
                .openClickSound(true)// 是否开启点击声音
                // .selectionMedia(t)// 是否传入已选图片
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(1);//结果回调onActivityResult code
    }

    private List<LocalMedia> selectList = new ArrayList<>();

    private void takePhoto() {

        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                //.enableCrop(true)// 是否裁剪
                .compress(false)// 是否压缩
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(true)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()////显示多少秒以内的视频or音频也可适用
                .forResult(1);//结果回调onActivityResult code

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    final String path = selectList.get(selectList.size() - 1).getPath();

                    UploadOptions uploadOptions = new UploadOptions(null, null, false,
                            new UpProgressHandler() {
                                @Override
                                public void progress(String key, double percent) {
                                    progress.setVisibility(View.VISIBLE);
                                    progress.setProgress((int)(percent*100));
                                    LogUtils.i("response", "key" + key + "----" + percent);
                                }
                            }, null);
                    AppApplication.getUploadManager().put(path, null, token, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info.isOK()) {
                                Log.i("qiniu", "Upload Success");
                                LogUtils.i("response", response.toString());
                                String key1 = null;
                                try {
                                    key1 = response.getString("key");
                                    UpLoadImage(key1);
                                    progress.setVisibility(View.GONE);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text.setVisibility(View.GONE);

                                RequestOptions options = new RequestOptions()
                                        .centerInside()
                                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);


                                Glide.with(BankUploadActivity.this).load(path).apply(options).into(fiv);
                                LogUtils.i("onActivityResult", selectList.size());

                                fiv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        PictureSelector.create(BankUploadActivity.this).externalPicturePreview(selectList.size() - 1, selectList);
                                    }
                                });

                            } else {
                                ToastUtils.showToast(BankUploadActivity.this, "上传失败");
                            }
                        }
                    }, uploadOptions);

                    break;
            }
        }
    }

    @Subscribe
    public void onMessageEvent(InformationEvent event) {
        String message = event.message;
        if ("bankPic".equals(message)) {
            getPictrue();
        }
    }

    private void UpLoadImage(String key) {
        UserToken = (String) SPUtils.get(this, "token", "1");

        Map<String, String> parms = new HashMap<>();
        parms.put("var", "1");
        parms.put("debit_photo", key);
        parms.put("token", UserToken);
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.NEW_URL + Urls.Pictrue.UpLoadImage)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            String isSuccess = object.getString("isSuccess");
                            if ("1".equals(isSuccess)) {
                                String msg = object.getString("msg");
                                ToastUtils.showToast(BankUploadActivity.this, msg);
                            } else {
                                String msg = object.getString("msg");
                                ToastUtils.showToast(BankUploadActivity.this, msg);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            EventBus.getDefault().post(new PicStatusEvent("update"));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
