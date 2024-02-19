package com.sahidev.maknyuss.data.source.network.interceptor

import com.sahidev.maknyuss.data.source.network.monitor.NetworkState
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * NetworkMonitorInterceptor is a [application-interceptor](https://square.github.io/okhttp/features/interceptors/#application-interceptors)
 * meant to first check the status of the Network before sending the request
 *
 * @param liveNetworkState a [NetworkState] implementation that handles all of the actual network logic checking
 * */
class NetworkMonitorInterceptor @Inject constructor(
    private val liveNetworkState: NetworkState
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val builder: Request.Builder = chain.request().newBuilder()

        return if (liveNetworkState.isConnected()) {
            chain.proceed(request)
        } else {
            builder.cacheControl(CacheControl.FORCE_CACHE)
            chain.proceed(builder.build())
        }
    }
}