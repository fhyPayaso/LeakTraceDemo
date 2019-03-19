package com.bytedance.fanhongyu.scaffold.test

import javax.inject.Inject

/**
 *
 * @author fhyPayaso
 * @since 2019/3/17 8:29 PM
 */
class DB() {

    @Inject
    lateinit var da: DA

    init {
        DaggerDBCompnent.create().inject(this)
    }


    public fun db() {
        da.da()
    }



}