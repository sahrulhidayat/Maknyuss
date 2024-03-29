package com.sahidev.maknyuss.data.repository

import com.sahidev.maknyuss.data.source.NetworkBoundResource
import com.sahidev.maknyuss.data.source.local.LocalDataSource
import com.sahidev.maknyuss.data.source.network.RemoteDataSource
import com.sahidev.maknyuss.data.source.network.api.ApiResponse
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import com.sahidev.maknyuss.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RecipeRepository {

    override fun searchRecipe(query: String, offset: Int): Flow<Resource<List<Recipe>>> {
        return object : NetworkBoundResource<List<Recipe>>() {
            override fun loadFromDB(): Flow<List<Recipe>> {
                return channelFlow { send(emptyList()) }
            }

            override fun shouldFetch(data: List<Recipe>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<Recipe>>> {
                return remoteDataSource.searchRecipe(query, offset)
            }
        }.asFlow()
    }

    override suspend fun getRecipeInfo(id: Int): Flow<Resource<RecipeAndInstructions>> {
        return object : NetworkBoundResource<RecipeAndInstructions>() {
            override fun loadFromDB(): Flow<RecipeAndInstructions> {
                return localDataSource.getRecipeInfo(id)
            }

            override fun shouldFetch(data: RecipeAndInstructions?): Boolean {
                return data?.recipe == null
            }

            override suspend fun createCall(): Flow<ApiResponse<RecipeAndInstructions>> {
                return remoteDataSource.getRecipeInfo(id)
            }
        }.asFlow()
    }

    override suspend fun getRandomRecipe(): Flow<Resource<List<Recipe>>> {
        return object : NetworkBoundResource<List<Recipe>>() {
            override fun loadFromDB(): Flow<List<Recipe>> {
                return channelFlow { send(emptyList()) }
            }

            override fun shouldFetch(data: List<Recipe>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<Recipe>>> {
                return remoteDataSource.getRandomRecipes()
            }
        }.asFlow()
    }

    override suspend fun getPriceBreakDown(
        recipeAndInstructions: RecipeAndInstructions
    ): Flow<Resource<RecipeAndInstructions>> {
        return object : NetworkBoundResource<RecipeAndInstructions>() {
            override fun loadFromDB(): Flow<RecipeAndInstructions> {
                return localDataSource.getRecipeInfo(recipeAndInstructions.recipe.id)
            }

            override fun shouldFetch(data: RecipeAndInstructions?): Boolean {
                return data?.recipe?.priceBreakDown?.isEmpty() ?: true
            }

            override suspend fun createCall(): Flow<ApiResponse<RecipeAndInstructions>> {
                return remoteDataSource.getPriceBreakDown(recipeAndInstructions)
            }
        }.asFlow()
    }

    override fun getSavedRecipes(): Flow<Resource<List<Recipe>>> {
        return object : NetworkBoundResource<List<Recipe>>() {
            override fun loadFromDB(): Flow<List<Recipe>> {
                return localDataSource.getSavedRecipes()
            }

            override fun shouldFetch(data: List<Recipe>?): Boolean {
                return false
            }

            override suspend fun createCall(): Flow<ApiResponse<List<Recipe>>> {
                return channelFlow { send(ApiResponse.Empty) }
            }
        }.asFlow()
    }

    override suspend fun addRecipe(recipe: Recipe) {
        localDataSource.addRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        localDataSource.deleteRecipe(recipe)
    }

    override suspend fun addInstruction(instruction: Instruction) {
        localDataSource.addInstruction(instruction)
    }
}