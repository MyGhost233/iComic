package com.example.qiuchenly.comicparse.Simple;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Stack;

public class AppManager {
    private static Stack<Activity> mStack;
    private static AppManager Appm;

    public static AppManager getAppm() {
        if (mStack == null) mStack = new Stack<>();
        if (Appm == null) Appm = new AppManager();
        return Appm;
    }


    @TargetApi(19)
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup decorViewGroup = (ViewGroup) activity.getWindow().getDecorView();
            //获取自己布局的根视图
            View rootView = ((ViewGroup) (decorViewGroup.findViewById(android.R.id.content))).getChildAt(0);
            //预留状态栏位置
            rootView.setFitsSystemWindows(true);

            //添加状态栏高度的视图布局，并填充颜色
            View statusBarTintView = new View(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    getInternalDimensionSize(activity.getResources(), "status_bar_height"));
            params.gravity = Gravity.TOP;
            statusBarTintView.setLayoutParams(params);
            statusBarTintView.setBackgroundColor(color);
            decorViewGroup.addView(statusBarTintView);
        }
    }

    public static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (Appm == null) {
            Appm = new AppManager();
        }

        if (mStack == null) {
            mStack = new Stack<Activity>();
        }

        return Appm;
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    public static Activity getActivity(Class<?> cls) {
        if (mStack != null)
            for (Activity activity : mStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        mStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = mStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = mStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && mStack.contains(activity)) {
            mStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null && mStack.contains(activity)) {
            mStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mStack.size(); i < size; i++) {
            if (null != mStack.get(i)) {
                finishActivity(mStack.get(i));
            }
        }
        mStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {

        try {
            finishAllActivity();
            // System.exit(0);
        } catch (Exception e) {
        }

        // 获取packagemanager的实例
        try {
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            mStack = null;
            Appm = null;
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }

    }

}
