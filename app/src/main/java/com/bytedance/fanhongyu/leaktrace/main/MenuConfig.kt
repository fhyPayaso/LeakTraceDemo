package com.bytedance.fanhongyu.leaktrace.main

import com.bytedance.fanhongyu.leaktrace.animation.AnimationActivity
import com.bytedance.fanhongyu.leaktrace.test.kotlin.KtTestActivity
import com.bytedance.fanhongyu.leaktrace.test.TestActivity
import java.util.ArrayList


/**
 *
 * @author fhyPayaso
 * @since 2019/2/26 4:44 PM
 */
class MenuConfig private constructor() {

    private var mMeumList = ArrayList<MenuItem>()

    init {

    }

    // 写在此处的都相当于静态变量
    companion object {

        private val INSTANCE = MenuConfig()

        fun inst(): MenuConfig {
            return INSTANCE
        }
    }

    fun getMenuData(): ArrayList<MenuItem> {
        if (mMeumList.isEmpty()) {
            mMeumList.add(MenuItem("Animation", AnimationActivity::class.java))
            mMeumList.add(MenuItem("JavaTest", TestActivity::class.java))
            mMeumList.add(MenuItem("KotlinTest", KtTestActivity::class.java))
        }
        return mMeumList
    }
}