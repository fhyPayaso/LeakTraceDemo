package com.bytedance.fanhongyu.scaffold.paging

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.bytedance.fanhongyu.scaffold.data.NetworkStatus
import com.bytedance.fanhongyu.scaffold.data.PagingDataSource
import com.bytedance.fanhongyu.scaffold.data.PagingDataSourceFactory

/**
 *
 * @author fhyPayaso
 * @since 2019/3/5 7:45 PM
 */

abstract class PagingViewModel<T> : ViewModel() {

    private val INIT_LOAD_SIZE: Int = 20

    private val PAGE_SIZE = 20

    private var mDataList: LiveData<PagedList<T>>? = null

    private var mFactory: PagingDataSourceFactory<T>? = null

    private var mLoadingStatus: MutableLiveData<NetworkStatus> = MutableLiveData()

    private var mRefrshStatus: MutableLiveData<NetworkStatus> = MutableLiveData()

    private var isEmpty: MutableLiveData<Boolean> = MutableLiveData()

    private var hasMore: MutableLiveData<Boolean> = MutableLiveData()


    init {

    }

//    private fun buildPagedList() = LivePagedListBuilder(mFactory,getConfig()).setBoundaryCallback(PagedList.BoundaryCallback()).build()


    protected abstract fun getDataSource(): PagingDataSource<T>

    private fun getConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(INIT_LOAD_SIZE)
        .setEnablePlaceholders(false)
        .setPageSize(PAGE_SIZE)
        .build()

}