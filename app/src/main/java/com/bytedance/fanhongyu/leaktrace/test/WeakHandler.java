package com.bytedance.fanhongyu.leaktrace.test;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author fhyPayaso
 * @since 2019/2/27 5:23 PM
 */
public class WeakHandler extends Handler {

    private WeakReference<IHandler> mRef;

    // 使用的类直接实现，handleMessage方法中写具体逻辑
    public interface IHandler {
        void handleMessage(Message msg);
    }

    //初始化时传入自身
    public WeakHandler(IHandler context) {
        mRef = new WeakReference<>(context);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mRef.get() != null && msg != null) {
            mRef.get().handleMessage(msg);
        }
    }

    public void clear() {
        // 这里是为了防止队列中的Message持有自身，外部销毁时调用
        this.removeCallbacksAndMessages(null);
    }
}
