package com.example.machenike.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.machenike.myapplication.a.AppManager;
import com.example.machenike.myapplication.a.BaseFragment;
import com.example.machenike.myapplication.a.FragmentParam;
import com.example.machenike.myapplication.a.JSONUtils;
import com.example.machenike.myapplication.a.PreferencesUtils;
import com.example.machenike.myapplication.a.StringUtils;
import com.example.machenike.myapplication.a.ToastUitl;
import com.example.machenike.myapplication.commonwidget.LoadingDialog;
import com.example.machenike.myapplication.commonwidget.StatusBarCompat;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public abstract class BaseActivity extends AutoLayoutActivity  {
    public Context mContext;
    private boolean isTwoBack;
    protected boolean hasAnimiation = true;

    private boolean isVertical = true;

    private Map<String, String> map;
    public String json;

    //com.mknl2345.pcdd12der

    // 昨天三个k2打了一个k2  对吧
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        x.view().inject(this);
        mContext = this;
        this.initPresenter();
        setStatus();
        this.initView();
        requestData();
    }

    public void setStatus() {
        String app_data = PreferencesUtils.getString(this, "app_data", "");
        if (TextUtils.isEmpty(app_data)) {
            json = StringUtils.getJson("app.json", this);
        } else {
            json = app_data;
        }
        map = JSONUtils.parseKeyAndValueToMap(JSONUtils.parseKeyAndValueToMap(json).get("app"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(map.get("status_bg")));
            if (map.get("status_text_color_type").equals("1")) {   //黑色
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(map.get("status_bg")));
            //底部导航栏
            //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }


    public void setIsVertical(boolean isVertical) {
        this.isVertical = isVertical;
    }

    private void doBeforeSetcontentView() {
        initTheme();
        AppManager.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (isVertical) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
//        mSetStatusBarColor();
//        SetStatusBarColor();
    }

    public abstract int getLayoutId();

    public abstract void initPresenter();

    public abstract void initView();

    public abstract void requestData();

    public void setBackTwo(boolean isTwoBack) {
        this.isTwoBack = isTwoBack;
    }

    private void initTheme() {
//        ChangeModeController.setTheme(this, R.style.DayTheme, R.style.NightTheme);
    }

    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }

    /*
     * *
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */

    public void mSetStatusBarColor() {
//        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.main_color2));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    public void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }


    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
//       overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public void showShortToast(String text) {
        ToastUitl.showShort(text);
    }

    public void showShortToast(int resId) {
        ToastUitl.showShort(resId);
    }

    public void showLongToast(int resId) {
        ToastUitl.showLong(resId);

    }

    public void showLongToast(String text) {
        ToastUitl.showLong(text);

    }

    public void showNetError() {
        ToastUitl.showShort(R.string.net_error);
    }

    void demo(List<String> strs) {
        List<? extends String> list; // ！！！在 Java 中不允许
    }

    private long firstTime;

    @Override
    public void onBackPressed() {
        if (isTwoBack) {
            if (System.currentTimeMillis() - firstTime < 3000) {
                hasAnimiation = false;
                AppManager.getInstance().AppExit(this);
            } else {

                firstTime = System.currentTimeMillis();
                showShortToast("再按一次返回桌面");
            }
        } else {
            super.onBackPressed();
        }
    }

    public void startActivity(String s1) {
        Intent intent = new Intent();
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ComponentName comp = new ComponentName(packageInfo.packageName, s1);
        intent.setComponent(comp);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
//        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void finish() {
        super.finish();
//        if (hasAnimiation) {
//            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
//        }
    }






    protected BaseFragment currentFragment;
    private List<BaseFragment> fragments;


    public void addFragment(Class<?> cls, Object data) {
        FragmentParam param = new FragmentParam();
        param.cls = cls;
        param.data = data;
        param.addToBackStack = false;
        this.processFragement(param);
    }

    public void addFragment(Class<?> cls, Object data, String tag) {
        FragmentParam param = new FragmentParam();
        param.cls = cls;
        param.data = data;
        param.tag = tag;
        param.addToBackStack = false;
        this.processFragement(param);
    }

    private void processFragement(FragmentParam param) {
        int containerId = this.getFragmentContainerId();
        Class cls = param.cls;
        if (cls != null) {
            try {
                String e = this.getFragmentTag(param);
                BaseFragment fragment = (BaseFragment) this.getSupportFragmentManager().findFragmentByTag(e);
                if (fragment == null) {
                    fragment = (BaseFragment) cls.newInstance();
                }
//                fragment.onComeIn(param.data);
                if (this.currentFragment != null) {
//                    this.currentFragment.onLeave();
                }

                if (this.fragments == null) {
                    this.fragments = new ArrayList();
                }
                addDistinctEntry(this.fragments, fragment);
                FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
                if (param.type != FragmentParam.TYPE.ADD) {
                    ft.replace(containerId, fragment, e);
                } else if (!fragment.isAdded()) {
                    ft.add(containerId, fragment, e);
                } else {
                    Iterator var7 = this.fragments.iterator();
                    while (var7.hasNext()) {
                        BaseFragment lastFragment = (BaseFragment) var7.next();
                        ft.hide(lastFragment);
                    }
                    if (this.currentFragment != null) {
                        this.currentFragment.onPause();
                    }
                    ft.show(fragment);

                    fragment.onResume();
                }
                this.currentFragment = fragment;
                if (param.addToBackStack) {
                    ft.addToBackStack(e);
                }

                ft.commitAllowingStateLoss();
            } catch (InstantiationException var9) {
                var9.printStackTrace();
            } catch (IllegalAccessException var10) {
                var10.printStackTrace();
            }

        }
    }

    public static <V> boolean addDistinctEntry(List<V> sourceList, V entry) {
        return (sourceList != null && !sourceList.contains(entry)) && sourceList.add(entry);
    }

    protected int getFragmentContainerId() {
        return 0;
    }

    protected String getFragmentTag(FragmentParam param) {
        StringBuilder sb = new StringBuilder(param.cls.toString() + param.tag);
        return sb.toString();
    }

}
