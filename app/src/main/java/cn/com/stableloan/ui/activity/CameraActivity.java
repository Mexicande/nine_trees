package cn.com.stableloan.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.stableloan.AppApplication;
import cn.com.stableloan.R;
import cn.com.stableloan.api.Urls;
import cn.com.stableloan.model.CardBean;
import cn.com.stableloan.model.InputBean;
import cn.com.stableloan.utils.BitmapUtils;
import cn.com.stableloan.utils.CameraUtils;
import cn.com.stableloan.utils.LogUtils;
import cn.com.stableloan.utils.SPUtil;
import cn.com.stableloan.utils.ToastUtils;
import cn.com.stableloan.view.CameraPreview;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Camera API. Android KitKat 及以前版本的 Android 使用 Camera API.
 */
@SuppressWarnings("deprecation")
public class CameraActivity extends AutoLayoutActivity {
    public static final int DEBIT_CODE = 4;
    public static final int CREDIT_CODE = 5;
    File mFile;
    Camera mCamera;
    CameraPreview mPreview;
    long mMaxPicturePixels;

    /**
     * 预览的最佳尺寸是否已找到
     */
    volatile boolean mPreviewBestFound;

    /**
     * 拍照的最佳尺寸是否已找到
     */
    volatile boolean mPictureBestFound;

    /**
     * finish()是否已调用过
     */
    volatile boolean mFinishCalled;
    @Bind(R.id.fl_camera_preview)
    FrameLayout flCameraPreview;
    @Bind(R.id.view_camera_dark0)
    View viewCameraDark0;
    @Bind(R.id.tv_camera_hint)
    TextView tvCameraHint;
    @Bind(R.id.view_camera_dark1)
    LinearLayout viewCameraDark1;
    @Bind(R.id.iv_camera_button)
    ImageView ivCameraButton;
    private  File file1;

    private void preInitData() {

        mFile = new File(getIntent().getStringExtra("file"));
        tvCameraHint.setText(getIntent().getStringExtra("hint"));
        if (getIntent().getBooleanExtra("hideBounds", false)) {
            viewCameraDark0.setVisibility(View.INVISIBLE);
            viewCameraDark1.setVisibility(View.INVISIBLE);
        }
        mMaxPicturePixels = getIntent().getIntExtra("maxPicturePixels", 3840 * 2160);
        initCamera();
        RxView.clicks(ivCameraButton)
                //防止手抖连续多次点击造成错误
                .throttleFirst(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    if (mCamera == null) {
                        return;
                    }
                    mCamera.takePicture(null, null, (data, camera) -> Flowable
                            .create((FlowableOnSubscribe<Integer>) emitter -> {
                                try {
                                    if (mFile.exists()) {
                                        mFile.delete();
                                    }
                                    FileOutputStream fos = new FileOutputStream(mFile);
                                    fos.write(data);
                                    try {
                                        fos.close();
                                    } catch (Exception ignored) {
                                    }
                                    emitter.onNext(200);
                                } catch (Exception e) {
                                    emitter.onError(e);
                                }
                            }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(integer -> {

                                file1=new File(mFile.toString());

                                int type = getIntent().getIntExtra("type", 0);
                                file1=new File(mFile.toString());
                                if(type!=0){
                                    Flowable.just(file1)
                                            //将File解码为Bitmap
                                            .map(file -> BitmapUtils.compressToResolution(file, 1920 * 1080))
                                            //裁剪Bitmap
                                            .map(BitmapUtils::crop)
                                            //将Bitmap写入文件
                                            .map(bitmap -> BitmapUtils.writeBitmapToFile(bitmap, "mFile"))
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(file -> {
                                                file1 = file;
                                                Log.i("file-----",file1.getAbsolutePath());
                                                AppApplication.mHandler.post(() -> {
                                                    mFinishCalled = true;
                                                    String camera2 = SPUtil.getString(CameraActivity.this, "camera");
                                                    if(camera2==null||("1").equals(camera2)){
                                                        SPUtil.putString(CameraActivity.this,"camera1","ok");
                                                        bankPhoto(file1.getAbsolutePath(),type);
                                                    }
                                                });
                                            });

                                }else {
                                    Flowable.just(file1)
                                            //将File解码为Bitmap
                                            .map(file -> BitmapUtils.compressToResolution(file, 1920 * 1080))
                                            //裁剪Bitmap
                                            .map(BitmapUtils::crop)
                                            //将Bitmap写入文件
                                            .map(bitmap -> BitmapUtils.writeBitmapToFile(bitmap, "mFile"))
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(file -> {
                                                file1 = file;
                                                Log.i("file-----",file1.getAbsolutePath());
                                                AppApplication.mHandler.post(() -> {
                                                    mFinishCalled = true;
                                                    String camera2 = SPUtil.getString(CameraActivity.this, "camera1" );

                                                    if(camera2==null||("1").equals(camera2)){
                                                        SPUtil.putString(CameraActivity.this,"camera1","ok");
                                                        identifyPhoto(file1.getAbsolutePath());
                                                    }
                                                });
                                                //清除该Uri的Fresco缓存. 若不清除，对于相同文件名的图片，Fresco会直接使用缓存而使得Drawee得不到更新.
                                            });
                                }
                              //  setResult(integer, getIntent().putExtra("file", mFile.toString()));

                               /* mFinishCalled = true;
                                finish();*/
                            }, throwable -> {
                                throwable.printStackTrace();
                                mCamera.startPreview();
                            }));
                });
    }

