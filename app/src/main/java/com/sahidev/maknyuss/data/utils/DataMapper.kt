package com.sahidev.maknyuss.data.utils

import com.sahidev.maknyuss.data.source.network.response.AnalyzedInstructionsItem
import com.sahidev.maknyuss.data.source.network.response.AutoCompleteResponseItem
import com.sahidev.maknyuss.data.source.network.response.EquipmentItem
import com.sahidev.maknyuss.data.source.network.response.ExtendedIngredientsItem
import com.sahidev.maknyuss.data.source.network.response.IngredientsItem
import com.sahidev.maknyuss.data.source.network.response.PriceBreakDownResponse
import com.sahidev.maknyuss.data.source.network.response.RecipeInfoResponse
import com.sahidev.maknyuss.data.source.network.response.RecipesItem
import com.sahidev.maknyuss.data.source.network.response.ResultsItem
import com.sahidev.maknyuss.domain.model.Equipment
import com.sahidev.maknyuss.domain.model.Ingredient
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Price
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import com.sahidev.maknyuss.domain.model.Search
import java.math.RoundingMode

object DataMapper {

    fun mapRecipesToModel(input: List<RecipesItem>): List<Recipe> {
        return input.map {
            val pricePerServing = centsToDollars(it.pricePerServing)
            Recipe(
                it.id,
                it.title,
                recipeImage(it.id, it.imageType),
                pricePerServing.toString(),
                dishTypes = it.dishTypes,
                diets = it.diets
            )
        }
    }

    fun mapSearchResponseToModel(input: List<ResultsItem>): List<Recipe> {
        return input.map {
            val pricePerServing = centsToDollars(it.pricePerServing)
            Recipe(
                it.id,
                it.title,
                recipeImage(it.id, it.imageType),
                pricePerServing.toString(),
                dishTypes = it.dishTypes,
                diets = it.diets
            )
        }
    }

    fun mapPriceBreakDownResponseToModel(
        input: PriceBreakDownResponse,
        recipeAndInstructions: RecipeAndInstructions
    ): RecipeAndInstructions {
        val pricePerServing = centsToDollars(input.totalCostPerServing)
        val totalCost = centsToDollars(input.totalCost)
        val priceBreakDown = if (input.ingredients.isNotEmpty()) {
            input.ingredients.map {
                val price = centsToDollars(it.price)

                val valueMetric = it.amount.metric.value.toString()
                val unitMetric = it.amount.metric.unit
                val valueUs = it.amount.us.value.toString()
                val unitUs = it.amount.us.unit

                Price(
                    it.name.capitalize(),
                    price.toString(),
                    formatAmount(valueMetric, unitMetric),
                    formatAmount(valueUs, unitUs),
                    ingredientImage(it.image)
                )
            }
        } else {
            emptyList()
        }

        val recipe = recipeAndInstructions.recipe.copy(
            pricePerServing = pricePerServing.toString(),
            totalCost = totalCost.toString(),
            priceBreakDown = priceBreakDown,
        )

        return recipeAndInstructions.copy(
            recipe = recipe
        )
    }

    fun mapRecipeInfoResponseToModel(input: RecipeInfoResponse): RecipeAndInstructions {
        val pricePerServing = centsToDollars(input.pricePerServing)
        val summary = input.summary
            .dropLast(1)
            .replaceAfterLast(". ", "")
            .dropLast(1)
            .replaceAfterLast(". ", "")
            .dropLast(1)
            .replaceAfterLast(". ", "")

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

        val recipe = Recipe(
            input.id,
            input.title,
            recipeImage(input.id, input.imageType),
            pricePerServing.toString(),
            totalCost = null,
            summary,
            input.likes.toString(),
            input.readyMinutes,
            input.servings,
            input.dishTypes,
            input.diets,
            equipments,
            mapExtendedIngredients(input.extendedIngredients),
            priceBreakDown = emptyList(),
        )

        return RecipeAndInstructions(
            recipe,
            instructions,
        )
    }

    fun mapAutoCompleteSearchToModel(
        input: List<AutoCompleteResponseItem>
    ): List<Search> {
        return if (input.isNotEmpty()) {
            input.map {
                Search(
                    it.title.capitalize(),
                    System.currentTimeMillis(),
                    recipeImage(it.id, it.imageType),
                    it.id
                )
            }
        } else {
            emptyList()
        }
    }

    private fun mapExtendedIngredients(
        input: List<ExtendedIngredientsItem>
    ): List<Ingredient> {
        return if (input.isNotEmpty()) {
            input.map {
                val valueMetric = it.measures.metric.amount.toString()
                val unitMetric = it.measures.metric.unitShort
                val valueUs = it.measures.us.amount.toString()
                val unitUs = it.measures.us.unitShort

                Ingredient(
                    it.name.capitalize(),
                    ingredientImage(it.image),
                    it.nameClean,
                    formatAmount(valueMetric, unitMetric),
                    formatAmount(valueUs, unitUs)
                )
            }
        } else {
            emptyList()
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
                    it.image
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
                it.image
            )
        }
    }

    private fun centsToDollars(cents: Double): Double {
        return (cents / 100)
            .toBigDecimal()
            .setScale(2, RoundingMode.CEILING)
            .toDouble()
    }

    private fun formatAmount(value: String, unit: String): String =
        "${value.trimTrailingZero()} $unit"

    private fun recipeImage(
        id: Int? = 0,
        imageType: String? = "jpg",
        aspectRatio: String? = "312x231"
    ): String {
        return "https://spoonacular.com/recipeImages/${id}-${aspectRatio}.${imageType}"
    }

    private fun ingredientImage(
        ingredient: String? = "ingredient.jpg",
        size: String = "100x100"
    ): String {
        return "https://spoonacular.com/cdn/ingredients_${size}/${ingredient?.ifBlank { "ingredient.jpg" }}"
    }
}