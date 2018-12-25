package com.example.machenike.myapplication.update_majia;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.machenike.myapplication.BaseActivity;
import com.example.machenike.myapplication.R;
import com.example.machenike.myapplication.a.Constant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import com.sun.sao.zai.sao.wo.mylibrary.MainAty;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ROYGEM-0830-1 on 2017/10/18.
 */

public class MainDownloadAty extends BaseActivity {


    public RelativeLayout relay_01;
    public ProgressBar pbProgress2;
    public TextView netSpeed2;

    public TextView tv_out_download;

    private NumberFormat numberFormat;
    private String url_down;
    private File mfile;


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private List<String> mPermissionList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.caty_main2;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (caiUtils.isAvilible(this, Constant.uninstall_packname)) {
            caiUtils.unInstall(this, caiUtils.getPackageName());
        } else if(caiUtils.isAvilible(this, Constant.uninstall_packname2)){
            caiUtils.unInstall(this, caiUtils.getPackageName());
        }
        else  {
            if (mfile != null) {
                caiUtils.install(mfile, caiUtils.getPackageName() + ".FileProvider");
            }
        }
    }


    @Override
    public void initView() {
        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        url_down = getIntent().getStringExtra("url_down");
    }

    @Override
    public void requestData() {
    }

    public void a(String url) {
        ApkModel apk1 = new ApkModel();
        apk1.url = url;
        String fileName = System.currentTimeMillis() + "";
        GetRequest<File> request = OkGo.<File>get(apk1.url)
                .headers("aaa", "111")
                .params("bbb", "222");
        OkDownload.request(apk1.url, request)
                .priority(apk1.priority)
                .extra1(apk1)
                .save().fileName(fileName)
                .register(new LogDownloadListener())
                .start();


        DownloadTask task = OkDownload.restore(DownloadManager.getInstance().get(apk1.url));
        task.register(new ListDownloadListener(apk1.url))
                .register(new LogDownloadListener());
    }

    public  void init(){
        relay_01 = findViewById(R.id.relay_01);
        pbProgress2 = findViewById(R.id.pbProgress2);
        netSpeed2 = findViewById(R.id.netSpeed2);
        tv_out_download = findViewById(R.id.tv_out_download);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        SetTranslanteBar();
        setBackTwo(true);
        a(url_down);

        mPermissionList.clear();
        for (int i = 0; i < PERMISSIONS_STORAGE.length; i++) {
            if (ContextCompat.checkSelfPermission(this, PERMISSIONS_STORAGE[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(PERMISSIONS_STORAGE[i]);
            }
        }
        if (mPermissionList.isEmpty() || mPermissionList.size() == 0 || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            a(url_down);
            tv_out_download.setVisibility(View.GONE);
        } else {
            tv_out_download.setVisibility(View.VISIBLE);
        }

        tv_out_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilogDownLoad();
            }
        });

    }

    public void dilogDownLoad() {
        new AlertDialog.Builder(this)
                .setMessage("是否打开浏览器更新版本？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //引导用户至设置页手动授权
                        browerUpdate(url_down);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //引导用户手动授权，权限请求失败
                        dialog.dismiss();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //引导用户手动授权，权限请求失败
            }
        }).setCancelable(false).show();
    }

    public void browerUpdate(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        this.startActivity(intent);
    }


    private class ListDownloadListener extends DownloadListener {


        ListDownloadListener(Object tag) {
            super(tag);
        }

        @Override
        public void onStart(Progress progress) {
        }

        @Override
        public void onProgress(Progress progress) {
            refresh(progress);
        }

        @Override
        public void onError(Progress progress) {
            Throwable throwable = progress.exception;
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }


        @Override
        public void onFinish(File file, Progress progress) {
            mfile = file;
            LogUtil.e(file.getAbsolutePath());
            caiUtils.installProcess(MainDownloadAty.this,file, caiUtils.getPackageName() + ".FileProvider");
        }

        @Override
        public void onRemove(Progress progress) {
        }
    }

    public void refresh(Progress progress) {
        switch (progress.status) {
            case Progress.NONE:
                netSpeed2.setText("已停止");
                break;
            case Progress.PAUSE:
                netSpeed2.setText("暂停中");
                break;
            case Progress.ERROR:
                netSpeed2.setText("下载出错");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(MainAty.class);
                    finish();
                }
                }, 1500);
                break;
            case Progress.WAITING:
                netSpeed2.setText("等待中...");
                break;
            case Progress.FINISH:
                netSpeed2.setText("下载完成");
                break;
            case Progress.LOADING:
                String speed = Formatter.formatFileSize(this, progress.speed);
                netSpeed2.setText(String.format("%s/s", speed));
                break;
        }
        pbProgress2.setMax(10000);
        pbProgress2.setProgress((int) (progress.fraction * 10000));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 10086) {
            caiUtils.installProcess(MainDownloadAty.this,mfile, caiUtils.getPackageName() + ".FileProvider");
        }
    }
}
