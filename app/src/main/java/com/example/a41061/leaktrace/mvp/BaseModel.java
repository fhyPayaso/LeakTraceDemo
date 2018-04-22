package com.example.a41061.leaktrace.mvp;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;


/**
 * @author FanHongyu.
 * @since 18/4/18 13:36.
 * email fanhongyu@hrsoft.net.
 */

public abstract class BaseModel<T> implements WeakHandler.IHandler {

    protected static final int MSG_REQUEST = 0;
    protected boolean mIsLoading;
    protected WeakHandler mHandler;
    protected T mData;
    protected List<INotifyListener> mNotifyListeners;

    public void addNotifyListener(INotifyListener listener) {
        if (listener == null) throw new NullPointerException("INotifyListener could not be null");
        if (mNotifyListeners == null) {
            mNotifyListeners = new LinkedList<>();
        }
        mNotifyListeners.add(listener);
    }

    public BaseModel() {
        mIsLoading = false;
        mHandler = new WeakHandler(Looper.getMainLooper(), this);
    }

    public void clearNotifyListener(INotifyListener listener) {
        if (listener == null) return;
        if (mNotifyListeners != null) {
            mNotifyListeners.remove(listener);
        }
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    protected void handleData(T data) {
        mData = data;
    }

    protected boolean sendRequest(Object... params) {
        if (checkParams(params)) {
            mIsLoading = true;
            return true;
        } else {
            //Logger.e(getClass().getSimpleName(),"sendRequest: params invalid(参数数组长度不符)");
            return false;
        }
    }

    @Override
    public void handleMsg(Message msg) {
        mIsLoading = false;
        if (msg.obj instanceof Exception) {
            Log.e("Snow", "exception: ");
            ((Exception) msg.obj).printStackTrace();
            if (mNotifyListeners != null) {
                for (INotifyListener listener : mNotifyListeners) {
                    listener.onFailed((Exception) msg.obj);
                }
            }
            return;
        }
        handleData((T) msg.obj);
        if (mNotifyListeners != null) {
            for (INotifyListener listener : mNotifyListeners) {
                listener.onSuccess();
            }
        }
    }

    protected abstract boolean checkParams(Object... params);

    public T getData() {
        return mData;
    }
}