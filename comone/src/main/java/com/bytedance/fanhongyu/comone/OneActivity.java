package com.bytedance.fanhongyu.comone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.bytedance.fanhongyu.comtwoapi.ComTwoService;
import com.bytedance.fanhongyu.core.Graph;

import javax.inject.Inject;

/**
 * @author fanhongyu
 * @since 2019/5/7 11:42 PM
 */
public class OneActivity extends AppCompatActivity {

    @Inject
    ComTwoService comTwoService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        Graph.getInstance().getGraph(ComOneComponent.class).inject(this);
        comTwoService.printComTwo("aaaaa");
        findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comTwoService.startTwoActivity(OneActivity.this);
            }
        });
    }
}
