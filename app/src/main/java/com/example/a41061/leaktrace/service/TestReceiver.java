package com.example.a41061.leaktrace.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.a41061.leaktrace.until.ToastUtil;

/**
 * @author FanHongyu.
 * @since 18/8/1 09:42.
 * email fanhongyu@hrsoft.net.
 */

public class TestReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        String data = intent.getStringExtra("data");
        ToastUtil.showToast("第一个广播 : "+data);

        Bundle bundle = new Bundle();
        bundle.putString("data","来自第一个广播");
        setResultExtras(bundle);
    }
}
