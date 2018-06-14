package com.example.a41061.leaktrace.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.a41061.leaktrace.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author FanHongyu.
 * @since 18/4/2 15:31.
 * email fanhongyu@hrsoft.net.
 */

public class ActivityB extends AppCompatActivity {




    @BindView(R.id.txt_name)
    TextView txtName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.txt_name)
    public void onViewClicked() {
        txtName.scrollTo(50,50);
    }
}
