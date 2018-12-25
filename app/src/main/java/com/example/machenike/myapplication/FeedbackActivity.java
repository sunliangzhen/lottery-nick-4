package com.example.machenike.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.machenike.myapplication.a.ImagesUtils;
import com.example.machenike.myapplication.a.JSONUtils;
import com.example.machenike.myapplication.a.StringUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;


/**
 * Created by liaozhoubei on 2017/1/5.
 */

public class FeedbackActivity extends BaseActivity {


    @ViewInject(R.id.webView)
    WebView webView;
    @ViewInject(R.id.relay_01)
    RelativeLayout relay_01;
    @ViewInject(R.id.in_tv_title)
    TextView in_tv_title;
    @ViewInject(R.id.send_button)
    TextView send_button;
    @ViewInject(R.id.imgv_cehua)
    ImageView imgv_cehua;

    private EditText subject_edit_text; // 邮件主题
    private EditText contact_information_edit_text;  // 联系方式
    private EditText feedback_edit_view;    // 反馈信息

    private Map<String, String> feedback_map;
    Map<String, String> map;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView22();

        map = JSONUtils.parseKeyAndValueToMap(JSONUtils.parseKeyAndValueToMap(json).get("feedback"));
        relay_01.setBackgroundColor(Color.parseColor(map.get("text_bg")));
        in_tv_title.setTextColor(Color.parseColor(map.get("text_color")));
        send_button.setTextColor(Color.parseColor(map.get("text_color")));

        String img_url = map.get("img_back");
        if (!img_url.startsWith("http")) {
            imgv_cehua.setImageResource(StringUtils.getImageResourceId(img_url));
        } else {
            ImagesUtils.disImg2(this, img_url, imgv_cehua);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initPresenter() {

    }

    @Event(value = {R.id.relay_cehua, R.id.send_button})
    private void Click(View view) {
        switch (view.getId()) {
            case R.id.relay_cehua:
                finish();
                break;
            case R.id.send_button:
                String subject = subject_edit_text.getText().toString();
                String contact_information = contact_information_edit_text.getText().toString();
                String feedback = feedback_edit_view.getText().toString();
                if (!TextUtils.isEmpty(subject) || !TextUtils.isEmpty(feedback)) {
                    if (!TextUtils.isEmpty(contact_information)) {
                        feedback = "联系方式：" + contact_information + "反馈信息：" + feedback;
                    }
                    Intent data = new Intent(Intent.ACTION_SENDTO);
                    data.setData(Uri.parse("mailto:"+map.get("gmail")));
                    data.putExtra(Intent.EXTRA_SUBJECT, subject);
                    data.putExtra(Intent.EXTRA_TEXT, feedback);
                    startActivity(data);
                    finish();
                }
                break;
        }
    }

    @Override
    public void initView() {
        String json = StringUtils.getJson("app.json", this);
        feedback_map = JSONUtils.parseKeyAndValueToMap(JSONUtils.parseKeyAndValueToMap(json).get("feedback"));
    }

    private void initView22() {
        subject_edit_text = (EditText) findViewById(R.id.subject_edit_text);
        contact_information_edit_text = (EditText) findViewById(R.id.contact_information_edit_text);
        feedback_edit_view = (EditText) findViewById(R.id.feedback_edit_view);
    }

    @Override
    public void requestData() {

    }


}
