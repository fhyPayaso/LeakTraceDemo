package com.bytedance.fanhongyu.scaffold.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList

/**
 *
 * @author fhyPayaso
 * @since 2019/3/5 9:18 PM
 */
class PagingBoundaryCallback<T>(hasMore: MutableLiveData<Boolean>, isEmpty: MutableLiveData<Boolean>) :
    PagedList.BoundaryCallback<T>() {


    private var hasMore: MutableLiveData<Boolean>? = null

    private var isEmpty: MutableLiveData<Boolean>? = null

    init {
        this.hasMore = hasMore
        this.isEmpty = isEmpty
    }


    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
    }

    override fun onItemAtEndLoaded(itemAtEnd: T) = hasMore!!.postValue(false)
}