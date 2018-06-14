package com.example.a41061.leaktrace.test;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author FanHongyu.
 * @since 18/6/10 16:28.
 * email fanhongyu@hrsoft.net.
 */

public class MyView extends LinearLayout {
    public MyView(Context context) {
        super(context);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
