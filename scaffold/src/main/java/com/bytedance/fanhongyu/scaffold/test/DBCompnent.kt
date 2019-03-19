package com.bytedance.fanhongyu.scaffold.test

import dagger.Component

/**
 *
 * @author fhyPayaso
 * @since 2019/3/17 8:31 PM
 */
@Component
interface DBCompnent {

    fun inject(db: DB)
}