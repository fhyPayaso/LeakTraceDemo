package com.bytedance.fanhongyu.leaktrace.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author fhyPayaso
 * @since 2019/3/17 9:53 PM
 */
public class Surface extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder mSurfaceHolder;

    public Surface(Context context) {
        super(context);
    }

    public Surface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Surface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
