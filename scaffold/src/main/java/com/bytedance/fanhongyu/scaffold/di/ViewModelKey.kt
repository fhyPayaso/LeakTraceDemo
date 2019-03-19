package com.bytedance.fanhongyu.scaffold.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 *
 * @author fhyPayaso
 * @since 2019/3/17 8:48 PM
 */
@Target(AnnotationTarget.FUNCTION)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
