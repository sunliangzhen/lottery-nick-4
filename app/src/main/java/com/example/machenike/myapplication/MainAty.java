package com.example.machenike.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.machenike.myapplication.a.Constant;
import com.example.machenike.myapplication.a.ImagesUtils;
import com.example.machenike.myapplication.a.JSONUtils;
import com.example.machenike.myapplication.a.StringUtils;
import com.example.machenike.myapplication.a.ToastUitl;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Map;


public class MainAty extends BaseActivity {

    @ViewInject(R.id.tv_01)
    TextView tv_01;
    @ViewInject(R.id.tv_02)
    TextView tv_02;
    @ViewInject(R.id.tv_03)
    TextView tv_03;
    @ViewInject(R.id.tv_04)
    TextView tv_04;
    @ViewInject(R.id.tv_05)
    TextView tv_05;

    @ViewInject(R.id.imgv_01)
    ImageView imgv_01;
    @ViewInject(R.id.imgv_02)
    ImageView imgv_02;
    @ViewInject(R.id.imgv_03)
    ImageView imgv_03;
    @ViewInject(R.id.imgv_04)
    ImageView imgv_04;
    @ViewInject(R.id.imgv_05)
    ImageView imgv_05;

    @ViewInject(R.id.linlay_01)
    LinearLayout linlay_01;
    @ViewInject(R.id.linlay_02)
    LinearLayout linlay_02;
    @ViewInject(R.id.linlay_03)
    LinearLayout linlay_03;
    @ViewInject(R.id.linlay_04)
    LinearLayout linlay_04;
    @ViewInject(R.id.linlay_05)
    LinearLayout linlay_05;

    @ViewInject(R.id.linlay_bottom)
    RelativeLayout linlay_bottom;
    @ViewInject(R.id.imgv_guide)
    ImageView imgv_guide;
    @ViewInject(R.id.relay_guide)
    RelativeLayout relay_guide;
    @ViewInject(R.id.tv_time)
    TextView tv_time;

    private WebView nowWebView;
    private ArrayList<TextView> list_tv = new ArrayList<>();
    private ArrayList<ImageView> list_imgv = new ArrayList<>();
    private int p;
    private String app_type;
    private Map<String, String> map;
    private Map<String, String> map_app;
    private Map<String, String> map_guide;
    private ArrayList<Map<String, String>> map_main;
    private MyCountDownTimer mCountDownTimer;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGuide();
        setBackTwo(true);
        titleBarIsCover();
        addFrg(map_main.get(0).get("web_url"), "0");
        if (map.get("navigationIsShowImg").equals("1")) {
            imgv_02.setVisibility(View.VISIBLE);
            imgv_03.setVisibility(View.VISIBLE);
            imgv_04.setVisibility(View.VISIBLE);
            imgv_05.setVisibility(View.VISIBLE);
        } else {
            imgv_01.setVisibility(View.GONE);
            imgv_02.setVisibility(View.GONE);
            imgv_03.setVisibility(View.GONE);
            imgv_04.setVisibility(View.GONE);
            imgv_05.setVisibility(View.GONE);
        }
        switch (Integer.parseInt(map_app.get("webPageNum"))) {
            case 1:
                linlay_01.setVisibility(View.VISIBLE);
                linlay_02.setVisibility(View.GONE);
                linlay_03.setVisibility(View.GONE);
                linlay_04.setVisibility(View.GONE);
                linlay_05.setVisibility(View.VISIBLE);
                break;
            case 2:
                linlay_01.setVisibility(View.VISIBLE);
                linlay_02.setVisibility(View.VISIBLE);
                linlay_03.setVisibility(View.GONE);
                linlay_04.setVisibility(View.GONE);
                linlay_05.setVisibility(View.VISIBLE);
                break;
            case 3:
                linlay_01.setVisibility(View.VISIBLE);
                linlay_02.setVisibility(View.VISIBLE);
                linlay_03.setVisibility(View.VISIBLE);
                linlay_04.setVisibility(View.GONE);
                linlay_05.setVisibility(View.VISIBLE);
                break;
            case 4:
                linlay_01.setVisibility(View.VISIBLE);
                linlay_02.setVisibility(View.VISIBLE);
                linlay_03.setVisibility(View.VISIBLE);
                linlay_04.setVisibility(View.VISIBLE);
                linlay_05.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        if (map_app.get("isShowsetingPage").equals("1")) {
            linlay_05.setVisibility(View.VISIBLE);
        } else {
            linlay_05.setVisibility(View.GONE);
        }
    }

