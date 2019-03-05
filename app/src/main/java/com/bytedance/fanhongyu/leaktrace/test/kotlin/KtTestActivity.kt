package com.bytedance.fanhongyu.leaktrace.test.kotlin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.bytedance.fanhongyu.leaktrace.R
import java.net.URL

/**
 *
 * @author fhyPayaso
 * @since 2019/2/28 2:35 PM
 */
class KtTestActivity : AppCompatActivity() {


    var not: Artist? = null

    lateinit var not2: Artist


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)


        val notNull: Artist? = null

        val aaa = Artist(1111, "", "", "")
        aaa.toString()
    }

    private val items = listOf(
        "",
        ""
    )

    private fun Context.toast() {
    }

    fun KtTestActivity.test() {
        var a = mutableListOf("", "asd")
        toast()
    }


}