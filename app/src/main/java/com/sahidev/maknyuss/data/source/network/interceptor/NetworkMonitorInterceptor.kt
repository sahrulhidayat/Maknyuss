package com.sahidev.maknyuss.data.source.network.interceptor

import com.sahidev.maknyuss.data.source.network.monitor.NetworkState
import com.sahidev.maknyuss.data.source.network.monitor.NoNetworkException
import com.sahidev.maknyuss.data.utils.Constants.CACHE_CONTROL_HEADER
import com.sahidev.maknyuss.data.utils.Constants.NETWORK_ERROR_MESSAGE
import com.sahidev.maknyuss.data.utils.Constants.NO_CACHE
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import kotlin.jvm.Throws

/**
 * NetworkMonitorInterceptor is a [application-interceptor](https://square.github.io/okhttp/features/interceptors/#application-interceptors)
 * meant to first check the status of the Network before sending the request
 *
 * @param liveNetworkState a [NetworkState] implementation that handles all of the actual network logic checking
 * */
class NetworkMonitorInterceptor @Inject constructor(
    private val liveNetworkState: NetworkState
) : Interceptor {

    @Throws(NoNetworkException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val builder: Request.Builder = chain.request().newBuilder()
        val shouldUseCache = request.header(CACHE_CONTROL_HEADER) != NO_CACHE

        return if (liveNetworkState.isConnected()) {
            chain.proceed(request)
        } else {
            if (shouldUseCache) {
                builder.cacheControl(CacheControl.FORCE_CACHE)
                chain.proceed(builder.build())
            } else {
                throw NoNetworkException(NETWORK_ERROR_MESSAGE)
            }
        }
    }
}