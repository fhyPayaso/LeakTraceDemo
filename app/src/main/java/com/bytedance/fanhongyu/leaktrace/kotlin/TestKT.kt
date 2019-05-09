package com.bytedance.fanhongyu.leaktrace.kotlin

/**
 *
 * @author fanhongyu
 * @since 2019/3/27 7:49 PM
 */
class TestKT {

    fun runt() {


        var mood = "I am sad"

        var onHasMore: ((Boolean) -> Unit)? = null

        onHasMore?.also {
            it(true)
        }

        run {

        }


        println(run {


        })

    }

}