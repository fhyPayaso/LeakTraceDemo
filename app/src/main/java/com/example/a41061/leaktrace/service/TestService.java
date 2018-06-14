package com.example.a41061.leaktrace.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.a41061.leaktrace.until.ToastUtil;

import static com.example.a41061.leaktrace.socket.ClientActivity.TAG;

/**
 * @author FanHongyu.
 * @since 18/6/13 20:28.
 * email fanhongyu@hrsoft.net.
 */

public class TestService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {

        ToastUtil.showToast(intent.getStringExtra("name"));

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    public class MyBinder extends Binder {

        public String getString() {
            return "连接成功";
        }
    }
}
