package com.bytedance.fanhongyu.comone;

import com.bytedance.fanhongyu.comoneapi.ComOneService;
import dagger.Module;
import dagger.Provides;

/**
 * @author fanhongyu
 * @since 2019/5/7 9:20 PM
 */
@Module
public class ComOneModule {

    @Provides
    public ComOneService provideComOneService() {
        return new ComOneServiceImpl();
    }
}