    private void bankPhoto(String path,int type) {

        hd .show();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        bitmap = BitmapFactory.decodeFile(path, options);
        String base64 = bitmapToBase64(bitmap);
        InputBean.InputsBean.ImageBean imageBean= new InputBean.InputsBean.ImageBean();
        imageBean.setDataType(50);
        imageBean.setDataValue(base64);
        InputBean.InputsBean bean=new InputBean.InputsBean();
        bean.setImage(imageBean);
        List<InputBean.InputsBean>list=new ArrayList<>();
        list.add(bean);
        InputBean inputBean=new InputBean();
        inputBean.setInputs(list);
        Gson gson=new Gson();
        String json = gson.toJson(inputBean);
        OkGo.<String>post("http://yhk.market.alicloudapi.com/rest/160601/ocr/ocr_bank_card.json")
                .tag(this)
                .headers("Authorization","APPCODE "+"652e57e76e014140a1fc93e0b1e6e6f9")
                .headers("Content-Type", "application/json; charset=UTF-8")
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        hd.dismiss();
                        String s1 = s.replace("\"{", "{");
                        String s2 = s1.replace("\\", "");
                        String s3 = s2.replace("}\"", "}");
                        Gson gson1=new Gson();
                        CardBean cardBean = gson1.fromJson(s3, CardBean.class);
                        CardBean.OutputsBean.OutputValueBean.DataValueBean value = cardBean.getOutputs().get(0).getOutputValue().getDataValue();
                        if(cardBean.getOutputs().get(0).getOutputValue().getDataValue().isSuccess()){
                            SPUtil.remove(CameraActivity.this,"camera1");
                            upImageBank(path,value.getCard_num());

                        }else {
                            ToastUtils.showToast(CameraActivity.this,"解析失败,请重新扫描");
                            SPUtil.remove(CameraActivity.this,"camera1");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showToast(CameraActivity.this,"解析失败,请重新扫描");
                        SPUtil.remove(CameraActivity.this,"camera1");
                        hd.dismiss();
                    }
                });


    }


    private  void upImageBank( String path,String num){
        hd.show();
        String   userToken = SPUtil.getString(this, Urls.lock.TOKEN);
        Map<String, String> parms = new HashMap<>();
        parms.put("token", userToken);
        parms.put("signature", "");
        parms.put("source", "");
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
                                    String qiNiuToken = object.getString("token");
                                    savePicture(path,qiNiuToken,num);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });


    }

    private void savePicture( String path, String qiNiuToken,String num) {
        UploadOptions uploadOptions1 = new UploadOptions(null, null, false,
                new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {

                        LogUtils.i("response", "key" + key + "----" + percent);
                    }
                }, null);
        AppApplication.getUploadManager().put(path, null, qiNiuToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    try {
                        String key1 = response.getString("key");

                        int type = getIntent().getIntExtra("type", 0);
                        String token = SPUtil.getString(CameraActivity.this,Urls.lock.TOKEN, "1");
                        switch (type) {
                            case DEBIT_CODE:
                                UpLoadImage(token, key1,String.valueOf(DEBIT_CODE), "debit_photo",num);
                                break;
                            case CREDIT_CODE:
                                UpLoadImage(token, key1,String.valueOf(CREDIT_CODE), "credite_photo",num);
                                break;
                        }


                        hd.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    hd.dismiss();
                    ToastUtils.showToast(CameraActivity.this, "保存失败,请重新尝试");
                }
            }
        }, uploadOptions1);
    }

    private void UpLoadImage(String tolen, String url, String var, String photo,String num) {
        Map<String, String> parms = new HashMap<>();
        parms.put(photo, url);
        parms.put("type", var);
        parms.put("token",tolen);
        parms.put("source", "");
        JSONObject jsonObject = new JSONObject(parms);
        OkGo.<String>post(Urls.Ip_url + Urls.Pictrue.UpLoadImage)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int error_code = object.getInt("error_code");
                            if (error_code==0) {
                                String data = object.getString("data");
                                JSONObject json=new JSONObject(data);
                                String msg = json.getString("msg");
                                /*    if (flag) {
                                        EventBus.getDefault().post(new CameraEvent(value));
                                    }*/
                                Intent intent=new Intent();
                                intent.putExtra("card_num",num);
                                setResult(Integer.parseInt(var),intent);
                                finish();

                            } else {
                                String msg = object.getString("error_message");
                                ToastUtils.showToast(CameraActivity.this, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                });

    }
    private Bitmap bitmap;
    private KProgressHUD hd;
    private void identifyPhoto(String path) {
        hd .show();
        String app_code = getIntent().getStringExtra("app_code");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        bitmap = BitmapFactory.decodeFile(path, options);
        String base64 = bitmapToBase64(bitmap);
        InputBean.InputsBean.ImageBean imageBean= new InputBean.InputsBean.ImageBean();
        imageBean.setDataType(50);
        imageBean.setDataValue(base64);
        InputBean.InputsBean.ConfigureBean.DataValueBean dataValueBean=new InputBean.InputsBean.ConfigureBean.DataValueBean();
        dataValueBean.setSide("face");
        InputBean.InputsBean.ConfigureBean configureBean=new InputBean.InputsBean.ConfigureBean();
        configureBean.setDataType(50);
        configureBean.setDataValue("{\"side\":\"face\"}");
        InputBean.InputsBean bean=new InputBean.InputsBean();
        bean.setImage(imageBean);
        bean.setConfigure(configureBean);
        List<InputBean.InputsBean>list=new ArrayList<>();
        list.add(bean);
        InputBean inputBean=new InputBean();
        inputBean.setInputs(list);
        Gson gson=new Gson();
        String json = gson.toJson(inputBean);
        OkGo.<String>post("https://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json")
                .tag(this)
                .headers("Authorization","APPCODE "+app_code)
                .headers("Content-Type", "application/json; charset=UTF-8")
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        hd.dismiss();
                        String s1 = s.replace("\"{", "{");
                        String s2 = s1.replace("\\", "");
                        int face = s2.indexOf("side");
                        String substring = s2.substring(0, face + 13);
                        String substring1 = s2.substring(face + 14, s2.length());
                        String s3=substring+substring1;
                        String replace = s3.replace("\"}}]}", "}}]}");
                        Gson gson1=new Gson();
                        CardBean cardBean = null;
                        cardBean = gson1.fromJson(replace, CardBean.class);
                        Log.i("dataValue---",cardBean.toString());
                        CardBean.OutputsBean.OutputValueBean.DataValueBean value = cardBean.getOutputs().get(0).getOutputValue().getDataValue();
                        if(cardBean.getOutputs().get(0).getOutputValue().getDataValue().isSuccess()){
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("carmera",value);
                            bundle.putString("path",path);
                            SPUtil.remove(CameraActivity.this,"camera1");
                            startActivity(new Intent(CameraActivity.this,CarmeraResultActivity.class).putExtra("bundle",bundle));
                            mFinishCalled = true;
                            finish();
                        }else {
                            ToastUtils.showToast(CameraActivity.this,"解析失败,请重新扫描");
                            SPUtil.remove(CameraActivity.this,"camera1");
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                                ToastUtils.showToast(CameraActivity.this,"解析失败,请重新扫描");
                                SPUtil.remove(CameraActivity.this,"camera1");
                                hd.dismiss();
                        finish();
                    }
                });

    }
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    void initCamera() {
        Flowable.create(CameraUtils.getCameraOnSubscribe(), BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(camera -> {
                    mCamera = camera;
                    mPreview = new CameraPreview(CameraActivity.this, mCamera, (throwable, showToast) -> {
                        if (showToast)
                            Toast.makeText(this, "开启相机预览失败，再试一次吧", Toast.LENGTH_LONG).show();
                        mFinishCalled = true;
                        finish();
                    });
                    flCameraPreview.addView(mPreview);
                    initParams();
                }, t -> {
                    t.printStackTrace();
                    Toast.makeText(this, "相机开启失败，再试一次吧", Toast.LENGTH_LONG).show();
                    mFinishCalled = true;
                    finish();
                });
    }

    void initParams() {
        Camera.Parameters params = mCamera.getParameters();
        //若相机支持自动开启/关闭闪光灯，则使用. 否则闪光灯总是关闭的.
        List<String> flashModes = params.getSupportedFlashModes();
        if (flashModes != null && flashModes.contains(Camera.Parameters.FLASH_MODE_AUTO))
            params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        mPreviewBestFound = false;
        mPictureBestFound = false;
        //寻找最佳预览尺寸，即满足16:9比例，且不超过1920x1080的最大尺寸;若找不到，则使用满足16:9的最大尺寸.
        //若仍找不到，使用最大尺寸;详见CameraUtils.findBestSize方法.
        CameraUtils previewUtils = new CameraUtils();
        List<Camera.Size> previewSizes = params.getSupportedPreviewSizes();
        previewUtils.findBestSize(false, previewSizes, previewUtils.new OnBestSizeFoundCallback() {
            @Override
            public void onBestSizeFound(Camera.Size size) {
                mPreviewBestFound = true;
                params.setPreviewSize(size.width, size.height);
                if (mPictureBestFound) initFocusParams(params);
            }
        }, 1920 * 1080);
        //寻找最佳拍照尺寸，即满足16:9比例，且不超过maxPicturePixels指定的像素数的最大Size;若找不到，则使用满足16:9的最大尺寸.
        //若仍找不到，使用最大尺寸;详见CameraUtils.findBestSize方法.
        CameraUtils pictureUtils = new CameraUtils();
        List<Camera.Size> pictureSizes = params.getSupportedPictureSizes();
        pictureUtils.findBestSize(true, pictureSizes, pictureUtils.new OnBestSizeFoundCallback() {
            @Override
            public void onBestSizeFound(Camera.Size size) {
                mPictureBestFound = true;
                params.setPictureSize(size.width, size.height);
                if (mPreviewBestFound) initFocusParams(params);
            }
        }, mMaxPicturePixels);
    }

    void initFocusParams(Camera.Parameters params) {
        //若支持连续对焦模式，则使用.
        List<String> focusModes = params.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            setParameters(params);
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            //进到这里，说明不支持连续对焦模式，退回到点击屏幕进行一次自动对焦.
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            setParameters(params);
            //点击屏幕进行一次自动对焦.
            flCameraPreview.setOnClickListener(v -> CameraUtils.autoFocus(mCamera));
            //4秒后进行第一次自动对焦，之后每隔8秒进行一次自动对焦.
            Flowable.timer(4, TimeUnit.SECONDS)
                    .flatMap(aLong -> {
                        CameraUtils.autoFocus(mCamera);
                        return Flowable.interval(8, TimeUnit.SECONDS);
                    }).subscribe(aLong -> CameraUtils.autoFocus(mCamera));
        }
    }

    void setParameters(Camera.Parameters params) {
        try {
            mCamera.setParameters(params);
        } catch (Exception e) {
            //非常罕见的情况
            //个别机型在SupportPreviewSizes里汇报了支持某种预览尺寸，但实际是不支持的，设置进去就会抛出RuntimeException.
            e.printStackTrace();
            try {
                //遇到上面所说的情况，只能设置一个最小的预览尺寸
                params.setPreviewSize(1920, 1080);
                mCamera.setParameters(params);
            } catch (Exception e1) {
                //到这里还有问题，就是拍照尺寸的锅了，同样只能设置一个最小的拍照尺寸
                e1.printStackTrace();
                try {
                    params.setPictureSize(1920, 1080);
                    mCamera.setParameters(params);
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        mFinishCalled = true;
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mCamera.stopPreview();
            mCamera.setPreviewDisplay(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mCamera.release();
            mCamera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!mFinishCalled) finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        hd = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("识别中,请稍后...")
                .setDimAmount(0.5f);
        preInitData();
    }

    @OnClick(R.id.iv_camera_off)
    public void onViewClicked() {
        finish();
    }
}
