package com.example.machenike.myapplication.b;

import android.os.Bundle;
import android.view.View;

import com.example.machenike.myapplication.BaseActivity;
import com.example.machenike.myapplication.R;

public class SendEmailAty extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.aty_send_email;
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
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EmailUtil.autoSendMail(getApplicationContext(), "我发送的邮件，请接收");
                    }
                }).start();
            }
        });
    }
}
