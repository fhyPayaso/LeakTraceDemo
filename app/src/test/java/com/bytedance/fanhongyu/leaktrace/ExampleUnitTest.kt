package com.bytedance.fanhongyu.leaktrace

import com.bytedance.fanhongyu.leaktrace.dagger.DaggerB
import com.bytedance.fanhongyu.leaktrace.kotlin.TestKT
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun isCorrect() {

        val kt = TestKT()

//        kt.runt()

        kt.run {
            this.runt()
        }

    }
}