    public void initGuide() {

        if (map_guide.get("is_need").equals("1")) {
            relay_guide.setVisibility(View.VISIBLE);
            String img_url = map_guide.get("img_url");
            if (!img_url.startsWith("http")) {
                imgv_guide.setImageResource(StringUtils.getImageResourceId(img_url));
            } else {
                ToastUitl.showShort("启动图不能放网络地址");
//                ImagesUtils.disImg2(this, img_url, imgv_guide);
            }
            mCountDownTimer = new MyCountDownTimer(5000, 1000);
            mCountDownTimer.start();

        } else {
            relay_guide.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.aty_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        map = JSONUtils.parseKeyAndValueToMap(json);
        map_app = JSONUtils.parseKeyAndValueToMap(map.get("app"));
        map_guide = JSONUtils.parseKeyAndValueToMap(map.get("guide"));
        map_main = JSONUtils.parseKeyAndValueToMapList(map.get("main"));
        app_type = map_app.get("app_type");
        list_tv.add(tv_01);
        list_tv.add(tv_02);
        list_tv.add(tv_03);
        list_tv.add(tv_04);
        list_tv.add(tv_05);
        list_imgv.add(imgv_01);
        list_imgv.add(imgv_02);
        list_imgv.add(imgv_03);
        list_imgv.add(imgv_04);
        list_imgv.add(imgv_05);
    }

    @Override
    public void requestData() {

    }

    public void a() {
    }

    public void titleBarIsCover() {

        for (int i = 0; i < map_main.size(); i++) {
            int text_size = Integer.parseInt(map_main.get(i).get("text_size"));
            list_tv.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, AutoUtils.getPercentWidthSize(text_size));
            list_tv.get(i).setText(map_main.get(i).get("text"));

            LinearLayout.LayoutParams paramsImag = (LinearLayout.LayoutParams) list_imgv.get(i).getLayoutParams();
            paramsImag.height = AutoUtils.getPercentHeightSize(Integer.parseInt(map_main.get(i).get("img_h")));
            paramsImag.width = AutoUtils.getPercentHeightSize(Integer.parseInt(map_main.get(i).get("img_w")));
            list_imgv.get(i).setLayoutParams(paramsImag);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linlay_bottom.getLayoutParams();
            params.height = AutoUtils.getPercentHeightSize(Integer.parseInt(map.get("bottom_h")));
            linlay_bottom.setLayoutParams(params);
            if (i == 0) {
                String img_check = map_main.get(i).get("img_check");
                if (!img_check.startsWith("http")) {
                    list_imgv.get(i).setImageResource(StringUtils.getImageResourceId(img_check));
                } else {
                    ImagesUtils.disImg2(MainAty.this, img_check, list_imgv.get(i));
                }
                list_tv.get(i).setTextColor(Color.parseColor(map_main.get(i).get("text_color_check")));
            } else {
                String img_check = map_main.get(i).get("img_normal");
                if (!img_check.startsWith("http")) {
                    list_imgv.get(i).setImageResource(StringUtils.getImageResourceId(img_check));
                } else {
                    ImagesUtils.disImg2(MainAty.this, img_check, list_imgv.get(i));
                }
                list_tv.get(i).setTextColor(Color.parseColor(map_main.get(i).get("text_color")));
            }
        }

    }


    @Override
    protected int getFragmentContainerId() {
        return R.id.fralay_content;
    }

    public void addFrg(String web_url, String index) {
        String type = "0";
        if (TextUtils.isEmpty(web_url) || web_url.startsWith("http")) {
            type = index;
        } else {
            type = web_url;
        }
        switch (type) {
            case "0":
                addFragment(c1Frg.class, null);
                break;
            case "1":
                addFragment(c2Frg.class, null);
                break;
            case "2":
                addFragment(c3Frg.class, null);
                break;
            case "3":
                addFragment(c4Frg.class, null);
                break;
//            case "4":
//                addFragment(c5Frg.class, null);
//                break;
//            case "page_model":
//                addFragment(cHomeFrg.class, null);
//                break;
//            case "page_model_2":
//                addFragment(cHomeModel2Frg.class, null);
//                break;
//            case "page_model_2_2":
//                addFragment(cHomeModel2_2Frg.class, null);
//                break;
//            case "page_model_2_3":
//                addFragment(cHomeModel2_3Frg.class, null);
//                break;
//            case "page_model_3":
//                addFragment(CommunityFrg.class, null);
//                break;
//            case "page_model_4":
//                addFragment(cHomeModel5Frg.class, null);
//                break;
//            case "my_01":
//                addFragment(HomeFrg.class, null);
//                break;
//            case "my_mon":
//                addFragment(GoMonFrg.class, null);
//                break;
//            case "my_box_01":
//                addFragment(cBoxHomeFrg.class, null);
//                break;
//            case "my_box_02":
//                addFragment(cBox2Frg.class, null);
//                break;
//            case "box_mine":
//                addFragment(cBoxMineFrg.class, null);
//                break;
        }
    }

