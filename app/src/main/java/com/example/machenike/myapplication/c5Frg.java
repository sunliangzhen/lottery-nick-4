package com.example.machenike.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machenike.myapplication.a.AppManager;
import com.example.machenike.myapplication.a.BaseFragment;
import com.example.machenike.myapplication.a.Constant;
import com.example.machenike.myapplication.a.ImagesUtils;
import com.example.machenike.myapplication.a.JSONUtils;
import com.example.machenike.myapplication.a.PreferencesUtils;
import com.example.machenike.myapplication.a.StringUtils;
import com.example.machenike.myapplication.dialog.DialogExit;
import com.example.machenike.myapplication.lar.LoginAty;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


/**
 * Created by Administrator on 2016/12/24.
 * 我 页面模块
 */

public class c5Frg extends BaseFragment {
    private AboutAdapter adapter;

    @ViewInject(R.id.list_item)
    ListView mListView;
    TextView user_name;

    private ImageView user_icon;
    private Map<String, String> map;
    private ArrayList<Map<String, String>> main_list;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_about;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        String json = StringUtils.getJson("set.json", getActivity());
        map = JSONUtils.parseKeyAndValueToMap(json);
        main_list = JSONUtils.parseKeyAndValueToMapList(map.get("main"));
    }


    public void initView2() {
        View user_view = LayoutInflater.from(getActivity()).inflate(R.layout.user_view, mListView, false);
        ImageView imgv_bg = (ImageView) user_view.findViewById(R.id.imgv_bg);
        user_icon = (ImageView) user_view.findViewById(R.id.user_icon);
        user_name = (TextView) user_view.findViewById(R.id.user_name);

        String img_url = map.get("user_bg");
        if (!img_url.startsWith("http")) {
            imgv_bg.setImageResource(StringUtils.getImageResourceId(img_url));
        } else {
            ImagesUtils.disImg2(getActivity(), img_url, imgv_bg);
        }
        adapter = new AboutAdapter();
        mListView.setAdapter(adapter);
        mListView.addHeaderView(user_view);
        initListener();
    }

    public void initBottomDialog(Dialog dialog) {
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
//        window.setWindowAnimations(R.style.dialog_bottom_anim);
    }


    public void loginOut() {
        Config.setLoginState(false);

        if (Config.isLogin()) {
            user_name.setText("已登录");
        } else {
            user_name.setText("请先登录");
        }
        String imgUrl = PreferencesUtils.getString(getActivity(), "imgUrl", "");
        if (Config.isLogin()) {
            String nickName = PreferencesUtils.getString(getActivity(), "nickName", "");
            if (TextUtils.isEmpty(nickName)) {
                user_name.setText(PreferencesUtils.getString(getActivity(), "userName"));
            } else {
                user_name.setText(nickName);
            }
        } else {
            user_name.setText("登录/注册");
        }

        if(Config.isLogin()&&!TextUtils.isEmpty(imgUrl)){
            ImagesUtils.disImgCircleNo(getActivity(), imgUrl, user_icon);
        }else{
            String img_url = map.get("user_logo");
            if (!img_url.startsWith("http")) {
                user_icon.setImageResource(StringUtils.getImageResourceId(img_url));
            } else {
                ImagesUtils.disImgCircleNo(getActivity(), img_url, user_icon);
            }
        }
    }
//        if (Constant.is_first_login.equals("1")) {
//            AppManager.getInstance().killActivity(Main2Aty.class);
//            AppManager.getInstance().killActivity(MainAty.class);
//            startActivity(LoginAty.class);
//            finish();
//        } else {
//            finish();
//        }
//    }

    public void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                Uri uri;

                if (position == 0) {
                    if (!Config.isLogin()) {
                        startActivity(LoginAty.class);
                    } else {

                        DialogExit dialogPhone = new DialogExit(getActivity(), new DialogExit.DialogExitListen() {
                            @Override
                            public void call() {
                                loginOut();
                            }
                        });
                        initBottomDialog(dialogPhone);
                        dialogPhone.show();

//                        startActivity(SetAty.class);
                    }
                    return;
                }

                switch (main_list.get(position - 1).get("type")) {
                    case "1":
                        int min=500;
                        int max=5000;
                        Random random = new Random();
                        int num = random.nextInt(max)%(max-min+1) + min;
                        startProgressDialog();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stopProgressDialog();
                                Toast.makeText(getActivity(), "没有可更新版本", Toast.LENGTH_SHORT).show();
                            }
                        }, num);
                        break;
                    case "2":
                        intent = new Intent(getActivity(), FeedbackActivity.class);
                        startActivity(intent);
                        break;
                    case "3":
                        startProgressDialog();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stopProgressDialog();
                                Toast.makeText(getActivity(), "清除缓存成功", Toast.LENGTH_SHORT).show();
                            }
                        }, 1500);
                        break;
                    case "4":
                        intent = new Intent(getActivity(), AboutAty.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }


    private class AboutAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return main_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_about, null);
            TextView tv_about = (TextView) convertView.findViewById(R.id.tv_about);
            tv_about.setText(main_list.get(position).get("text"));
            return convertView;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView2();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Config.isLogin()) {
            user_name.setText("已登录");
        } else {
            user_name.setText("请先登录");
        }
        String imgUrl = PreferencesUtils.getString(getActivity(), "imgUrl", "");
        if (Config.isLogin()) {
            String nickName = PreferencesUtils.getString(getActivity(), "nickName", "");
            if (TextUtils.isEmpty(nickName)) {
                user_name.setText(PreferencesUtils.getString(getActivity(), "userName"));
            } else {
                user_name.setText(nickName);
            }
        } else {
            user_name.setText("登录/注册");
        }

        if(Config.isLogin()&&!TextUtils.isEmpty(imgUrl)){
            ImagesUtils.disImgCircleNo(getActivity(), imgUrl, user_icon);
        }else{
            String img_url = map.get("user_logo");
            if (!img_url.startsWith("http")) {
                user_icon.setImageResource(StringUtils.getImageResourceId(img_url));
            } else {
                ImagesUtils.disImgCircleNo(getActivity(), img_url, user_icon);
            }
        }
    }

}
