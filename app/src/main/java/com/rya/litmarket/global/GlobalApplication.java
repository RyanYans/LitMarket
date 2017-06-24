package com.rya.litmarket.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Process;

import com.squareup.leakcanary.LeakCanary;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */

public class GlobalApplication extends Application {

    //运用list来保存们每一个activity是关键
    private List<Activity> mList = new LinkedList<Activity>();
    //为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static GlobalApplication ginstance;
    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    public static int getSdkVersion() {
        return sdkVersion;
    }

    private static int sdkVersion;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = Process.myTid();
        sdkVersion = Build.VERSION.SDK_INT;

        // 初始化内存检测
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    //实例化一次
    public synchronized static GlobalApplication getInstance() {
        if (null == ginstance) {
            ginstance = new GlobalApplication();
        }
        return ginstance;
    }

    //添加要退出的Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    //关闭每一个list内的activity
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

}
