package com.sahidev.maknyuss.data.di

import android.content.Context
import com.sahidev.maknyuss.BuildConfig
import com.sahidev.maknyuss.data.source.network.RemoteDataSource
import com.sahidev.maknyuss.data.source.network.api.ApiService
import com.sahidev.maknyuss.data.source.network.interceptor.CacheInterceptor
import com.sahidev.maknyuss.data.source.network.interceptor.NetworkMonitorInterceptor
import com.sahidev.maknyuss.data.source.network.monitor.LiveNetworkMonitor
import com.sahidev.maknyuss.data.source.network.monitor.NetworkState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext appContext: Context
    ): NetworkState {
        return LiveNetworkMonitor(appContext)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext appContext: Context,
        liveNetworkMonitor: NetworkState
    ): OkHttpClient {
        // Create OkHttpClient with a cache directory
        val cacheSize = (50 * 1024 * 1024).toLong() // 50 MB
        val cache = Cache(File(appContext.cacheDir, "http-cache"), cacheSize)
        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(NetworkMonitorInterceptor(liveNetworkMonitor))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClient.addInterceptor(logging)
        }

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }
}