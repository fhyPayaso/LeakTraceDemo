package com.bytedance.fanhongyu.leaktrace.test.kotlin

/**
 *  kotlin数据类
 * @author fhyPayaso
 * @since 2019/2/28 2:49 PM
 */
data class Artist(
    var id: Long,
    var name: String,
    var url: String,
    var mbid: String
)

/**
 * 会根据变量自动生成get/set方法
 *
 * 这种写法实例化的时候需要全部参数
 */

