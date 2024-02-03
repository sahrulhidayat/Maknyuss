package com.sahidev.maknyuss.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sahidev.maknyuss.data.utils.Constants.DEFAULT_ERROR_MESSAGE
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.feature.component.HomeError
import com.sahidev.maknyuss.feature.component.HomeSkeleton
import com.sahidev.maknyuss.feature.component.ImageSlide
import com.sahidev.maknyuss.feature.component.RecipeCard
import com.sahidev.maknyuss.ui.theme.MaknyussTheme
import com.sahidev.maknyuss.ui.theme.backgroundLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val search = remember {
        mutableStateListOf(
            "Healthy breakfast",
            "Chicken dinner"
        )
    }

    var maxWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        maxWidth = with(density) {
                            it.size.width.toDp()
                        }
                    },
                horizontalArrangement = Arrangement.Center
            ) {
                SearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = {
                        search.add(it)
                        active = false
                    },
                    active = active,
                    onActiveChange = { active = it },
                    placeholder = { Text(text = "Search") },
                    leadingIcon = {
                        if (active) {
                            IconButton(
                                onClick = { active = !active }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "Back icon"
                                )
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search icon"
                            )
                        }
                    },
                    trailingIcon = {
                        if (active && query.isNotEmpty()) {
                            Icon(
                                modifier = Modifier.clickable {
                                    query = ""
                                },
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close icon"
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .defaultMinSize(minWidth = maxWidth - 20.dp)
                ) {
                    search.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = "History icon"
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = it)
                        }
                    }
                }
            }
        }
    ) { padding ->
        when (val recipeState = viewModel.recipeState.value) {
            is Resource.Error -> {
                HomeError(
                    message = recipeState.message ?: DEFAULT_ERROR_MESSAGE,
                    modifier = Modifier.padding(padding)
                )
            }

            is Resource.Loading -> {
                HomeSkeleton(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                )
            }

            is Resource.Success -> {
                val data = recipeState.data
                if (data != null) {
                    RecipeGrid(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        data = data
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeGrid(modifier: Modifier = Modifier, data: List<Recipe>) {
    LazyVerticalGrid(
        modifier = modifier.background(backgroundLight),
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        item(
            span = {
                GridItemSpan(maxCurrentLineSpan)
            }
        ) {
            ImageSlide(data = data)
        }
        items(data) { recipe ->
            RecipeCard(recipe = recipe)
        }
    }
}

@Preview
@Composable
fun Preview() {
    MaknyussTheme {
        val recipes = listOf(
            Recipe(1, "Recipe 1", "image"),
            Recipe(2, "Recipe 2", "image")
        )

        MaknyussTheme {
            RecipeGrid(data = recipes)
        }
    }
}

