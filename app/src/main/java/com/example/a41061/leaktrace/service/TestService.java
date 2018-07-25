package com.example.a41061.leaktrace.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author FanHongyu.
 * @since 18/6/13 20:28.
 * email fanhongyu@hrsoft.net.
 */

public class TestService extends Service {

    public static final int SERVICE_ID = 1001;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        Intent innerIntent = new Intent(this, InnerService.class);
//        startService(innerIntent);
        startForeground(SERVICE_ID, new Notification());
    }

    public static class InnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
