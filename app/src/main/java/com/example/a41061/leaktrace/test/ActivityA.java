package com.example.a41061.leaktrace.test;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a41061.leaktrace.R;

/**
 * @author FanHongyu.
 * @since 18/4/2 15:30.
 * email fanhongyu@hrsoft.net.
 */

public class ActivityA extends AppCompatActivity implements ServiceConnection{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        findViewById(R.id.btn_show_activity_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityA.this,ActivityB.class));
                startActivity(new Intent(ActivityA.this,ActivityC.class));
                startActivity(new Intent(ActivityA.this,ActivityB.class));
            }
        });
        bindService(new Intent(ActivityA.this,MyService.class),this,0);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
