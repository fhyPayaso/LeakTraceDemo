package com.bytedance.fanhongyu.leaktrace.dagger;

import dagger.Component;

/**
 * @author fhyPayaso
 * @since 2019/3/17 5:35 PM
 */
@Component(modules = BModule.class)
public interface BComponent {

    void inject(DaggerB daggerB);
}
