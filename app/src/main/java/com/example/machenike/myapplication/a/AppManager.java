package com.example.machenike.myapplication.a;

import android.app.Activity;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author Zero
 * @date 2016/4/13 9:01
 */
public class AppManager {
    private static Stack<Activity> mActivityStack;
    private static AppManager mAppManager;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }

        return mAppManager;
    }

    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack();
        }

        mActivityStack.add(activity);
    }

    public Activity getTopActivity() {
        Activity activity = (Activity) mActivityStack.lastElement();
        return activity;
    }

    public void killTopActivity() {
        Activity activity = (Activity) mActivityStack.lastElement();
        this.killActivity(activity);
    }

    public void killActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }

    }

    public void killtoActivity(Class<?> cls) {
        Iterator iterator = mActivityStack.iterator();

        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (!activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
                activity = null;
            }
        }
    }

    public int SelectActivity(Class<?> cls) {
        int index = 0;
        Iterator iterator = mActivityStack.iterator();

        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (activity.getClass().equals(cls)) {
                index = 1;
            }
        }
        return index;
    }

    public Activity selectActivity(Class<?> cls) {

        Activity mActivity = null;
        Iterator iterator = mActivityStack.iterator();

        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (activity.getClass().equals(cls)) {
                mActivity = activity;
            }
        }
        return mActivity;
    }


    public void killActivity(Class<?> cls) {
        Iterator iterator = mActivityStack.iterator();

        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
                activity = null;
            }
        }

    }

    public void killAllActivity() {
        int i = 0;
        for (int size = mActivityStack.size(); i < size; ++i) {
            if (null != mActivityStack.get(i)) {
                ((Activity) mActivityStack.get(i)).finish();
            }
        }

        mActivityStack.clear();
    }

    public void AppExit(Context context) {
        try {
            this.killAllActivity();
//            ActivityManager e = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            e.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception var3) {
        }
    }
}
