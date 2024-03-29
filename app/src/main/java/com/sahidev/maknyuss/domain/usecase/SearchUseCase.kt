package com.sahidev.maknyuss.domain.usecase

import com.sahidev.maknyuss.data.source.network.api.ApiResponse
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Search
import com.sahidev.maknyuss.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchUseCaseImpl(
    private val repository: SearchRepository
) : SearchUseCase {
    override suspend fun addSearchHistory(search: Search) {
        repository.addSearchHistory(search)
    }

    override fun getSearchHistory(): Flow<List<Search>> {
        return repository.getSearchHistory()
    }

    override suspend fun getAutoCompleteRecipe(query: String): Flow<Resource<List<Search>>> {
        return repository.getAutoCompleteRecipe(query)
    }

    override suspend fun deleteSearchHistory(query: String) {
        repository.deleteSearchHistory(query)
    }

    override suspend fun clearSearchHistory() {
        repository.clearSearchHistory()
    }

}

interface SearchUseCase {
    suspend fun addSearchHistory(search: Search)
    fun getSearchHistory(): Flow<List<Search>>
    suspend fun getAutoCompleteRecipe(query: String): Flow<Resource<List<Search>>>
    suspend fun deleteSearchHistory(query: String)
    suspend fun clearSearchHistory()
}