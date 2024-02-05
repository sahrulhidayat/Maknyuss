package com.sahidev.maknyuss.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sahidev.maknyuss.domain.model.Search
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Upsert
    suspend fun addSearchHistory(search: Search)

    @Query(value = "SELECT * FROM Search ORDER BY timestamp DESC LIMIT 10")
    fun getSearchHistory(): Flow<List<Search>>

    @Query(value = "DELETE FROM Search WHERE `query` = :query")
    suspend fun deleteSearchHistory(query: String)

    @Query(value = "DELETE FROM Search")
    suspend fun clearSearchHistory()
}