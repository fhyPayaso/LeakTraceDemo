package com.example.a41061.leaktrace.test;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.AppCompatTextView;
import android.view.MotionEvent;
import android.widget.Scroller;


/**
 * @author FanHongyu.
 * @since 18/6/10 22:07.
 * email fanhongyu@hrsoft.net.
 */

public class MyMoveButton extends AppCompatTextView {

    private Scroller mScroller = new Scroller(getContext());

    private int mLastX;
    private int mLastY;

    public MyMoveButton(Context context) {
        super(context);
    }


    private void smoothScollto(int dx, int dy) {

        int sx = getScrollX();
        int delta = dx - sx;
        mScroller.startScroll(sx,0,delta,1000);



    }


    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            smoothScollto(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }

    }
}
