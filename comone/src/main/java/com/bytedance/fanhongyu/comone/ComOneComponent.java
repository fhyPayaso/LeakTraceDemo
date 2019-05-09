package com.bytedance.fanhongyu.comone;

import com.bytedance.fanhongyu.comoneapi.ComOneService;

/**
 * @author fanhongyu
 * @since 2019/5/7 9:33 PM
 */
public interface ComOneComponent {

    void inject(ComOneService service);

    void inject(OneActivity activity);

}
