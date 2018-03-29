package com.example.a41061.leaktrace.until;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * @author FanHongyu.
 * @since 18/1/22 12:45.
 * email fanhongyu@hrsoft.net.
 */

public class AppNetWorkUtil {

    /**
     * 未找到合适匹配网络类型
     */
    public static final int TYPE_NO = 0;

    /**
     * WIFI网络
     */
    public static final int TYPE_WIFI = 1;


    /**
     * 移动网络
     */
    public static final int TYPE_MOBILE = 2;

    /**
     * 网络类型 - 无连接
     */
    public static final int NETWORK_TYPE_NO_CONNECTION = -1231545315;





    /**
     * 获取当前网络的状态
     *
     * @param context 上下文
     * @return 当前网络的状态。没有网络连接时返回null
     */
    public static NetworkInfo.State getCurrentNetworkState(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.getState() : null;
    }


    /**
     * 判断当前网络是否已经连接
     *
     * @param context 上下文
     * @return 当前网络是否已经连接。false：尚未连接
     */
    public static boolean isConnected(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.CONNECTED;
    }

    /**
     * 判断当前网络是否正在连接
     *
     * @param context 上下文
     * @return 当前网络是否正在连接
     */
    public static boolean isConnecting(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.CONNECTING;
    }


    /**
     * 判断当前网络是否已经断开
     *
     * @param context 上下文
     * @return 当前网络是否已经断开
     */
    public static boolean isDisconnected(Context context) {
        return getCurrentNetworkState(context) ==
                NetworkInfo.State.DISCONNECTED;
    }


    /**
     * 判断当前网络是否正在断开
     *
     * @param context 上下文
     * @return 当前网络是否正在断开
     */
    public static boolean isDisconnecting(Context context) {
        return getCurrentNetworkState(context) ==
                NetworkInfo.State.DISCONNECTING;
    }


    /**
     * 判断当前网络是否已经暂停
     *
     * @param context 上下文
     * @return 当前网络是否已经暂停
     */
    public static boolean isSuspended(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.SUSPENDED;
    }


    /**
     * 判断当前网络是否处于未知状态中
     *
     * @param context 上下文
     * @return 当前网络是否处于未知状态中
     */
    public static boolean isUnknown(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.UNKNOWN;
    }



    /**
     * 获取当前连接网络类型
     * @param context
     * @return
     */
    public static int getNetWorkState(Context context) {


        if (isConnected(context)) {

            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info != null && info.isAvailable()) {
                int currentType = info.getType();
                if (currentType == ConnectivityManager.TYPE_WIFI) {

                    Log.i(TAG, "getNetWorkState: 当前网络类型为WIFI");
                    return TYPE_WIFI;
                } else if (currentType == ConnectivityManager.TYPE_MOBILE) {
                    Log.i(TAG, "getNetWorkState: 当前网络类型为移动网络");
                    return TYPE_MOBILE;
                }

            } else {
                Log.i(TAG, "getNetWorkState: 未知网络类型");
                return TYPE_NO;
            }
        }

        Log.i(TAG, "getNetWorkState: 当前未连接网络");
        return NETWORK_TYPE_NO_CONNECTION;
    }
}
