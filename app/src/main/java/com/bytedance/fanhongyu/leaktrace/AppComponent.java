package com.bytedance.fanhongyu.leaktrace;

import com.bytedance.fanhongyu.comone.ComOneComponent;
import com.bytedance.fanhongyu.comone.ComOneModule;
import com.bytedance.fanhongyu.comtwo.ComTwoComponent;
import com.bytedance.fanhongyu.comtwo.ComTwoModule;
import com.bytedance.fanhongyu.leaktrace.animation.AnimationActivity;
import dagger.Component;

/**
 * @author fanhongyu
 * @since 2019/5/7 9:28 PM
 */
@Component(modules = {ComOneModule.class, ComTwoModule.class})
public interface AppComponent extends ComOneComponent, ComTwoComponent {


    void inject(AnimationActivity animationActivity);

}
