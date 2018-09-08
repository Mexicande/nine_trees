package cn.com.laifenqicash.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import cn.com.laifenqicash.AppApplication;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : App相关工具类
 * </pre>
 */
public final class AppUtils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    static WeakReference<Activity> sTopActivityWeakRef;
    static List<Activity> sActivityList = new LinkedList<>();

    private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            sActivityList.add(activity);
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);
        }
    };


    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param context context
     */
    public static void init(@NonNull final Context context) {
        AppUtils.sApplication = ((Application) context.getApplicationContext());
        AppUtils.sApplication.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    public static Application getApp() {
        if (sApplication != null) {
            return sApplication;
        }
        throw new NullPointerException("u should init first");
    }

    private static void setTopActivityWeakRef(final Activity activity) {
        if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
            sTopActivityWeakRef = new WeakReference<>(activity);
        }
    }

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return whether the app is installed.
     *
     * @param action   The Intent action, such as ACTION_VIEW.
     * @param category The desired category.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isInstallApp(final String action, final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = AppApplication.getApp().getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }


    /**
     * 获取 App 包名
     *
     * @return App 包名
     */
    public static String getAppPackageName() {
        return AppApplication.getApp().getPackageName();
    }


    /**
     * 获取 App 名称
     *
     * @return App 名称
     */
    public static String getAppName() {
        return getAppName(AppApplication.getApp().getPackageName());
    }

    /**
     * 获取 App 名称
     *
     * @param packageName 包名
     * @return App 名称
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = AppApplication.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 图标
     *
     * @return App 图标
     */
    public static Drawable getAppIcon() {
        return getAppIcon(AppApplication.getApp().getPackageName());
    }

    /**
     * 获取 App 图标
     *
     * @param packageName 包名
     * @return App 图标
     */
    private static Drawable getAppIcon(final String packageName) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = AppApplication.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 路径
     *
     * @return App 路径
     */
    public static String getAppPath() {
        return getAppPath(AppApplication.getApp().getPackageName());
    }

    /**
     * 获取 App 路径
     *
     * @param packageName 包名
     * @return App 路径
     */
    public static String getAppPath(final String packageName) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = AppApplication.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 版本号
     *
     * @return App 版本号
     */
    public static String getAppVersionName() {
        return getAppVersionName(AppApplication.getApp().getPackageName());
    }

    /**
     * 获取 App 版本号
     *
     * @param packageName 包名
     * @return App 版本号
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = AppApplication.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 App 版本码
     *
     * @return App 版本码
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(AppApplication.getApp().getPackageName());
    }

    /**
     * 获取 App 版本码
     *
     * @param packageName 包名
     * @return App 版本码
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName)) {
            return -1;
        }
        try {
            PackageManager pm = AppApplication.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp(final String packageName) {
        if (isSpace(packageName)) {
            return false;
        }
        try {
            PackageManager pm = AppApplication.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断 App 是否是 Debug 版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        return isAppDebug(AppApplication.getApp().getPackageName());
    }

    /**
     * 判断 App 是否是 Debug 版本
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName)) {
            return false;
        }
        try {
            PackageManager pm = AppApplication.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断 App 是否处于前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground() {
        ActivityManager manager =
                (ActivityManager) AppApplication.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> info = manager != null ? manager.getRunningAppProcesses() : null;
        if (info == null || info.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(AppApplication.getApp().getPackageName());
            }
        }
        return false;
    }


    /**
     * 封装 App 信息的 Bean 类
     */
    public static class AppInfo {

        private String name;
        private Drawable icon;
        private String packageName;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(final int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        /**
         * @param name        名称
         * @param icon        图标
         * @param packageName 包名
         * @param packagePath 包路径
         * @param versionName 版本号
         * @param versionCode 版本码
         * @param isSystem    是否系统应用
         */
        public AppInfo(String packageName, String name, Drawable icon, String packagePath,
                       String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        @Override
        public String toString() {
            return "pkg name: " + getPackageName() +
                    "\napp name: " + getName() +
                    "\napp path: " + getPackagePath() +
                    "\napp v name: " + getVersionName() +
                    "\napp v code: " + getVersionCode() +
                    "\nis system: " + isSystem();
        }
    }

    /**
     * 获取 App 信息
     * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
     *
     * @return 当前应用的 AppInfo
     */
    public static AppInfo getAppInfo() {
        return getAppInfo(AppApplication.getApp().getPackageName());
    }

    /**
     * 获取 App 信息
     * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
     *
     * @param packageName 包名
     * @return 当前应用的 AppInfo
     */
    public static AppInfo getAppInfo(final String packageName) {
        try {
            PackageManager pm = AppApplication.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到 AppInfo 的 Bean
     *
     * @param pm 包的管理
     * @param pi 包的信息
     * @return AppInfo 类
     */
    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pm == null || pi == null) {
            return null;
        }
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }


    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int[] getScreenSize(Activity activity) {
        int[] a = new int[2];
        WindowManager wm = activity.getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        a[0] = displayMetrics.widthPixels;
        a[1] = displayMetrics.heightPixels;
        return a;
    }

    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    public static String getSDKVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getPhone(Context mContext) {

        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String str = "";
        if (tm != null) {
            str = tm.getLine1Number();
        }
        if(str!=null&&str.length()>11){
            str = str.substring(str.length() - 11, str.length());
        }
        return str;
    }

}