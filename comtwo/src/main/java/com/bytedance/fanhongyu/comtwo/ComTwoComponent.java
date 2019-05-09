package com.bytedance.fanhongyu.comtwo;

import com.bytedance.fanhongyu.comtwoapi.ComTwoService;

/**
 * @author fanhongyu
 * @since 2019/5/7 9:34 PM
 */
public interface ComTwoComponent {

    void inject(ComTwoService service);
}
