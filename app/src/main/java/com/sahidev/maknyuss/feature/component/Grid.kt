package com.sahidev.maknyuss.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.ui.theme.backgroundLight

@Composable
fun RecipeGrid(
    data: List<Recipe>,
    modifier: Modifier = Modifier,
    topPadding: Dp = 8.dp,
    onClickItem: (id: Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier.background(backgroundLight),
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(start = 8.dp, top = topPadding, end = 8.dp, bottom = 8.dp)
    ) {
        items(data) { recipe ->
            RecipeCard(
                recipe = recipe,
                modifier = Modifier.clickable { onClickItem(recipe.id) }
            )
        }
    }
}