package com.sahidev.maknyuss.data.source.network

import android.util.Log
import com.sahidev.maknyuss.data.source.network.api.ApiResponse
import com.sahidev.maknyuss.data.source.network.api.ApiService
import com.sahidev.maknyuss.data.source.network.monitor.NoNetworkException
import com.sahidev.maknyuss.data.utils.Constants.NETWORK_ERROR_MESSAGE
import com.sahidev.maknyuss.data.utils.DataMapper
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun searchRecipe(
        query: String,
        offset: Int
    ): Flow<ApiResponse<List<Recipe>>> {
        return flow {
            try {
                val response = apiService.searchRecipe(query = query, offset = offset)
                val results = response.results
                if (results.isNotEmpty()) {
                    emit(
                        ApiResponse.Success(
                            DataMapper.mapSearchResponseToModel(results)
                        )
                    )
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (exception: Exception) {
                when (exception) {
                    is NoNetworkException -> {
                        emit(ApiResponse.Error(NETWORK_ERROR_MESSAGE))
                    }

                    else -> {
                        emit(ApiResponse.Error(exception.toString()))
                        Log.e(TAG, "searchRecipe: $exception")
                    }
                }
            }
        }
    }

    suspend fun getRecipeInfo(id: Int): Flow<ApiResponse<RecipeAndInstructions>> {
        return flow {
            try {
                val response = apiService.getRecipeInfo(id)
                emit(
                    ApiResponse.Success(
                        DataMapper.mapRecipeInfoResponseToModel(response)
                    )
                )
            } catch (exception: Exception) {
                when (exception) {
                    is NoNetworkException -> {
                        emit(ApiResponse.Error(NETWORK_ERROR_MESSAGE))
                    }

                    else -> {
                        emit(ApiResponse.Error(exception.toString()))
                        Log.e(TAG, "getRecipeInfo: $exception")
                    }
                }
            }
        }
    }

    suspend fun getRandomRecipes(): Flow<ApiResponse<List<Recipe>>> {
        return flow {
            try {
                val response = apiService.getRandomRecipes()
                val results = response.recipes
                if (results.isNotEmpty()) {
                    emit(
                        ApiResponse.Success(
                            DataMapper.mapRecipesToModel(results)
                        )
                    )
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (exception: Exception) {
                when (exception) {
                    is NoNetworkException -> {
                        emit(ApiResponse.Error(NETWORK_ERROR_MESSAGE))
                    }

                    else -> {
                        emit(ApiResponse.Error(exception.toString()))
                        Log.e(TAG, "getRandomRecipes: $exception")
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "Remote Data Source"
    }
}