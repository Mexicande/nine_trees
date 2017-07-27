package cn.com.stableloan.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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

import java.lang.ref.WeakReference;
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

public class IdentityUploadActivity extends BaseActivity {


    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_save)
    TextView tvSave;
    @Bind(R.id.fiv)
    ImageView fiv;
    @Bind(R.id.fiv_un)
    ImageView fivUn;
    @Bind(R.id.fiv_back)
    ImageView fivBack;
    @Bind(R.id.progress1)
    RingProgressBar progress1;
    @Bind(R.id.progress2)
    RingProgressBar progress2;
    @Bind(R.id.progress3)
    RingProgressBar progress3;

    private String UserToken;

    private static final String positive_photo = "positive_photo";
    private static final String negative_photo = "negative_photo";
    private static final String am_photo = "am_photo";

    private static final int IdentityInfo = 1000;
    private static final int unInfo = 2000;
    private static final int backInfo = 3000;

    private SwitchHandler mHandler = new SwitchHandler(this);

    private String token = null;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, IdentityUploadActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_upload);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        ivBack.setVisibility(View.VISIBLE);
        titleName.setText("身份证照片");

        getPictrue();

    }


    @Subscribe
    public void onMessageEvent(InformationEvent event) {
        String message = event.message;
        if ("identity".equals(message)) {
            getPictrue();
        }
    }

    private void getPictrue() {
        UserToken = (String) SPUtils.get(this, "token", "1");
        String signature = (String) SPUtils.get(this, "signature", "1");
        Map<String, String> parms1 = new HashMap<>();
        parms1.put("var", "0");
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
                                    String photo1 = jsonObject1.getString(positive_photo);
                                    String photo2 = jsonObject1.getString(negative_photo);
                                    String photo3 = jsonObject1.getString(am_photo);
                                    RequestOptions options = new RequestOptions()
                                            .centerInside()
                                            .placeholder(R.mipmap.ic_default)
                                            .error(R.mipmap.ic_default)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                                    RequestOptions options1 = new RequestOptions()
                                            .centerInside()
                                            .placeholder(R.mipmap.ic_default_un)

                                            .error(R.mipmap.ic_default_un)

                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                                    RequestOptions options2 = new RequestOptions()
                                            .centerInside()
                                            .placeholder(R.mipmap.ic_default_head)
                                            .error(R.mipmap.ic_default_head)
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                                    Glide.with(IdentityUploadActivity.this).load(photo1).apply(options).into(fiv);
                                    Glide.with(IdentityUploadActivity.this).load(photo2).apply(options1).into(fivUn);
                                    Glide.with(IdentityUploadActivity.this).load(photo3).apply(options2).into(fivBack);

                                } else {
                                    Intent intent = new Intent(IdentityUploadActivity.this, Verify_PasswordActivity.class).putExtra("from", "IdentityUpload");
                                    startActivity(intent);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });


    }

    @OnClick({R.id.bt_takePhoto, R.id.bt_Picture, R.id.iv_back, R.id.bt_takePhoto_un, R.id.bt_Picture_un, R.id.bt_takePhoto_back, R.id.bt_Picture_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_takePhoto:
                takePhoto(IdentityInfo);
                break;
            case R.id.bt_Picture:
                takePicture(IdentityInfo);
                break;
            case R.id.bt_takePhoto_un:
                takePhoto(unInfo);

                break;
            case R.id.bt_Picture_un:
                takePicture(unInfo);

                break;
            case R.id.bt_takePhoto_back:
                takePhoto(backInfo);
                break;
            case R.id.bt_Picture_back:
                takePicture(backInfo);
                break;
            case R.id.iv_back:
                EventBus.getDefault().post(new PicStatusEvent("update"));
                finish();
                break;
        }
    }

    /**
     * 相册选取
     */
    private void takePicture(int identityInfo) {

        PictureSelector.create(IdentityUploadActivity.this)
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
                .compress(true)// 是否压缩
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
                .forResult(identityInfo);//结果回调onActivityResult code
    }

    /**
     * 相机拍照
     */

    private void takePhoto(int identityInfo) {

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
                .compress(true)// 是否压缩
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
                .forResult(identityInfo);//结果回调onActivityResult code

    }

    private static class SwitchHandler extends Handler {
        private WeakReference<IdentityUploadActivity> mWeakReference;

        SwitchHandler(IdentityUploadActivity activity) {
            mWeakReference = new WeakReference<IdentityUploadActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            IdentityUploadActivity activity = mWeakReference.get();
            String token = (String) SPUtils.get(activity, "token", "1");
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        UpLoadImage(activity, token, s, "0", positive_photo);
                        break;
                    case 2:
                        UpLoadImage(activity, token, s, "0", negative_photo);
                        break;
                    case 3:
                        UpLoadImage(activity, token, s, "0", am_photo);
                        break;


                }
            }
        }

        /**
         * 服务器七牛上传字段
         *
         * @param url
         */
        private void UpLoadImage(final Activity activity, String tolen, String url, String var, String photo) {
            Map<String, String> parms = new HashMap<>();
            parms.put("var", var);
            parms.put(photo, url);
            parms.put("token", tolen);
            // parms.put("signature",signature);
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
                                    ToastUtils.showToast(activity, msg);

                                } else {
                                    String msg = object.getString("msg");

                                    ToastUtils.showToast(activity, msg);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }

                        }
                    });

        }
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
                                } else {
                                    Intent intent = new Intent(IdentityUploadActivity.this, Verify_PasswordActivity.class).putExtra("from", "IdentityUpload");
                                    startActivity(intent);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                });


    }


    private List<LocalMedia> selectList = new ArrayList<>();

    // 例如 LocalMedia 里面返回三种path
    // 1.media.getPath(); 为原图path
    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            selectList = PictureSelector.obtainMultipleResult(data);
            final String path = selectList.get(selectList.size() - 1).getCompressPath();
            final RequestOptions options = new RequestOptions()
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            switch (requestCode) {
                case IdentityInfo:
                    // 图片选择结果回调
                    LogUtils.i("path---n", path);

                    UploadOptions uploadOptions = new UploadOptions(null, null, false,
                            new UpProgressHandler() {
                                @Override
                                public void progress(String key, double percent) {
                                    progress1.setVisibility(View.VISIBLE);
                                    progress1.setProgress((int)(percent*100));
                                    LogUtils.i("response", "key" + key + "----" + percent);
                                }
                            }, null);
                    AppApplication.getUploadManager().put(path, null, token, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info.isOK()) {
                                Log.i("qiniu", "Upload Success");
                                LogUtils.i("response", response.toString());
                                try {
                                    String key1 = response.getString("key");
                                    Message message = Message.obtain();
                                    message.obj = key1;
                                    message.what = 1;
                                    mHandler.sendMessage(message);
                                    progress1.setVisibility(View.GONE);
                                    Glide.with(IdentityUploadActivity.this).load(path).apply(options).into(fiv);
                                    LogUtils.i("onActivityResult", selectList.size());
                                    fiv.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            PictureSelector.create(IdentityUploadActivity.this).externalPicturePreview(selectList.size() - 1, selectList);
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                ToastUtils.showToast(IdentityUploadActivity.this, "上传失败");
                            }
                        }
                    }, uploadOptions);

                    break;
                case unInfo:
                    // 图片选择结果回调
                    LogUtils.i("path---n", path);

                    UploadOptions uploadOptions1 = new UploadOptions(null, null, false,
                            new UpProgressHandler() {
                                @Override
                                public void progress(String key, double percent) {
                                    progress2.setVisibility(View.VISIBLE);
                                    progress2.setProgress((int)(percent*100));

                                    LogUtils.i("response", "key" + key + "----" + percent);
                                }
                            }, null);
                    AppApplication.getUploadManager().put(path, null, token, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info.isOK()) {
                                Log.i("qiniu", "Upload Success");
                                LogUtils.i("response", response.toString());
                                try {
                                    String key1 = response.getString("key");
                                    progress2.setVisibility(View.GONE);

                                    Message message = Message.obtain();
                                    message.obj = key1;
                                    message.what = 2;
                                    mHandler.sendMessage(message);
                                    Glide.with(IdentityUploadActivity.this).load(path).apply(options).into(fivUn);
                                    fivUn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            PictureSelector.create(IdentityUploadActivity.this).externalPicturePreview(selectList.size() - 1, selectList);
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                ToastUtils.showToast(IdentityUploadActivity.this, "上传失败");
                            }
                        }
                    }, uploadOptions1);


                    break;
                case backInfo:
                    // 图片选择结果回调
                    LogUtils.i("path---n", path);

                    UploadOptions uploadOptions2 = new UploadOptions(null, null, false,
                            new UpProgressHandler() {
                                @Override
                                public void progress(String key, double percent) {
                                    LogUtils.i("response", "key" + key + "----" + percent);
                                    progress3.setVisibility(View.VISIBLE);
                                    progress3.setProgress((int)(percent*100));
                                }
                            }, null);
                    AppApplication.getUploadManager().put(path, null, token, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info.isOK()) {
                                Log.i("qiniu", "Upload Success");
                                LogUtils.i("response", response.toString());
                                try {
                                    String key1 = response.getString("key");
                                    progress3.setVisibility(View.GONE);
                                    Message message = Message.obtain();
                                    message.obj = key1;
                                    message.what = 3;
                                    mHandler.sendMessage(message);
                                    Glide.with(IdentityUploadActivity.this).load(path).apply(options).into(fivBack);
                                    fivBack.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            PictureSelector.create(IdentityUploadActivity.this).externalPicturePreview(selectList.size() - 1, selectList);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                ToastUtils.showToast(IdentityUploadActivity.this, "上传失败");
                            }
                        }
                    }, uploadOptions2);


                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        EventBus.getDefault().post(new PicStatusEvent("update"));
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
