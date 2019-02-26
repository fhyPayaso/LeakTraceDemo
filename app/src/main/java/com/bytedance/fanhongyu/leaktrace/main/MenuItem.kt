package com.bytedance.fanhongyu.leaktrace.main

/**
 *
 * @author fhyPayaso
 * @since 2019/2/26 4:41 PM
 */
data class MenuItem(
    var description: String,
    var activity: Class<*>
)