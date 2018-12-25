package com.example.machenike.myapplication;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

public class AboutAty extends BaseActivity {

    @ViewInject(R.id.tv_01)
    TextView tv_01;
    @ViewInject(R.id.tv_02)
    TextView tv_02;

    @Override
    public int getLayoutId() {
        return R.layout.aty_about;
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

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0";
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv_01.setText(getString(R.string.version_name) + getVersion());
        tv_02.setText("@版权所有(V" + getVersion() + ")");
    }


    @Event(value = {R.id.relay_cehua})
    private void Click(View view) {
        switch (view.getId()) {
            case R.id.relay_cehua:
                finish();
                break;
        }
    }
}
