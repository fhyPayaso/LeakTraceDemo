package com.bytedance.fanhongyu.leaktrace.dagger;

import javax.inject.Inject;

/**
 * @author fhyPayaso
 * @since 2019/3/17 5:30 PM
 */
public class DaggerA {

    @Inject
    public DaggerA() {
    }

    public void daggerA() {
        System.out.println("daggerA");
    }
}
