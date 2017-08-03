package cn.com.stableloan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.coorchice.library.SuperTextView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.com.stableloan.R;
import cn.com.stableloan.manager.CameraManager;
import cn.com.stableloan.model.CardBean;
import cn.com.stableloan.model.InputBean;
import cn.com.stableloan.view.PreviewBorderView;
import okhttp3.Call;
import okhttp3.Response;



public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private RelativeLayout mLinearLayout;
    private PreviewBorderView mPreviewBorderView;
    private SurfaceView mSurfaceView;

    private CameraManager cameraManager;
    private boolean hasSurface;
    private Intent mIntent;
    private static final String DEFAULT_PATH = "/sdcard/";
    private static final String DEFAULT_NAME = "default.jpg";
    private static final String DEFAULT_TYPE = "default";
    private String filePath;
    private String fileName;
    private String type;
    private SuperTextView take, light;
    private boolean toggleLight;
    public static void launch(Context context) {
        context.startActivity(new Intent(context, CameraActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        initIntent();
        initLayoutParams();
    }

    private void initIntent() {
        mIntent = getIntent();
        filePath = mIntent.getStringExtra("path");
        fileName = mIntent.getStringExtra("name");
        type = mIntent.getStringExtra("type");
        if (filePath == null) {
            filePath = DEFAULT_PATH;
        }
        if (fileName == null) {
            fileName = DEFAULT_NAME;
        }
        if (type == null) {
            type = DEFAULT_TYPE;
        }
        Log.e("TAG", filePath + "/" + fileName + "_" + type);
    }

    /**
     * 重置surface宽高比例为3:4，不重置的话图形会拉伸变形
     */
    private void initLayoutParams() {
        take = (SuperTextView) findViewById(R.id.take);
       // light = (Button) findViewById(R.id.light);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraManager.takePicture(null, null, myjpegCallback);
            }
        });
      /*  light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!toggleLight) {
                    toggleLight = true;
                    cameraManager.openLight();
                } else {
                    toggleLight = false;
                    cameraManager.offLight();
                }
            }
        });*/

        //重置宽高，3:4
        int widthPixels = getScreenWidth(this);
        int heightPixels = getScreenHeight(this);
        mLinearLayout = (RelativeLayout) findViewById(R.id.linearlaout);
        mPreviewBorderView = (PreviewBorderView) findViewById(R.id.borderview);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);


        RelativeLayout.LayoutParams surfaceviewParams = (RelativeLayout.LayoutParams) mSurfaceView.getLayoutParams();
        surfaceviewParams.width = heightPixels * 4 / 3;
        surfaceviewParams.height = heightPixels;
        mSurfaceView.setLayoutParams(surfaceviewParams);

        RelativeLayout.LayoutParams borderViewParams = (RelativeLayout.LayoutParams) mPreviewBorderView.getLayoutParams();
        borderViewParams.width = heightPixels * 4 / 3;
        borderViewParams.height = heightPixels;
        mPreviewBorderView.setLayoutParams(borderViewParams);

        RelativeLayout.LayoutParams linearLayoutParams = (RelativeLayout.LayoutParams) mLinearLayout.getLayoutParams();
        linearLayoutParams.width = widthPixels - heightPixels * 4 / 3;
        linearLayoutParams.height = heightPixels;
        mLinearLayout.setLayoutParams(linearLayoutParams);


        Log.e("TAG", "Screen width:" + heightPixels * 4 / 3);
        Log.e("TAG", "Screen height:" + heightPixels);

    }


    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 初始化camera
         */
        cameraManager = new CameraManager();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();

        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    /**
     * 初始camera
     *
     * @param surfaceHolder SurfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            cameraManager.startPreview();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        /**
         * 停止camera，是否资源操作
         */
        cameraManager.stopPreview();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    /**
     * 拍照回调
     */
    Camera.PictureCallback myjpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            // 根据拍照所得的数据创建位图
            final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
                    data.length);
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();
            final Bitmap bitmap1 = Bitmap.createBitmap(bitmap, (width - height) / 2, height / 6, height, height * 2 / 3);
            Log.e("TAG", "width:" + width + " height:" + height);
            Log.e("TAG", "x:" + (width - height) / 2 + " y:" + height / 6 + " width:" + height + " height:" + height * 2 / 3);
            // 创建一个位于SD卡上的文件

            File path = new File(filePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(path, type + "_" + fileName);

            FileOutputStream outStream = null;
            try {
                // 打开指定文件对应的输出流
                outStream = new FileOutputStream(file);
                // 把位图输出到指定文件中
                bitmap1.compress(Bitmap.CompressFormat.JPEG,
                        100, outStream);
                outStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;

            Bitmap bitmap3 = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            String base64 = bitmapToBase64(bitmap3);

            uploadAndRecognize(base64);
/*
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("path", file.getAbsolutePath());
            bundle.putString("type", type);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            CameraActivity.this.finish();*/

        }
    };

    /**
     * BitMap换位base64
     * @param bitmap
     * @return
     */
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

    private void uploadAndRecognize(String base64) {
      /*  hud = KProgressHUD.create(CameraActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("识别中,请稍后.....")
                .setCancellable(true)
                .show();*/
        InputBean.InputsBean.ImageBean imageBean = new InputBean.InputsBean.ImageBean();
        imageBean.setDataType(50);
        imageBean.setDataValue(base64);
        InputBean.InputsBean.ConfigureBean.DataValueBean dataValueBean = new InputBean.InputsBean.ConfigureBean.DataValueBean();
        dataValueBean.setSide("face");
        InputBean.InputsBean.ConfigureBean configureBean = new InputBean.InputsBean.ConfigureBean();
        configureBean.setDataType(50);
        configureBean.setDataValue("{\"side\":\"face\"}");
        InputBean.InputsBean bean = new InputBean.InputsBean();
        bean.setImage(imageBean);
        bean.setConfigure(configureBean);
        List<InputBean.InputsBean> list = new ArrayList<>();
        list.add(bean);
        InputBean inputBean = new InputBean();
        inputBean.setInputs(list);
        Gson gson = new Gson();
        String json = gson.toJson(inputBean);
        OkGo.<String>post("https://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json")
                .tag(this)
                .headers("Authorization", "APPCODE " + "a37dbd4d651b43c2a7e0c56b3f842b74")
                .headers("Content-Type", "application/json; charset=UTF-8")
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        String s1 = s.replace("\"{", "{");
                        String s2 = s1.replace("\\", "");
                        int face = s2.indexOf("side");
                        String substring = s2.substring(0, face + 13);
                        String substring1 = s2.substring(face + 14, s2.length());
                        String s3 = substring + substring1;
                        String replace = s3.replace("\"}}]}", "}}]}");
                        Gson gson1 = new Gson();
                        CardBean cardBean = null;
                        try {
                            cardBean = gson1.fromJson(replace, CardBean.class);
                            Log.i("dataValue---", cardBean.toString());

                            CardBean.OutputsBean.OutputValueBean.DataValueBean value = cardBean.getOutputs().get(0).getOutputValue().getDataValue();

                            boolean success = value.isSuccess();
                            if(success){
                                File path = new File(filePath);
                                if (!path.exists()) {
                                    path.mkdirs();
                                }
                                File file = new File(path, type + "_" + fileName);

                                Bundle bundle=new Bundle();

                                bundle.putSerializable("carmera",value);
                                bundle.putString("path",file.getAbsolutePath());
                                startActivity(new Intent(CameraActivity.this,CarmeraResultActivity.class).putExtra("bundle",bundle));

                            }else {

                            }
                           // mTextView.setText(value.getName() + "\n" + value.getSex() + "\n" + value.getNationality() + "\n" + value.getBirth() + "\n" + value.getNum() + "\n" + value.getAddress());
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }
                });
    }
    /**
     * 获得屏幕宽度，单位px
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }


    /**
     * 获得屏幕高度
     *
     * @param context 上下文
     * @return 屏幕除去通知栏的高度
     */
    public int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取通知栏高度
     *
     * @param context 上下文
     * @return 通知栏高度
     */
    public int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            int temp = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }
}
