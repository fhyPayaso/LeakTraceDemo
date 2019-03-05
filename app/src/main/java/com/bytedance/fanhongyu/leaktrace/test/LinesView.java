package com.bytedance.fanhongyu.leaktrace.test;

/**
 * @author fhyPayaso
 * @since 2019/3/3 6:29 PM
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class LinesView extends View {

    private Paint mPaint;
    private Path mPath;

    public LinesView(Context context) {
        this(context, null);
    }

    public LinesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(5000, 500);
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);


        mPath = new Path();
        mPath.moveTo(0, 30);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = 0, y = 30;
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            x += 50;
            mPath.lineTo(x, y);
            y = random.nextInt(300) + 1;
            mPath.moveTo(x, y);
            canvas.drawPath(mPath, mPaint);
        }

    }
}
