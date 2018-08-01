package com.example.a41061.leaktrace.service;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author FanHongyu.
 * @since 18/7/31 10:45.
 * email fanhongyu@hrsoft.net.
 */

public class AnimActivity extends AppCompatActivity {


    TestReceiver mTestReceiver;
    SecondReceiver mSecondReceiver;
    LocalBroadcastManager mLocalBroadcastManager;

    @BindView(R.id.btn_start)
    Button btnStart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);


        ButterKnife.bind(this);
        mTestReceiver = new TestReceiver();
        mSecondReceiver = new SecondReceiver();


        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("action");
        intentFilter1.setPriority(1000);


        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("action");
        intentFilter2.setPriority(500);

        registerReceiver(mTestReceiver, intentFilter1);
        registerReceiver(mSecondReceiver,intentFilter2);


    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mTestReceiver);
    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {



        Intent intent = new Intent();
        intent.setAction("action");
        intent.putExtra("data", "来自activity");
        sendOrderedBroadcast(intent,null);

    }
}
