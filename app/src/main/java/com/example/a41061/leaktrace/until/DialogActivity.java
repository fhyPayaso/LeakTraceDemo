package com.example.a41061.leaktrace.until;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.a41061.leaktrace.R;

/**
 * @author fhyPayaso
 * @since 2018/3/31 on 下午9:34
 * fhyPayaso@qq.com
 */
public class DialogActivity extends AppCompatActivity {


    private  static final String TAG = "DialogActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Log.i(TAG, "onCreate:");
    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.i(TAG, "onBackPressed: 点击返回");
    }
}
