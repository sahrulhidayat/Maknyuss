package com.sahidev.maknyuss.data.source.network.monitor

import okio.IOException

/**
 * NetworkMonitor is a interface meant to act as the API to all network monitoring related issues
 *
 * @function isConnected used to determine if the application is connected to a network or not
 * */
interface NetworkState {
    fun isConnected(): Boolean
}

class NoNetworkException(message:String): IOException(message)