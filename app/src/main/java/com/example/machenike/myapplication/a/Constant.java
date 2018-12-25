package com.example.machenike.myapplication.a;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.webkit.WebView;

/**
 * Created by wanghao on 2017/3/17.
 */
public class Constant {
    public static int WIDTH_SCREEN; //屏幕宽度
    public static int HEIGHT_SCREEN; //屏幕高度
    public static String app_type = "";
    public static String is_first_login = "";


    public static WebView webView1;
    public static WebView webView2;
    public static WebView webView3;
    public static WebView webView4;
    public static WebView webView5;
    public static WebView webView6;
    public static WebView webView7;
    public static WebView webView8;
    public static WebView webView9;
    public static String url = "";
    public static String title = "";
    public static int index = 0;

    public static final String SP_DOWNLOAD_PATH = "download.path";
    public static final String SP_NAME = "Phoenix";
    public static final String MyWorkRewardFrg = "MyWorkRewardFrg";
    public static final String uninstall_packname = "com.yunji.app.w119";
    public static final String uninstall_packname2 = "com.yibo.app.d396";

    public static void obtainScreenParams(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constant.WIDTH_SCREEN = metrics.widthPixels;
        Constant.HEIGHT_SCREEN = metrics.heightPixels;
    }
}
