package com.sahidev.maknyuss.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.feature.component.RecipeCard
import com.sahidev.maknyuss.feature.component.SkeletonHome
import com.sahidev.maknyuss.ui.theme.MaknyussTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    when (val recipeState = viewModel.recipeState.value) {
        is Resource.Error -> TODO()
        is Resource.Loading -> SkeletonHome()
        is Resource.Success -> {
            Scaffold { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    val data = recipeState.data
                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        if (data != null) {
                            items(data) { recipe ->
                                AsyncImage(
                                    model = recipe.image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(312f / 231f)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(250.dp),
                    ) {
                        if (data != null) {
                            items(data) { recipe ->
                                RecipeCard(recipe = recipe)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    MaknyussTheme {
        HomeScreen()
    }
}