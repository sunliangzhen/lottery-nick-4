package com.example.machenike.myapplication;


import android.content.Context;

import com.example.machenike.myapplication.a.AppManager;
import com.example.machenike.myapplication.a.PreferencesUtils;

/**
 * Created by aa on 2017/5/4.
 */

public class Config {
    public static boolean isLogin() {
        return PreferencesUtils.getBoolean(AppManager.getInstance().getTopActivity(), "PREF_KEY_LOGIN_STATE");
    }
    public static boolean isLogin(Context context) {
        return PreferencesUtils.getBoolean(context, "PREF_KEY_LOGIN_STATE");
    }

    public static void setLoginState(boolean isLogin) {
        PreferencesUtils.putBoolean(AppManager.getInstance().getTopActivity(), "PREF_KEY_LOGIN_STATE", isLogin);
    }
}
