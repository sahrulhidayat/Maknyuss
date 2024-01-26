package com.sahidev.maknyuss.data.repository

import com.sahidev.maknyuss.data.source.local.LocalDataSource
import com.sahidev.maknyuss.domain.model.Ingredient
import com.sahidev.maknyuss.domain.repository.IngredientRepository
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : IngredientRepository {
    override suspend fun addIngredient(ingredient: Ingredient) {
        localDataSource.addIngredient(ingredient)
    }
}