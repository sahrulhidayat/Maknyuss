package com.sahidev.maknyuss.data.utils

import com.sahidev.maknyuss.data.source.network.response.RecipeInfoResponse
import com.sahidev.maknyuss.data.source.network.response.RecipesItem
import com.sahidev.maknyuss.data.source.network.response.ResultsItem
import com.sahidev.maknyuss.domain.model.Recipe

object DataMapper {

    fun mapSearchResponseToModel(input: List<ResultsItem>): List<Recipe> {
        return input.map {
            Recipe(
                it.id,
                it.title,
                it.image,
            )
        }
    }

    fun mapRecipeInfoResponseToModel(input: RecipeInfoResponse): Recipe =
        Recipe(
            input.id,
            input.title,
            input.image,
            input.summary,
            input.price.toString(),
            input.likes.toString(),
            input.veryPopular,
            input.veryHealthy,
            input.readyMinutes,
            input.servings,
            input.cuisines.joinToString(separator = ", "),
            input.dishTypes.joinToString(separator = ", "),
            input.source,
        )

    fun mapRecipesToModel(input: List<RecipesItem>): List<Recipe> {
        return input.map {
            Recipe(
                it.id,
                it.title,
                it.image
            )
        }
    }
}