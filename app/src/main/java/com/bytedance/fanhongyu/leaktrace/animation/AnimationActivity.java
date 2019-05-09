package com.bytedance.fanhongyu.leaktrace.animation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.bytedance.fanhongyu.comone.OneActivity;
import com.bytedance.fanhongyu.core.Graph;
import com.bytedance.fanhongyu.leaktrace.AppComponent;
import com.bytedance.fanhongyu.leaktrace.R;

/**
 * @author fhyPayaso
 * @since 2019/2/26 2:14 PM
 */
public class AnimationActivity extends AppCompatActivity {


    private Handler handler;

//    @Inject
//    ComOneService comOneService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Graph.getInstance().getGraph(AppComponent.class).inject(this);

//        ComOneService comOneService = new ComOneServiceImpl();
//        comOneService.printComOne("testCom");

        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimationActivity.this, OneActivity.class);
                startActivity(intent);
            }
        });

//        ComTwoService service = (ComTwoService) getInstance().get(ComTwoService.class);
//        service.printComTwo("");
//        if (comOneService != null) {
//        comOneService.printComOne("testCom");
//        }
    }

}
