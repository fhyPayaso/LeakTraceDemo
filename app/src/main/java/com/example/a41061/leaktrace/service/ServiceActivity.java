package com.example.a41061.leaktrace.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.a41061.leaktrace.R;

/**
 * @author FanHongyu.
 * @since 18/6/13 20:31.
 * email fanhongyu@hrsoft.net.
 */

public class ServiceActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);


        Intent intent = new Intent(ServiceActivity.this,TestService.class);
        findViewById(R.id.btn_start).setOnClickListener(v-> startService(intent));
        findViewById(R.id.btn_end).setOnClickListener(v-> stopService(intent));
    }
}
