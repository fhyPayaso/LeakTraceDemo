package com.example.a41061.leaktrace.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.a41061.leaktrace.until.ToastUtil;

import java.io.File;

import static com.example.a41061.leaktrace.socket.ClientActivity.TAG;

/**
 * @author FanHongyu.
 * @since 18/8/1 11:01.
 * email fanhongyu@hrsoft.net.
 */

public class SecondReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "onReceive: =========>>>>");
        Bundle bundle = getResultExtras(false);
        ToastUtil.showToast("第二个广播 : "+bundle.getString("data"));
    }
}
