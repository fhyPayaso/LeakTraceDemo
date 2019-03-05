package com.bytedance.fanhongyu.scaffold.data

/**
 *
 * @author fhyPayaso
 * @since 2019/3/5 8:01 PM
 */
class NetworkStatus(var mStatus: Status) {

    companion object {

        fun loading(): NetworkStatus = NetworkStatus(NetworkStatus.Status.RUNNING)

        fun error(): NetworkStatus = NetworkStatus(NetworkStatus.Status.FAILED)

        fun success(): NetworkStatus = NetworkStatus(NetworkStatus.Status.SUCCESS)
    }

    fun isLoading(): Boolean = mStatus == Status.RUNNING

    fun isSuccess(): Boolean = mStatus == Status.SUCCESS

    fun isFailed(): Boolean = mStatus == Status.FAILED

    enum class Status(var mStatus: Int) {

        /** 正在请求  */
        RUNNING(1),
        /** 请求成功  */
        SUCCESS(0),
        /** 请求失败  */
        FAILED(-1)
    }
}