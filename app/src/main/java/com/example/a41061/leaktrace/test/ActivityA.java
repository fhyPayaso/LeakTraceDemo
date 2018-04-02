package com.example.a41061.leaktrace.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a41061.leaktrace.R;

/**
 * @author FanHongyu.
 * @since 18/4/2 15:30.
 * email fanhongyu@hrsoft.net.
 */

public class ActivityA extends AppCompatActivity{


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
    }
}
