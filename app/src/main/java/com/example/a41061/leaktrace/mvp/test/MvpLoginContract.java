package com.example.a41061.leaktrace.mvp.test;

import com.example.a41061.leaktrace.mvp.base.IBaseView;

/**
 * @author FanHongyu.
 * @since 18/4/22 18:34.
 * email fanhongyu@hrsoft.net.
 */

public interface MvpLoginContract {

    interface Persenter {

        /**
         * P层登录方法
         *
         * @param username
         * @param password
         */
        void login(String username, String password);
    }

    interface View extends IBaseView {

        void onLoginSuccess();

        void onLoginFasle(String error);
    }
}
