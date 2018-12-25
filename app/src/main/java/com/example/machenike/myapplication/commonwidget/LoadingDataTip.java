package com.example.machenike.myapplication.commonwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.machenike.myapplication.R;
import com.example.machenike.myapplication.a.ImagesUtils;
import com.example.machenike.myapplication.a.JSONUtils;
import com.example.machenike.myapplication.a.StringUtils;
import com.zhy.autolayout.utils.AutoUtils;
import com.zyao89.view.zloading.ZLoadingView;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.Map;



/**
 * des:加载页面内嵌提示
 * Created by xsf
 * on 2016.07.17:22
 */
public class LoadingDataTip extends LinearLayout {

    private ImageView img_tip_logo;
    private ZLoadingView progress;
    private TextView tv_tips;
    private TextView bt_operate;
    private TextView tv_lar;
    private String errorMsg;
    private onReloadListener onReloadListener;
    private onLarListener onLarListener;
    private String img_url;
    private Context context;

    public LoadingDataTip(Context context) {
        super(context);
        initView(context);
    }

    public LoadingDataTip(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public LoadingDataTip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingDataTip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public static enum LoadStatus {
        sereverError, error, empty, loading, finish, lar, empty2
    }

    private void initView(Context context) {
        this.context = context;
        View.inflate(context, R.layout.dialog_loading_tip_data, this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        img_tip_logo = (ImageView) findViewById(R.id.img_tip_logo);
        progress = (ZLoadingView) findViewById(R.id.progress);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        tv_lar = (TextView) findViewById(R.id.tv_lar);
        bt_operate = (TextView) findViewById(R.id.bt_operate);
        //重新尝试
        img_tip_logo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onReloadListener != null) {
                    onReloadListener.reload();
                }
            }
        });
        //登陆
        tv_lar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLarListener != null) {
                    onLarListener.lar();
                }
            }
        });
        setVisibility(View.GONE);
        String json = StringUtils.getJson("app.json", context);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(JSONUtils.parseKeyAndValueToMap(json).get("progress"));
        img_url = map.get("nonet_img");
//        progress.setType(map.get("progress_type"), map.get("progress_bg"));

        progress.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            progress.setColorFilter(Color.parseColor(map.get("progress_bg")));
        }

        bt_operate.setTextColor(Color.parseColor(map.get("text_color")));
        bt_operate.setText(map.get("text"));
        bt_operate.setTextSize(TypedValue.COMPLEX_UNIT_PX, AutoUtils.getPercentHeightSize(Integer.parseInt(map.get("text_size"))));
        if (!img_url.startsWith("http")) {
            img_tip_logo.setImageResource(StringUtils.getImageResourceId(img_url));
        } else {
            ImagesUtils.disImg2(context, img_url, img_tip_logo);
        }
        LayoutParams paramsImag = (LayoutParams) img_tip_logo.getLayoutParams();
        paramsImag.height = AutoUtils.getPercentHeightSize(Integer.parseInt(map.get("img_h")));
        paramsImag.width = AutoUtils.getPercentHeightSize(Integer.parseInt(map.get("img_w")));
        img_tip_logo.setLayoutParams(paramsImag);

    }

    public void setTips(String tips) {
        if (tv_tips != null) {
            tv_tips.setText(tips);
        }
    }

    /**
     * 根据状态显示不同的提示
     *
     * @param loadStatus
     */
    public void setLoadingTip(LoadStatus loadStatus) {
        switch (loadStatus) {
            case empty:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tv_tips.setText(getContext().getText(R.string.empty).toString());
                img_tip_logo.setImageResource(R.drawable.empty_nodata);
                bt_operate.setVisibility(View.GONE);
                tv_tips.setVisibility(View.GONE);
                tv_lar.setVisibility(View.GONE);
                break;
            case empty2:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tv_tips.setText(getContext().getText(R.string.empty).toString());
                img_tip_logo.setImageResource(R.drawable.empty_nodata);
                bt_operate.setVisibility(View.VISIBLE);
                tv_tips.setVisibility(View.GONE);
                tv_lar.setVisibility(View.GONE);
                break;
            case sereverError:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                if (TextUtils.isEmpty(errorMsg)) {
                    tv_tips.setText(getContext().getText(R.string.net_error).toString());
                } else {
                    tv_tips.setText(errorMsg);
                }
//                img_tip_logo.setImageResource(R.drawable.empty_no_network);
                bt_operate.setVisibility(View.GONE);
                tv_tips.setVisibility(View.GONE);
                tv_lar.setVisibility(View.GONE);

//                if (!img_url.startsWith("http")) {
//                    img_tip_logo.setImageResource(StringUtils.getImageResourceId(img_url));
//                } else {
//                    ImagesUtils.disImg2(context, img_url, img_tip_logo);
//                }

                break;
            case error:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                if (TextUtils.isEmpty(errorMsg)) {
                    tv_tips.setText(getContext().getText(R.string.net_error).toString());
                } else {
                    tv_tips.setText(errorMsg);
                }
                img_tip_logo.setImageResource(R.drawable.empty_no_network);
                bt_operate.setVisibility(View.GONE);
                tv_tips.setVisibility(View.GONE);
                tv_lar.setVisibility(View.GONE);
//
//                if (!img_url.startsWith("http")) {
//                    img_tip_logo.setImageResource(StringUtils.getImageResourceId(img_url));
//                } else {
//                    ImagesUtils.disImg2(context, img_url, img_tip_logo);
//                }
                break;
            case loading:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tv_tips.setText("Loading...");
                bt_operate.setVisibility(View.GONE);
                tv_tips.setVisibility(View.GONE);
                tv_lar.setVisibility(View.GONE);
                break;
            case lar:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                bt_operate.setVisibility(View.GONE);
                tv_tips.setVisibility(View.GONE);
                tv_lar.setVisibility(View.VISIBLE);
                break;
            case finish:
                setVisibility(View.GONE);
                break;
            default:

                break;

        }
    }


    public void setOnReloadListener(onReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;

    }

    public void setLarListener(onLarListener onLarListener) {
        this.onLarListener = onLarListener;

    }

    /**
     * 重新尝试接口
     */
    public interface onReloadListener {
        void reload();
    }

    public interface onLarListener {
        void lar();
    }
}

