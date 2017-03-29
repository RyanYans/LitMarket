package com.rya.litmarket.global;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Process;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */

public class GlobalApplication extends Application {

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

/*
        // 初始化内存检测
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
*/
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
