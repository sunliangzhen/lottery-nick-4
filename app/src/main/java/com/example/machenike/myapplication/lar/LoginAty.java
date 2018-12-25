package com.example.machenike.myapplication.lar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.machenike.myapplication.BaseActivity;
import com.example.machenike.myapplication.Config;
import com.example.machenike.myapplication.MainAty;
import com.example.machenike.myapplication.R;
import com.example.machenike.myapplication.a.Constant;
import com.example.machenike.myapplication.a.JSONUtils;
import com.example.machenike.myapplication.a.PreferencesUtils;
import com.example.machenike.myapplication.a.StringUtils;

import org.json.JSONArray;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Administrator
 */
public class LoginAty extends BaseActivity {


    @ViewInject(R.id.userName)
    public EditText userName;
    @ViewInject(R.id.password)
    public EditText password;
    @ViewInject(R.id.loginButton)
    public Button loginButton;
    @ViewInject(R.id.regTextView)
    public TextView regTextView;

    private Map<String, String> map;

    @Override
    public int getLayoutId() {
        String json = StringUtils.getJson("lar.json", this);
        map = JSONUtils.parseKeyAndValueToMap(json);
        switch (map.get("type")) {
            case "1":
                return R.layout.caty_login;
            case "2":
                return R.layout.caty_login2;
            case "3":
                return R.layout.caty_login3;
            default:
                return R.layout.caty_login;
        }
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
    protected void onResume() {
        super.onResume();
    }


    /**
     * 根据表名查询多条数据
     */
    public void queryObjectsByTable(String name, String pass) {
        BmobQuery query = new BmobQuery("user");
        query.addWhereEqualTo("userName", name);
        query.addWhereEqualTo("passWord", pass);
        Disposable objectsByTable = query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray ary, BmobException e) {
                stopProgressDialog();
                if (e == null) {
                    if (ary == null || ary.length() == 0) {
                        showShortToast("账号或密码输入错误");
                    } else {
                        Config.setLoginState(true);
                        ArrayList<Map<String, String>> arrayList = JSONUtils.parseKeyAndValueToMapList(ary.toString());
                        Map<String, String> map = arrayList.get(0);
                        PreferencesUtils.putString(LoginAty.this, "objectId", map.get("objectId"));
                        PreferencesUtils.putString(LoginAty.this, "userName", map.get("userName"));
                        PreferencesUtils.putString(LoginAty.this, "imgUrl", map.get("imgUrl"));
                        PreferencesUtils.putString(LoginAty.this, "nickName", map.get("nickName"));
                        PreferencesUtils.putString(LoginAty.this, "passWord", map.get("passWord"));
                        LogUtil.e(ary.toString() + ary.length() + "aaaaaaaaaaa");
                        if (Constant.is_first_login.equals("1")) {
                            if (Constant.app_type.equals("3")) {
                                Intent intent = new Intent(LoginAty.this, MainAty.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(LoginAty.this, MainAty.class);
                                startActivity(intent);
                            }
                            finish();
                        } else {
                            finish();
                        }
                    }
//                    Config.setLoginState(true);
//                    startActivity(MainAty.class);
//                    finish();
//                    log(ary.toString());
                } else {
//                    loge(e);
                    showShortToast("账号或密码输入错误");
                }
            }
        });
        addSubscription(objectsByTable);

    }

    /**
     * 解决Subscription内存泄露问题
     *
     * @param s
     */
    private CompositeDisposable mCompositeDisposable;

    protected void addSubscription(Disposable s) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(s);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GradientDrawable background = (GradientDrawable) loginButton.getBackground();
        background.setColor(Color.parseColor(map.get("lar_bg")));
        ((GradientDrawable) regTextView.getBackground()).setStroke(1, Color.parseColor(map.get("lar_bg")));
        regTextView.setTextColor(Color.parseColor(map.get("lar_bg")));
    }

    @Event(value = {R.id.regTextView, R.id.find_password_tv, R.id.loginButton, R.id.imgv_back})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.regTextView:
                startActivity(Register2Aty.class);
                break;
            case R.id.find_password_tv:
                break;
            case R.id.loginButton:
                if (TextUtils.isEmpty(userName.getText().toString())) {
                    showShortToast("账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    showShortToast("密码不能为空");
                    return;
                }
                startProgressDialog();
                queryObjectsByTable(userName.getText().toString(), password.getText().toString());
                break;
            case R.id.imgv_back:
                finish();
                break;
        }
    }
}
