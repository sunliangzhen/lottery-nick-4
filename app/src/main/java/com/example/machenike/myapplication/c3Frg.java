package com.example.machenike.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.machenike.myapplication.a.BaseFragment;
import com.example.machenike.myapplication.a.Constant;
import com.example.machenike.myapplication.a.JSONUtils;
import com.example.machenike.myapplication.a.NetWorkUtils;
import com.example.machenike.myapplication.a.StringUtils;
import com.example.machenike.myapplication.commonwidget.LoadingDataTip;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;



public class c3Frg extends BaseFragment implements LoadingDataTip.onReloadListener {


    @ViewInject(R.id.webView)
    WebView webView;
    @ViewInject(R.id.loadedTip)
    LoadingDataTip loadedTip;
    @ViewInject(R.id.relay_01)
    RelativeLayout relay_01;
    @ViewInject(R.id.relay_cehua)
    RelativeLayout relay_cehua;
    @ViewInject(R.id.linlay_bottom)
    LinearLayout linlay_bottom;
    @ViewInject(R.id.in_tv_title)
    TextView in_tv_title;
    @ViewInject(R.id.imgv_cehua)
    ImageView imgv_cehua;

    private String mFailingUrl = "";
    private boolean isLoadOK = true;
    private String app_type;
    private String url;

    private Map<String, String> map;
    private Map<String, String> map_app;
    private Map<String, String> map_main;

    @Override
    protected int getLayoutResource() {
        return R.layout.cfrg_1;
    }

    @Override
    public void initPresenter() {
    }

    @Event(value = {R.id.relay_01})
    private void Click(View view) {
        switch (view.getId()) {
            case R.id.relay_01:
                break;
            default:
                break;
        }
    }

    @Override
    protected void initView() {
        String json = StringUtils.getJson("app.json", getActivity());
        map = JSONUtils.parseKeyAndValueToMap(json);
        map_app = JSONUtils.parseKeyAndValueToMap(map.get("app"));
        map_main = JSONUtils.parseKeyAndValueToMapList(map.get("main")).get(2);
        app_type = map_app.get("app_type");
        url = map_main.get("web_url");
    }

    @Override
    public void requestDatas() {
        super.requestDatas();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void webViewSet() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setLoadWithOverviewMode(true);//这方法可以让你的页面适应手机屏幕的分辨率，完整的显示在屏幕上，可以放大缩小
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setUseWideViewPort(true);
        //图片加载处理
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        final String cachePath = getActivity().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(cachePath);
        webSettings.setAppCacheMaxSize(5 * 1024 * 1024);
        webSettings.setDomStorageEnabled(true);//可以使用Android4.4手机和Chrome Inspcet Device联调
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//5.0以上
        }
    }


    public void titleBarIsCover() {
        relay_cehua.setVisibility(View.GONE);

        int dimension2 = Integer.parseInt(map_main.get("webcover_b_h"));
        int dimension = Integer.parseInt(map.get("bottom_h"));
        if (dimension - dimension2 < 0) {
            linlay_bottom.setPadding(0, 0, 0, (int) AutoUtils.getPercentHeightSize(dimension));
        } else {
            linlay_bottom.setPadding(0, 0, 0, (int) AutoUtils.getPercentHeightSize(dimension - dimension2));
        }

        int dimensionTop = Integer.parseInt(map_main.get("web_t_h"));
        int dimension2Top = Integer.parseInt(map_main.get("webcover_t_h"));

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) webView.getLayoutParams();
        if (dimensionTop - dimension2Top < 0) {
            lp.topMargin = (int) AutoUtils.getPercentHeightSize(0);
        } else {
            lp.topMargin = (int) AutoUtils.getPercentHeightSize(dimensionTop - dimension2Top);
        }

        in_tv_title.setText(map_main.get("web_title"));
        in_tv_title.setTextColor(Color.parseColor(map_main.get("web_text_color")));
        in_tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, AutoUtils.getPercentWidthSize(Integer.parseInt(map_main.get("web_text_size"))));

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) relay_01.getLayoutParams();
        params.height = AutoUtils.getPercentHeightSize(dimensionTop);
        relay_01.setLayoutParams(params);

        if(map_main.get("web_text_bg").startsWith("#")){
            relay_01.setBackgroundColor(Color.parseColor(map_main.get("web_text_bg")));
        }else{
            String img_url = map_main.get("web_text_bg");
            if (!img_url.startsWith("http")) {
                relay_01.setBackgroundResource(StringUtils.getImageResourceId(img_url));
            } else {
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleBarIsCover();
        webViewSet();
        loadedTip.setOnReloadListener(this);
        if (NetWorkUtils.isNetConnected(getContext())) {
            loadedTip.setLoadingTip(LoadingDataTip.LoadStatus.loading);
            webView.loadUrl(url);
        } else {
            mFailingUrl = url;
            loadedTip.setLoadingTip(LoadingDataTip.LoadStatus.error);
        }
        Constant.webView3 = webView;
        relay_cehua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainAty mainAty = (MainAty) getActivity();
                mainAty.a();
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                isLoadOK = false;
                mFailingUrl = failingUrl;
                webView.setVisibility(View.GONE);
                loadedTip.setLoadingTip(LoadingDataTip.LoadStatus.error);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isLoadOK) {
                    webView.setVisibility(View.VISIBLE);
                    loadedTip.setLoadingTip(LoadingDataTip.LoadStatus.finish);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO 自动生成的方法存根
                LogUtil.e("uuuuuuuuuu" + url);
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                if (url.startsWith("alipays://")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                webView.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                view.loadUrl("javascript:function setTop(){document.querySelector('#pageTop').style.display=\"none\";}setTop();");
                view.loadUrl("javascript:function setTop(){document.querySelector('#turnpage').style.display=\"none\";}setTop();");
                view.loadUrl("javascript:function setTop(){document.querySelector('#pageWp > div.hbox.hindex').style.display=\"none\";}setTop();");
                view.loadUrl("javascript:function setTop(){document.querySelector('#pageFooter').style.display=\"none\";}setTop();");
                view.loadUrl("javascript:function setTop(){document.querySelector('#pageContent > article > section > h2').style.display=\"none\";}setTop();");

                view.loadUrl("javascript:function setTop(){document.getElementById('pageWp').style.padding = 0}setTop();");
                super.onProgressChanged(view, newProgress);
            }

        });

    }

    @Override
    public void reload() {
        if (!NetWorkUtils.isNetConnected(getContext())) {
            showShortToast("网络异常");
            return;
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        isLoadOK = true;
        webView.loadUrl(mFailingUrl);
        webView.setVisibility(View.VISIBLE);
        loadedTip.setLoadingTip(LoadingDataTip.LoadStatus.loading);
    }


}
