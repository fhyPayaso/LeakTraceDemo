package com.bytedance.fanhongyu.scaffold.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource

/**
 *
 * @author fhyPayaso
 * @since 2019/3/5 8:52 PM
 */
class PagingDataSourceFactory<T>(val dataSource: PagingDataSource<T>) : DataSource.Factory<Int, T>() {

    private val mDataSource: MutableLiveData<PagingDataSource<T>> = MutableLiveData()

    init {
        mDataSource.value = dataSource
    }

    override fun create(): PagingDataSource<T>? = mDataSource.value
}