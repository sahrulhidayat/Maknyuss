package com.sahidev.maknyuss.data.repository

import com.sahidev.maknyuss.data.source.NetworkBoundResource
import com.sahidev.maknyuss.data.source.local.LocalDataSource
import com.sahidev.maknyuss.data.source.network.RemoteDataSource
import com.sahidev.maknyuss.data.source.network.api.ApiResponse
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Search
import com.sahidev.maknyuss.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {
    override suspend fun addSearchHistory(search: Search) {
        localDataSource.addSearchHistory(search)
    }

    override fun getSearchHistory(): Flow<List<Search>> {
        return localDataSource.getSearchHistory()
    }

    override suspend fun getAutoCompleteRecipe(query: String): Flow<Resource<List<Search>>> {
        return object : NetworkBoundResource<List<Search>>() {
            override fun loadFromDB(): Flow<List<Search>> {
                return channelFlow { send(emptyList()) }
            }

            override fun shouldFetch(data: List<Search>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<Search>>> {
                return remoteDataSource.getAutoCompleteRecipe(query)
            }
        }.asFlow()
    }

    override suspend fun deleteSearchHistory(query: String) {
        localDataSource.deleteSearchHistory(query)
    }

    override suspend fun clearSearchHistory() {
        localDataSource.clearSearchHistory()
    }
}