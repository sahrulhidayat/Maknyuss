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
import java.math.RoundingMode

object DataMapper {

    fun mapSearchResponseToModel(input: List<ResultsItem>): List<Recipe> {
        return input.map {
            Recipe(
                it.id,
                it.title,
                it.image ?: "https://spoonacular.com/recipeImages/${it.id}-556x370.jpg",
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
            instruction.equipments.forEach {
                if (!equipments.contains(it)) {
                    equipments.add(it)
                }
            }
        }
        val priceInDollars = (input.price / 100)
            .toBigDecimal()
            .setScale(2, RoundingMode.UP)
            .toDouble()

        val summary = input.summary
            .dropLast(1)
            .replaceAfterLast(". ", "")
            .dropLast(1)
            .replaceAfterLast(". ", "")
            .dropLast(1)
            .replaceAfterLast(". ", "")

        val recipe = Recipe(
            input.id,
            input.title,
            input.image ?: "https://spoonacular.com/recipeImages/${input.id}-556x370.jpg",
            summary,
            priceInDollars.toString(),
            input.likes.toString(),
            input.readyMinutes,
            input.servings,
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
            val amount = it.measures.metric.amount.toString()
            val unit = it.measures.metric.unitShort
            val measures = "${amount.trimTrailingZero()} $unit"

            Ingredient(
                it.name.capitalize(),
                ingredientImage(it.image),
                it.nameClean,
                measures
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
                it.name.capitalize(),
                equipmentImage(it.image)
            )
        }
    }

    private fun mapIngredients(
        input: List<IngredientsItem>
    ): List<Ingredient> {
        return input.map {
            Ingredient(
                it.name.capitalize(),
                ingredientImage(it.image)
            )
        }
    }

    fun mapRecipesToModel(input: List<RecipesItem>): List<Recipe> {
        return input.map {
            Recipe(
                it.id,
                it.title,
                it.image ?: "https://spoonacular.com/recipeImages/${it.id}-556x370.jpg"
            )
        }
    }

    private fun ingredientImage(ingredient: String, size: String = "100x100"): String {

        return "https://spoonacular.com/cdn/ingredients_${size}/${ingredient.ifEmpty { "ingredient.jpg" }}"
    }

    private fun equipmentImage(equipment: String, size: String = "100x100"): String {
        return "https://spoonacular.com/cdn/equipment_${size}/${equipment.ifEmpty { "equipment.jpg" }}"
    }
}