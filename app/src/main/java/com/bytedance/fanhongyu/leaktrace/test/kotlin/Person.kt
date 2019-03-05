package com.bytedance.fanhongyu.leaktrace.test.kotlin

/**
 *
 * @author fhyPayaso
 * @since 2019/2/28 4:04 PM
 */
public class Person {

    var name: String = ""
        get() = field.toUpperCase()
        set(value) {
            field = "ssss$value"
        }


    var bbb: String = ""
        get() = "aaa"
        set(value) {
            field = "aaaaa$value"
        }


}