package com.example.a41061.leaktrace;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import java.util.Iterator;
import java.util.List;

import static com.example.a41061.leaktrace.BuildConfig.DEBUG;
import static com.hyphenate.chat.EMGCMListenerService.TAG;


/**
 * @author fhyPayaso
 * @since 2017/12/14 10:16
 * email fhyPayaso@qq.com
 */

public class TestApplication extends Application {


    Context mAppContext;
    private static TestApplication instance;
    public static Context appContext;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        appContext = this;


        initTbs();


        //initEM();
    }


    private void initTbs() {

        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {


                Log.i(TAG, "onViewInitFinished: x5加载成功？" + b);
            }
        });
    }


    /**
     * 初始化环信
     */
    private void initEM() {
        int pid = android.os.Process.myPid();
        String processName = getProcessAppName(pid);
        if (processName == null || !processName.equalsIgnoreCase(appContext.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        options.setAutoLogin(false);
        EMClient.getInstance().init(appContext, options);
        EMClient.getInstance().setDebugMode(DEBUG);
    }

    /**
     * 获取processAppName
     *
     * @param pID pid
     * @return name
     */
    private String getProcessAppName(int pID) {
        String processName = null;
        ActivityManager activityManager = (ActivityManager) appContext.getSystemService(ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) iterator.next();
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    public static TestApplication getInstance() {
        return instance;
    }
}
