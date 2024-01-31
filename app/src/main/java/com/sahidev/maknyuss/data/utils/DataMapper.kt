package com.sahidev.maknyuss.data.utils

import com.sahidev.maknyuss.data.source.network.response.AnalyzedInstructionsItem
import com.sahidev.maknyuss.data.source.network.response.EquipmentItem
import com.sahidev.maknyuss.data.source.network.response.ExtendedIngredientsItem
import com.sahidev.maknyuss.data.source.network.response.IngredientsItem
import com.sahidev.maknyuss.data.source.network.response.RecipeInfoResponse
import com.sahidev.maknyuss.data.source.network.response.RecipesItem
import com.sahidev.maknyuss.data.source.network.response.ResultsItem
import com.sahidev.maknyuss.domain.model.Equipment
import com.sahidev.maknyuss.domain.model.Ingredient
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions

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

    fun mapRecipeInfoResponseToModel(input: RecipeInfoResponse): RecipeAndInstructions {
        val equipments = arrayListOf<Equipment>()
        val instructions = mapRecipeInstructions(
            input.id,
            input.analyzedInstructions
        )
        instructions.forEach { instruction ->
            instruction.equipments.forEach { equipments.add(it) }
        }

        val recipe = Recipe(
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
            equipments,
            mapExtendedIngredients(input.extendedIngredients)
        )

        return RecipeAndInstructions(
            recipe,
            instructions
        )
    }

    private fun mapExtendedIngredients(
        input: List<ExtendedIngredientsItem>
    ): List<Ingredient> {
        return input.map {
            Ingredient(
                it.name,
                it.image,
                it.nameClean,
                "${it.measures.us.unitShort} (${it.measures.metric.unitShort})"
            )
        }
    }

    private fun mapRecipeInstructions(
        recipeId: Int,
        input: List<AnalyzedInstructionsItem>
    ): List<Instruction> {
        return input[0].steps.map {
            Instruction(
                it.number,
                it.step,
                mapEquipments(it.equipments),
                mapIngredients(it.ingredients),
                recipeId
            )
        }
    }

    private fun mapEquipments(
        input: List<EquipmentItem>
    ): List<Equipment> {
        return input.map {
            Equipment(
                it.name,
                it.image
            )
        }
    }

    private fun mapIngredients(
        input: List<IngredientsItem>
    ): List<Ingredient> {
        return input.map {
            Ingredient(
                it.name,
                it.image
            )
        }
    }

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