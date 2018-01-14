package com.example.a41061.leaktrace.until;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.example.a41061.leaktrace.TestApplication;


/**
 * @author abtion.
 * @since 17/10/24 20:18.
 * email caiheng@hrsoft.net.
 */

public final class ToastUtil {
    private static final int DURATION = Toast.LENGTH_SHORT;
    private static final boolean IS_SHOW_ERROR_CODE = true;

    public static void showToast(final String msg) {
        Utility.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TestApplication.getInstance(), msg, DURATION).show();
            }
        });
    }

    public static void showToast(@StringRes final int resId) {
        Utility.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TestApplication.getInstance(), resId, DURATION).show();
            }
        });
    }

    public static void showToast(String msg, int... errorCode) {
        if (IS_SHOW_ERROR_CODE) {
            for (int anErrorCode : errorCode) {
                msg = msg + "-" + anErrorCode;
            }
        }
        showToast(msg);
    }

    public static void showToast(@StringRes int resId, int... errorCode) {
        String msg = TestApplication.getInstance().getResources().getString(resId);
        if (IS_SHOW_ERROR_CODE) {
            for (int anErrorCode : errorCode) {
                msg = msg + "-" + anErrorCode;
            }
        }
        showToast(msg);
    }
}