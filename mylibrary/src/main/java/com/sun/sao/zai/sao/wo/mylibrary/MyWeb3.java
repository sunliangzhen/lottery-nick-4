package com.sun.sao.zai.sao.wo.mylibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;


class MyWeb3 extends AppCompatActivity {

    private LinearLayout mldzChufanginfoWeblayout;
    private String url;
    AgentWeb go;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_web3);
        url = getIntent().getStringExtra("url");
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        mldzChufanginfoWeblayout = findViewById(R.id.mldz_chufanginfo_weblayout);
//        AgentWeb mAgentWeb = AgentWeb.with(this)//传入Activity
//                .setAgentWebParent(mldzChufanginfoWeblayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
//                .useDefaultIndicator()// 使用默认进度条
//                .defaultProgressBarColor() // 使用默认进度条颜色
//                .createAgentWeb()//
//                .ready()
//                .go("http://e.firefoxchina.cn/");

        go = AgentWeb.with(this)
                .setAgentWebParent(mldzChufanginfoWeblayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }


    @Override
    public void onBackPressed() {
        if (go.back()) {
        } else {
            super.onBackPressed();
        }
    }
}