    @Event(value = {R.id.linlay_01, R.id.linlay_02, R.id.linlay_03,
            R.id.linlay_04, R.id.linlay_05, R.id.relay_guide})
    private void Click(View view) {
        switch (view.getId()) {
            case R.id.linlay_01:
                p = 0;
                setSelector(tv_01);
                addFrg(map_main.get(0).get("web_url"), "0");
                break;
            case R.id.linlay_02:
                p = 1;
                setSelector(tv_02);
//                addFragment(c2Frg.class, null);
                addFrg(map_main.get(1).get("web_url"), "1");
                break;
            case R.id.linlay_03:
                p = 2;
                setSelector(tv_03);
//                addFragment(c3Frg.class, null);
                addFrg(map_main.get(2).get("web_url"), "2");
                break;
            case R.id.linlay_04:
                p = 3;
                setSelector(tv_04);
//                addFragment(c4Frg.class, null);
                addFrg(map_main.get(3).get("web_url"), "3");
                break;
            case R.id.linlay_05:
//                if (!Config.isLogin()) {
//                    startActivity(LoginAty.class);
//                    return;
//                }
                p = 4;
                setSelector(tv_05);
                addFragment(c5Frg.class, null);
                addFrg(map_main.get(4).get("web_url"), "4");
                break;
            case R.id.relay_guide:
                break;
            default:
                break;
        }
    }

    public void toMine() {
        p = 4;
        setSelector(tv_05);
        addFrg(map_main.get(4).get("web_url"), "4");
    }

    public void toHome() {
        p = 0;
        setSelector(tv_01);
        addFrg(map_main.get(0).get("web_url"), "0");
    }

    public void checkImg(ImageView imgv, String img_check, TextView textView, String txtcolor) {
        if (!img_check.startsWith("http")) {
            imgv.setImageResource(StringUtils.getImageResourceId(img_check));
        } else {
            ImagesUtils.disImg2(MainAty.this, img_check, imgv);
        }
        textView.setTextColor(Color.parseColor(txtcolor));
    }

    private void setSelector(TextView tv) {
        for (int i = 0; i < list_tv.size(); i++) {
            if (tv == list_tv.get(i)) {
                checkImg(list_imgv.get(i), map_main.get(i).get("img_check"), list_tv.get(i), map_main.get(i).get("text_color_check"));
            } else {
                checkImg(list_imgv.get(i), map_main.get(i).get("img_normal"), list_tv.get(i), map_main.get(i).get("text_color"));
            }
        }
    }


    private void getWebview() {
        switch (Constant.index) {
            case 0:
                nowWebView = Constant.webView1;
                break;
            case 1:
                nowWebView = Constant.webView2;
                break;
            case 2:
                nowWebView = Constant.webView3;
                break;
            case 3:
                nowWebView = Constant.webView4;
                break;
            case 4:
                nowWebView = Constant.webView5;
                break;
            case 5:
                nowWebView = Constant.webView6;
                break;
            case 6:
                nowWebView = Constant.webView7;
                break;
            case 7:
                nowWebView = Constant.webView8;
                break;
            case 8:
                nowWebView = Constant.webView9;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (app_type.equals("3")) {
            getWebview();
//            nowWebView = Constant.webView1;
            if (nowWebView != null && nowWebView.canGoBack()) {
                nowWebView.goBack();
            } else {
                super.onBackPressed();
            }
        } else {
            if (p == 0) {
                nowWebView = Constant.webView1;
            } else if (p == 1) {
                nowWebView = Constant.webView2;
            } else if (p == 2) {
                nowWebView = Constant.webView3;
            } else if (p == 3) {
                nowWebView = Constant.webView4;
            } else {
                nowWebView = null;
            }
            if (p == 4) {
                super.onBackPressed();
            } else {
                if (nowWebView != null && nowWebView.canGoBack()) {
                    nowWebView.goBack();
                } else {
                    super.onBackPressed();
                }
            }
        }
    }


    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以「 毫秒 」为单位倒计时的总数
         *                          例如 millisInFuture = 1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick()
         *                          例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
         */

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        public void onFinish() {
            tv_time.setText("0s");
            relay_guide.setVisibility(View.GONE);
        }

        public void onTick(long millisUntilFinished) {
            tv_time.setText(millisUntilFinished / 1000 + "s");
        }

    }
}
