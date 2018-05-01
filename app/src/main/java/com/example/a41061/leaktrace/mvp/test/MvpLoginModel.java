package com.example.a41061.leaktrace.mvp.test;

import com.example.a41061.leaktrace.mvp.base.BaseModel;

/**
 * @author FanHongyu.
 * @since 18/4/22 18:33.
 * email fanhongyu@hrsoft.net.
 */

public class MvpLoginModel extends BaseModel<UserBean>{

    @Override
    protected boolean checkParams(Object... params) {
        return false;
    }
}
