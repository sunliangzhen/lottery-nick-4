package com.example.machenike.myapplication.a;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.machenike.myapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * @author Zero
 * @date 2016/4/15 10:13
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    public static int getImageResourceId(String name) {
        R.drawable drawables = new R.drawable();
        int resId = 0x7f02000b;
        try {
            java.lang.reflect.Field field = R.drawable.class.getField(name);
            resId = (Integer) field.get(drawables);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resId;
    }
}
