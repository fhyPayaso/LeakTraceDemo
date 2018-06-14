package com.example.a41061.leaktrace.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

/**
 * @author FanHongyu.
 * @since 18/6/14 20:33.
 * email fanhongyu@hrsoft.net.
 */

public class TestIntentService extends IntentService{

    public TestIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
