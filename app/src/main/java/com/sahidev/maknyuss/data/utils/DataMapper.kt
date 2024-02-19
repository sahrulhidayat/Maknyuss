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
        if (instructions.isNotEmpty()) {
            instructions.forEach { instruction ->
                instruction.equipments.forEach {
                    if (!equipments.contains(it)) {
                        equipments.add(it)
                    }
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
        if (input.isNotEmpty()) {
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
        } else {
            return emptyList()
        }
    }

    private fun mapRecipeInstructions(
        recipeId: Int,
        input: List<AnalyzedInstructionsItem>
    ): List<Instruction> {
        return if (input.isNotEmpty()) {
            input[0].steps.map {
                Instruction(
                    it.number,
                    it.step,
                    mapEquipments(it.equipments),
                    mapIngredients(it.ingredients),
                    recipeId
                )
            }
        } else {
            emptyList()
        }
    }

    private fun mapEquipments(
        input: List<EquipmentItem>
    ): List<Equipment> {
        return if (input.isNotEmpty()) {
            input.map {
                Equipment(
                    it.name.capitalize(),
                    equipmentImage(it.image)
                )
            }
        } else {
            emptyList()
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

    private fun ingredientImage(ingredient: String?, size: String = "100x100"): String {

        return "https://spoonacular.com/cdn/ingredients_${size}/${ingredient ?: "ingredient.jpg"}"
    }

    private fun equipmentImage(equipment: String? = "ingredient.jpg", size: String = "100x100"): String {
        return "https://spoonacular.com/cdn/equipment_${size}/${equipment ?: "equipment.jpg"}"
    }
}