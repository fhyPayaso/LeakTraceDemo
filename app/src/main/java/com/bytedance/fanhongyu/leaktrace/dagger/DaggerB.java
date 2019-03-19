package com.bytedance.fanhongyu.leaktrace.dagger;

import okhttp3.OkHttpClient;

import javax.inject.Inject;

/**
 * @author fhyPayaso
 * @since 2019/3/17 5:31 PM
 */
public class DaggerB {

    @Inject
    DaggerA daggerA;

    @Inject
    OkHttpClient client;

    public DaggerB() {
        DaggerBComponent.builder().build().inject(this);
    }

    public void daggerB() {
        daggerA.daggerA();
    }

    public void okHttp() {
        System.out.println(client == null);
    }
}
