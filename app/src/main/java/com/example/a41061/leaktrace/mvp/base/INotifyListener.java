package com.example.a41061.leaktrace.mvp.base;

/**
 * 成功失败回调
 *
 * @author FanHongyu.
 * @since 18/4/18 13:36.
 * email fanhongyu@hrsoft.net.
 */

public interface INotifyListener {

    void onFailed(Exception e);

    void onSuccess();
}