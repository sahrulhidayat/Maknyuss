package com.sahidev.maknyuss.data.repository

import com.sahidev.maknyuss.data.source.local.LocalDataSource
import com.sahidev.maknyuss.domain.model.Search
import com.sahidev.maknyuss.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : SearchRepository {
    override suspend fun addSearchHistory(search: Search) {
        localDataSource.addSearchHistory(search)
    }

    override fun getSearchHistory(): Flow<List<Search>> {
        return localDataSource.getSearchHistory()
    }

    override suspend fun deleteSearchHistory(query: String) {
        localDataSource.deleteSearchHistory(query)
    }

    override suspend fun clearSearchHistory() {
        localDataSource.clearSearchHistory()
    }
}