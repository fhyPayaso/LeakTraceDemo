package com.example.a41061.leaktrace;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.leakcanary.LeakCanary;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {


    Button button;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Handler mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 0:
//                        // do something
//                        break;
//                    case 1:
//                        // do something
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };

//
//        mHandler.sendEmptyMessage(0);
//        String asa;
//        context = this;
//        setButton();
    }



    public void setButton() {

        button = (Button) findViewById(R.id.btn_first);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
