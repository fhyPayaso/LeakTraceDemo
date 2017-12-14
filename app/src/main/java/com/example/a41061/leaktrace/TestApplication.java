package com.example.a41061.leaktrace;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;


/**
 * @author fhyPayaso
 * @since 2017/12/14 10:16
 * email fhyPayaso@qq.com
 */

public class TestApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
