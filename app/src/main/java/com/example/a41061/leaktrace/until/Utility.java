package com.example.a41061.leaktrace.until;

import android.os.Handler;
import android.os.Looper;

/**
 * @author abtion.
 * @since 17/10/23 20:01.
 * email caiheng@hrsoft.net.
 */

public final class Utility {
    private static Handler uiHandle;

    private Utility() {
    }

    /**
     * 获取UI线程
     */
    private static void getUIHandle() {
        if (uiHandle == null) {
            synchronized (Utility.class) {
                if (uiHandle == null) {
                    uiHandle = new Handler(Looper.getMainLooper());
                }
            }
        }
    }

    /**
     * UI线程中运行
     */
    public static void runOnUiThread(Runnable runnable) {
        runOnUiThread(runnable, 0);
    }

    /**
     * UI线程中运行
     */
    public static void runOnUiThread(Runnable runnable, long delayMills) {
        getUIHandle();
        uiHandle.postDelayed(runnable, delayMills);
    }

    /**
     * 在新线程中运行
     */
    public static void runOnNewThread(Runnable runnable) {
        new Thread(runnable).start();
    }

}
