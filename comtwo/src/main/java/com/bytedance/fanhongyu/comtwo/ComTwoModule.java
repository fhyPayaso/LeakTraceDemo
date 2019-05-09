package com.bytedance.fanhongyu.comtwo;

import com.bytedance.fanhongyu.comtwoapi.ComTwoService;
import dagger.Module;
import dagger.Provides;

/**
 * @author fanhongyu
 * @since 2019/5/7 9:25 PM
 */
@Module
public class ComTwoModule {

    @Provides
    public ComTwoService provideComTwoService() {
        return new ComTwoServiceImpl();
    }

}
