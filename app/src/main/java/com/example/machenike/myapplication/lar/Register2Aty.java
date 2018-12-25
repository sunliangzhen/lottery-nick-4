package com.example.machenike.myapplication.lar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.machenike.myapplication.BaseActivity;
import com.example.machenike.myapplication.R;
import com.example.machenike.myapplication.a.JSONUtils;
import com.example.machenike.myapplication.a.StringUtils;

import org.json.JSONArray;
import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by pc on 2017/3/12.
 */

public class Register2Aty extends BaseActivity {

    @ViewInject(R.id.edit_password)
    public EditText edit_password;
    @ViewInject(R.id.edit_repassword)
    public EditText edit_repassword;
    @ViewInject(R.id.edit_account)
    public EditText edit_account;


    private DbManager db;

    @Override
    public int getLayoutId() {

        String json = StringUtils.getJson("lar.json", this);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(json);
        switch (map.get("type")) {
            case "1":
                return R.layout.caty_register;
            case "2":
                return R.layout.caty_register2;
            case "3":
                return R.layout.caty_register3;
            default:
                return R.layout.caty_register;
        }
    }

    @Override
    public void requestData() {
    }

    @Override
    public void initPresenter() {
    }

    private String phone;

    @Override
    public void initView() {
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetStatusBarColor(0xffffffff);
    }


    @Event(value = {R.id.regButton, R.id.imgv_back})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.regButton:

                if (TextUtils.isEmpty(edit_account.getText().toString())) {
                    showShortToast("账号不能为空");
                    return;
                }
//                if (edit_account.getText().toString().length() < 6) {
//                    showShortToast("账号不能少于6位");
//                    return;
//                }
                if (!PwdCheckUtil.isPhoneNumber(edit_account.getText().toString()) && !PwdCheckUtil.isEmail(edit_account.getText().toString())) {
                    showShortToast("请输入正确的手机号或邮箱");
                    return;
                }
                if (TextUtils.isEmpty(edit_password.getText().toString())) {
                    showShortToast("密码不能为空");
                    return;
                }
                if (edit_password.getText().toString().length() < 6) {
                    showShortToast("密码不能少于6位");
                    return;
                }

                if (TextUtils.isEmpty(edit_repassword.getText().toString())) {
                    showShortToast("密码不能为空");
                    return;
                }
                if (!edit_password.getText().toString().equals(edit_repassword.getText().toString())) {
                    showShortToast("两次输入密码不一致");
                    return;
                }
                startProgressDialog();
                queryObjectsByTable(edit_account.getText().toString(), edit_password.getText().toString());
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        stopProgressDialog();
//                        selectData(edit_account.getText().toString(), edit_password.getText().toString());
//                    }
//                }, 2000);
//                cai.e(phone, edit_password.getText().toString(), Register2Aty.this);
//                demo.register(mRxManager, edit_username.getText().toString(), edit_password.getText().toString(), edit_phone.getText().toString(), edit_code.getText().toString(), true, this, this);
                break;
            case R.id.imgv_back:
                finish();
                break;
        }
    }

    /**
     * 插入对象
     */
    private void testinsertObject(String name, String pass) {

        user coder = new user();
        coder.setUserName(name);
        coder.setPassWord(pass);
        coder.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    LogUtil.e("11111111" + s);
                    showShortToast("注册成功");
                    finish();
                } else {
                    showShortToast("服务器异常");
                }
            }
        });
    }

    /**
     * 根据表名查询多条数据
     */

    public void queryObjectsByTable(final String name, final String pass) {
        BmobQuery query = new BmobQuery("user");
        query.addWhereEqualTo("userName", name);
        Disposable ssss = query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray ary, BmobException e) {
                stopProgressDialog();
                if (e == null) {
                    if (ary == null || ary.length() == 0) {
                        testinsertObject(name, pass);
//                        showShortToast("账号或密码输入错误");
                    } else {
                        showShortToast("该账号已经被注册");
                    }
                } else {
                    showShortToast("");
                }
            }
        });
        addSubscription(ssss);
    }

    private CompositeDisposable mCompositeDisposable;

    protected void addSubscription(Disposable s) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(s);
    }

}
