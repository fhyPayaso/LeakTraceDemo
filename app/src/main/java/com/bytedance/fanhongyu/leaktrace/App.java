package com.bytedance.fanhongyu.leaktrace;

import android.app.Application;

import com.bytedance.fanhongyu.comone.ComOneServiceImpl;
import com.bytedance.fanhongyu.comoneapi.ComOneService;
import com.bytedance.fanhongyu.comtwo.ComTwoServiceImpl;
import com.bytedance.fanhongyu.comtwoapi.ComTwoService;
import com.bytedance.fanhongyu.core.Graph;

/**
 * @author fhyPayaso
 * @since 2019/2/26 3:10 PM
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // host层拿到全局的Component并交给core
        AppComponent component = DaggerAppComponent.builder().build();
        Graph.getInstance().init(component);

        //手动注册
        Graph.getInstance().register(ComOneService.class, new ComOneServiceImpl());
        Graph.getInstance().register(ComTwoService.class, new ComTwoServiceImpl());
    }
}
