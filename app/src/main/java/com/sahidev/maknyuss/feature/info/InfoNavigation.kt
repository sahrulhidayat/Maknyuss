package com.sahidev.maknyuss.feature.info

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val INFO_ROUTE = "info"
const val RECIPE_ID = "recipeId"

fun NavGraphBuilder.infoScreen(
    onBack: () -> Unit
) {
    composable(
        route = "$INFO_ROUTE/{$RECIPE_ID}",
        arguments = listOf(
            navArgument(RECIPE_ID) { type = NavType.IntType }
        )
    ) {
        InfoScreen(onBack)
    }
}