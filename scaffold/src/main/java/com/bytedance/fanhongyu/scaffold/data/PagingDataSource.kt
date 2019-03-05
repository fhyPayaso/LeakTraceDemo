package com.bytedance.fanhongyu.scaffold.data

import android.arch.paging.DataSource
import android.arch.paging.PositionalDataSource

/**
 *
 * @author fhyPayaso
 * @since 2019/3/5 8:29 PM
 */
class PagingDataSource<T> : PositionalDataSource<T>(), DataSource.InvalidatedCallback {










    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInvalidated() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}