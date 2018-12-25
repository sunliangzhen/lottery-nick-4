package com.example.machenike.myapplication.update_majia;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.machenike.myapplication.BaseActivity;
import com.example.machenike.myapplication.MainAty;
import com.example.machenike.myapplication.R;
import com.example.machenike.myapplication.a.Constant;
import com.example.machenike.myapplication.a.JSONUtils;
import com.example.machenike.myapplication.a.PreferencesUtils;
import com.example.machenike.myapplication.commonwidget.app;
import com.example.machenike.myapplication.encoder.BASE64Decoder;
import com.example.machenike.myapplication.net.ApiListener;
import com.example.machenike.myapplication.net.ApiTool;

import org.json.JSONArray;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class splash152Aty extends BaseActivity {

    private String appid = "20181211002";

    @Override
    public int getLayoutId() {
        return R.layout.aty_splash2;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
    }

    @Override
    public void requestData() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appid = getResources().getString(R.string.appid);
//        Config.setLoginState(true);
        PreferencesUtils.putString(this, "isShowContent", "0");
        if (caiUtils.isAvilible(splash152Aty.this, Constant.uninstall_packname)) {
            PackageManager packageManager = getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(Constant.uninstall_packname);
            startActivity(intent);
            finish();
            return;
        }
        if (caiUtils.isAvilible(splash152Aty.this, Constant.uninstall_packname2)) {
            PackageManager packageManager = getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(Constant.uninstall_packname2);
            startActivity(intent);
            finish();
            return;
        }
//        goNext();
        queryNear();
//        view();
//        Config.setLoginState(true);
    }


    //    http://103.71.50.136/bick/public/index.php/api/index/get_appid/appid/XXX
//    XXX为自己生成的APPID 生成后发给我添加即可测试
//    返回的值需要base64解密 解密后 1是为跳转 0就是不跳转
    private CompositeDisposable mCompositeDisposable;

    protected void addSubscription(Disposable s) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(s);
    }

    private void queryNear() {
        BmobQuery query = new BmobQuery("app");
        query.addWhereEqualTo("appid", appid);
        query.setLimit(2);
        query.order("createdAt");
        Disposable disposable = query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray ary, BmobException e) {
                if (e == null) {

                    Log.e("ssssssss",ary.toString());
                    ArrayList<Map<String, String>> arrayList = JSONUtils.parseKeyAndValueToMapList(ary.toString());
                    Map<String, String> map = arrayList.get(0);
                    String isShow = map.get("isShow");
                    String url = map.get("apkUrl");

                    if (isShow.equals("1")) {
                        if (url.contains(".apk")) {
                            Intent intent = new Intent(splash152Aty.this, MainDownloadAty.class);
                            intent.putExtra("url_down", url);
                            startActivity(intent);
                            finish();
                        } else {

//                            Log.e("ssssssssssssss",url);
                            Intent intent = new Intent(splash152Aty.this, MyWeb3.class);
                            intent.putExtra("url", url);
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        view();
                    }


                } else {
//                    Log.e("ssssssss",ary.toString());
                    view();
//                    loge(e);
                }
            }
        });

        addSubscription(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅，防止rx内存泄露
        if (this.mCompositeDisposable != null) {
            this.mCompositeDisposable.dispose();
        }
    }

    public void goNext() {
        RequestParams params = new RequestParams("http://plmoknijbuhvygc.com/Lottery/get_init_data?appid=" + appid + "&type=android");
        ApiTool apiTool = new ApiTool();
        apiTool.getApi(params, new ApiListener() {
            @Override
            public void onCancelled(Callback.CancelledException var1) {
            }

            @Override
            public void onComplete(RequestParams var1, String var2, String type) {
                Map<String, String> map = JSONUtils.parseKeyAndValueToMap(var2);
                if (!map.get("type").equals("200")) {
                    view();
                    return;
                }
                Map<String, String> data_map = JSONUtils.parseKeyAndValueToMap(map.get("data"));

                if (data_map.get("is_jump").equals("1")) {
                    String url = data_map.get("jump_url");

                    if (url.contains(".apk")) {
                        Intent intent = new Intent(splash152Aty.this, MainDownloadAty.class);
                        intent.putExtra("url_down", url);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(splash152Aty.this, MyWeb3.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    view();
                }
            }

            @Override
            public void onError(Map<String, String> var1, RequestParams var2) {
                view();
            }

            @Override
            public void onExceptionType(Throwable var1, RequestParams params, String type) {
                view();
            }
        }, "get_init_data");
    }

    public String getFromBASE64(String s) {
        if (s == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

    public void view() {
//        Intent intent = new Intent(splash152Aty.this, MyWeb3.class);
//        intent.putExtra("url", "https://buyimg.bianxianmao.com/");
//        startActivity(intent);
//        finish();
        startActivity(MainAty.class);
        finish();
    }


}
