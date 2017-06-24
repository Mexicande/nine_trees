# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/apple/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}




-keep class de.greenrobot.dao.** { *; }
-keep class com.squareup.picasso.** { *; }
-keep class com.nostra13.universalimageloader.** { *; }
-keep class com.android.volley.** { *; }

-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

#-keep class com.android.volley.** {*;}
#-keep class com.android.volley.toolbox.** {*;}
#-keep class com.android.volley.Response$* { *; }
#-keep class com.android.volley.Request$* { *; }
#-keep class com.android.volley.RequestQueue$* { *; }
#-keep class com.android.volley.toolbox.HurlStack$* { *; }
#-keep class com.android.volley.toolbox.ImageLoader$* { *; }


-dontwarn com.jeremyfeinstein.slidingmenu.lib.**
-keep class com.jeremyfeinstein.slidingmenu.lib.**{*;}

-dontwarn com.squareup.okhttp.**


-keepattributes SourceFile,LineNumberTable
-keep class com.parse.*{ *; }
-dontwarn com.parse.**
#-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}
#-dontwarn com.nostra13.universalimageloader.**

#-keep class com.nostra13.universalimageloader.** { *; }

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
#指定压缩级别
-optimizationpasses 5

-dontusemixedcaseclassnames  #是否使用大小写混合


#混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#把混淆类中的方法名也混淆了
-useuniqueclassmembernames

#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification

# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers

#保留行号
-keepattributes SourceFile,LineNumberTable

-dontoptimize
-dontpreverify
-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

#保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keep public class * extends android.app.Activity  #所有activity的子类不要去混淆
-keep public class * extends android.app.Application

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application

-keep public class **.*Model*.** {*;}
#保持所有拥有本地方法的类名及本地方法名
-keepclasseswithmembernames class * {
    native <methods>;
}

#保持自定义View的get和set相关方法
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

#保持Activity中View及其子类入参的方法
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keep class com.null.test.entities.** {
    public void set*(***);
    public *** get*();
    public *** is*();
}

#枚举
-keepclassmembers enum * {
    **[] $VALUES;
    public *;
}

#Parcelable
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

#R文件的静态成员
-keepclassmembers class **.R$* {
    public static <fields>;
}

-dontwarn android.support.**

#keep相关注解
-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}
-keep class com.example.guolin.androidtest.MyFragment {
    *;
}
-keepclassmembers class com.example.guolin.androidtest.Utils {
    public void methodUnused();
}
-keep class org.litepal.** {
    *;
}

-keep class android.support.** {
    *;
}
-dontwarn rx.internal.util.unsafe.*
-dontwarn com.google.gson.**

-keep class sun.misc.Unsafe { *; }


-keep class com.google.gson.JsonObject { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.** {
    <fields>;
    <methods>;
}
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}
-keep class com.google.gson.** {*;}

-keep class com.badlogic.** { *; }

-keep class * implements com.badlogic.gdx.utils.Json*

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);  #保持自定义控件类不被混淆，指定格式的构造方法不去混淆
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View); #保持指定规则的方法不被混淆（Android layout 布局文件中为控件配置的onClick方法不能混淆）
}

-keep public class * extends android.view.View {  #保持自定义控件指定规则的方法不被混淆
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
-keep public class * extends android.app.Service

-keepnames class * implements java.io.Serializable #需要序列化和反序列化的类不能被混淆（注：Java反射用到的类也不能被混淆）
-keep class **.Webview2JsInterface { *; }  #保护WebView对HTML页面的API不被混淆
-keepclassmembers class * extends android.webkit.WebViewClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
     public boolean *(android.webkit.WebView,java.lang.String);
}
-keepclassmembernames class com.cgv.cn.movie.common.bean.** { *; }  #转换JSON的JavaBean，类成员名称保护，使其不被混淆
-keep class com.chad.library.adapter.** {
   *;
}
-keep class com.jph.android.entity.** { *; } #实体类不参与混淆

-dontwarn com.google.gson.**
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }