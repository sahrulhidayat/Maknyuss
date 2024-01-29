package com.sahidev.maknyuss.data.repository

import com.sahidev.maknyuss.data.source.NetworkBoundResource
import com.sahidev.maknyuss.data.source.local.LocalDataSource
import com.sahidev.maknyuss.data.source.network.RemoteDataSource
import com.sahidev.maknyuss.data.source.network.api.ApiResponse
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Equipment
import com.sahidev.maknyuss.domain.model.Ingredient
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RecipeRepository {

    override fun searchRecipe(query: String, offset: Int): Flow<Resource<List<Recipe>>> {
        return object : NetworkBoundResource<List<Recipe>>() {
            override fun loadFromDB(): Flow<List<Recipe>> {
                return localDataSource.searchRecipe(query)
            }

            override fun shouldFetch(data: List<Recipe>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<Recipe>>> {
                return remoteDataSource.searchRecipe(query, offset)
            }
        }.asFlow()
    }

    override suspend fun getRecipeInfo(id: Int): Flow<Resource<Recipe>> {
        return object : NetworkBoundResource<Recipe>() {
            override fun loadFromDB(): Flow<Recipe> {
                return localDataSource.getRecipeInfo(id)
            }

            override suspend fun createCall(): Flow<ApiResponse<Recipe>> {
                return remoteDataSource.getRecipeInfo(id)
            }

            override fun shouldFetch(data: Recipe?): Boolean {
                return data?.summary.isNullOrBlank()
            }
        }.asFlow()
    }

    override suspend fun getRandomRecipe(): Flow<Resource<List<Recipe>>> {
        return object : NetworkBoundResource<List<Recipe>>() {
            override fun loadFromDB(): Flow<List<Recipe>> {
                return flow { emit(emptyList()) }
            }

            override fun shouldFetch(data: List<Recipe>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<Recipe>>> {
                return remoteDataSource.getRandomRecipes()
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
                return flow { emit(ApiResponse.Empty) }
            }

        }.asFlow()
    }

    override suspend fun addRecipe(recipe: Recipe) {
        localDataSource.addRecipe(recipe)
    }

    override suspend fun deleteRecipe(id: Int) {
        localDataSource.deleteRecipe(id)
    }

    override suspend fun addInstruction(instruction: Instruction) {
        localDataSource.addInstruction(instruction)
    }

    override suspend fun addIngredient(ingredient: Ingredient) {
        localDataSource.addIngredient(ingredient)
    }

    override suspend fun addEquipment(equipment: Equipment) {
        localDataSource.addEquipment(equipment)
    }
}