package com.bytedance.fanhongyu.leaktrace.dagger;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * @author fhyPayaso
 * @since 2019/3/17 6:07 PM
 */
@Module
public class BModule {

    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

}
