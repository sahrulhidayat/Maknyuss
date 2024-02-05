package com.sahidev.maknyuss.domain.repository

import com.sahidev.maknyuss.domain.model.Search
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun addSearchHistory(search: Search)
    fun getSearchHistory(): Flow<List<Search>>
    suspend fun deleteSearchHistory(query: String)
    suspend fun clearSearchHistory()
}