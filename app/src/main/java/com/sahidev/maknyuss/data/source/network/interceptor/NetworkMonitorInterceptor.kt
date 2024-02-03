package com.sahidev.maknyuss.data.source.network.interceptor

import com.sahidev.maknyuss.data.source.network.monitor.NetworkState
import com.sahidev.maknyuss.data.source.network.monitor.NoNetworkException
import com.sahidev.maknyuss.data.utils.Constants.NETWORK_ERROR_MESSAGE
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

    @Throws(NoNetworkException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(chain.request())
        val builder: Request.Builder = chain.request().newBuilder()

        val cacheAvailable = !response.cacheControl.noCache

        return if (liveNetworkState.isConnected()) {
            chain.proceed(request)
        } else {
            if (cacheAvailable) {
                builder.cacheControl(CacheControl.FORCE_CACHE)
                chain.proceed(builder.build())
            } else {
                throw NoNetworkException(NETWORK_ERROR_MESSAGE)
            }
        }
    }
}