package com.bytedance.fanhongyu.leaktrace.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.bytedance.fanhongyu.leaktrace.R;

/**
 * @author fhyPayaso
 * @since 2019/2/26 2:14 PM
 */
public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 第一个动画是新页面进入，第二个是老页面退出
        overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out);
    }
}
