package com.sahidev.maknyuss.domain.repository

import com.sahidev.maknyuss.domain.model.Ingredient

interface IngredientRepository {
    suspend fun addIngredient(ingredient: Ingredient)
}