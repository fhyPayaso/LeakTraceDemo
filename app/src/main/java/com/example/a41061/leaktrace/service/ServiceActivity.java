package com.example.a41061.leaktrace.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.ToastUtil;

/**
 * @author FanHongyu.
 * @since 18/6/13 20:31.
 * email fanhongyu@hrsoft.net.
 */

public class ServiceActivity extends AppCompatActivity {

    //private TestService.MyBinder mBinder;

//    private ServiceConnection mConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mBinder = (TestService.MyBinder) service;
//            String str = mBinder.getString();
//            ToastUtil.showToast(str);
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mBinder = null;
//        }
//    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Intent intent = new Intent(ServiceActivity.this, TestService.class);
        findViewById(R.id.btn_start).setOnClickListener(v -> startService(intent));
        findViewById(R.id.btn_end).setOnClickListener(v -> stopService(intent));
    }
}
