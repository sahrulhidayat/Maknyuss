package com.sahidev.maknyuss.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sahidev.maknyuss.data.utils.Constants.DEFAULT_ERROR_MESSAGE
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.feature.component.HomeError
import com.sahidev.maknyuss.feature.component.HomeSkeleton
import com.sahidev.maknyuss.feature.component.RecipeCard

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

    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
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
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
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
                }
            ) {
                search.forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "History icon"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = it)
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

            is Resource.Loading -> HomeSkeleton(Modifier.padding(padding))
            is Resource.Success -> {
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