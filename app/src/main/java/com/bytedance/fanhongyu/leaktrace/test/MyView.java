package com.bytedance.fanhongyu.leaktrace.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author fhyPayaso
 * @since 2019/3/4 10:57 AM
 */
public class MyView extends View {

    private Paint mPaint;

    public MyView(Context context) {
        super(context);
        initView();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ffffff"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x, y = 10;
        for (int i = 0; i < 500; i++) {
            x = i * 20;
            canvas.drawCircle(x, y, 5, mPaint);
        }
        super.onDraw(canvas);
    }
}
