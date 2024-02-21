package com.sahidev.maknyuss.data.source.network

import android.util.Log
import com.sahidev.maknyuss.data.source.network.api.ApiResponse
import com.sahidev.maknyuss.data.source.network.api.ApiService
import com.sahidev.maknyuss.data.utils.Constant.NETWORK_ERROR_MESSAGE
import com.sahidev.maknyuss.data.utils.DataMapper
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import retrofit2.HttpException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun searchRecipe(
        query: String,
        offset: Int
    ): Flow<ApiResponse<List<Recipe>>> {
        return channelFlow {
            try {
                val response = apiService.searchRecipe(query = query, offset = offset)
                val results = response.results
                if (results.isNotEmpty()) {
                    send(
                        ApiResponse.Success(
                            DataMapper.mapSearchResponseToModel(results)
                        )
                    )
                } else {
                    send(ApiResponse.Empty)
                }
            } catch (exception: Exception) {
                when (exception) {
                    is HttpException -> {
                        Log.e(TAG, "searchRecipe: $exception")
                        send(ApiResponse.Error(NETWORK_ERROR_MESSAGE))
                    }

                    else -> {
                        send(ApiResponse.Error(exception.toString()))
                        Log.e(TAG, "searchRecipe: $exception")
                    }
                }
            }
        }
    }

    suspend fun getRecipeInfo(id: Int): Flow<ApiResponse<RecipeAndInstructions>> {
        return channelFlow {
            try {
                val response = apiService.getRecipeInfo(id)
                send(
                    ApiResponse.Success(
                        DataMapper.mapRecipeInfoResponseToModel(response)
                    )
                )
            } catch (exception: Exception) {
                when (exception) {
                    is HttpException -> {
                        Log.e(TAG, "getRecipeInfo: $exception")
                        send(ApiResponse.Error(NETWORK_ERROR_MESSAGE))
                    }

                    else -> {
                        send(ApiResponse.Error(exception.toString()))
                        Log.e(TAG, "getRecipeInfo: $exception")
                    }
                }
            }
        }
    }

    suspend fun getRandomRecipes(): Flow<ApiResponse<List<Recipe>>> {
        return channelFlow {
            try {
                val response = apiService.getRandomRecipes()
                val results = response.recipes
                if (results.isNotEmpty()) {
                    send(
                        ApiResponse.Success(
                            DataMapper.mapRecipesToModel(results)
                        )
                    )
                } else {
                    send(ApiResponse.Empty)
                }
            } catch (exception: Exception) {
                when (exception) {
                    is HttpException -> {
                        Log.e(TAG, "getRandomRecipes: $exception")
                        send(ApiResponse.Error(NETWORK_ERROR_MESSAGE))
                    }

                    else -> {
                        Log.e(TAG, "getRandomRecipes: $exception")
                        send(ApiResponse.Error(exception.toString()))
                    }
                }
            }
        }
    }

    suspend fun getPriceBreakDown(recipeAndInstructions: RecipeAndInstructions): Flow<ApiResponse<RecipeAndInstructions>> {
        return channelFlow {
            try {
                val response = apiService.getPriceBreakDownById(recipeAndInstructions.recipe.id)
                send(
                    ApiResponse.Success(
                        DataMapper.mapPriceBreakDownResponseToModel(response, recipeAndInstructions)
                    )
                )
            } catch (exception: Exception) {
                when (exception) {
                    is HttpException -> {
                        Log.e(TAG, "getPriceBreakDown: $exception")
                        send(ApiResponse.Error(NETWORK_ERROR_MESSAGE))
                    }

                    else -> {
                        Log.e(TAG, "getPriceBreakDown: $exception")
                        send(ApiResponse.Error(exception.toString()))
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "Remote Data Source"
    }
}