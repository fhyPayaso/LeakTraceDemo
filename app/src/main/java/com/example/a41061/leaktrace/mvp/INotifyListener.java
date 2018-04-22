package com.example.a41061.leaktrace.mvp;

/**
 * @author FanHongyu.
 * @since 18/4/18 13:36.
 * email fanhongyu@hrsoft.net.
 */

public interface INotifyListener {

    void onFailed(Exception e);

    void onSuccess();
}