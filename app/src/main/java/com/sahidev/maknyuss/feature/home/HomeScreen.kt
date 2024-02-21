package com.sahidev.maknyuss.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sahidev.maknyuss.data.utils.Constant.DEFAULT_ERROR_MESSAGE
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.feature.component.ErrorScreen
import com.sahidev.maknyuss.feature.component.HomeSkeleton
import com.sahidev.maknyuss.feature.component.ImageSlide
import com.sahidev.maknyuss.feature.component.MenuItem
import com.sahidev.maknyuss.feature.component.RecipeCard
import com.sahidev.maknyuss.feature.component.RecipeGrid
import com.sahidev.maknyuss.feature.component.RecipeGridSkeleton
import com.sahidev.maknyuss.feature.component.menuItems
import com.sahidev.maknyuss.ui.theme.MaknyussTheme
import com.sahidev.maknyuss.ui.theme.backgroundLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onClickItem: (id: Int) -> Unit,
    navigateToMenu: (item: MenuItem) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val searchHistory = viewModel.searchHistory.value
    val showingSearchResult = viewModel.showingSearchResult.value
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    var refreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            if (showingSearchResult) {
                viewModel.onEvent(HomeEvent.SearchRecipe(query))
            } else {
                viewModel.onEvent(HomeEvent.PullRefresh)
            }
        }
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BackHandler {
        if (showingSearchResult) {
            viewModel.onEvent(HomeEvent.ShowingSearchResult(false))
            viewModel.onEvent(HomeEvent.PullRefresh)
        }
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    "Maknyuss",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                menuItems.forEach { item ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        onClick = {
                            navigateToMenu(item)
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SearchBar(
                        query = query,
                        onQueryChange = { query = it },
                        onSearch = {
                            viewModel.onEvent(HomeEvent.ShowingSearchResult(true))
                            viewModel.onEvent(HomeEvent.SearchRecipe(it))
                            active = false
                        },
                        active = active,
                        onActiveChange = { active = it },
                        placeholder = {
                            Text(
                                text = "Search Recipe",
                                color = Color.Gray
                            )
                        },
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
                                IconButton(
                                    onClick = { scope.launch { drawerState.open() } }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Toggle navigation drawer"
                                    )
                                }
                            }
                        },
                        trailingIcon = {
                            if (active && query.isNotEmpty()) {
                                IconButton(onClick = { query = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close icon"
                                    )
                                }
                            }
                        },
                        colors = SearchBarDefaults.colors(containerColor = Color.White)
                    ) {
                        searchHistory.forEach {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(HomeEvent.SearchRecipe(it.query))
                                        query = it.query
                                        viewModel.onEvent(HomeEvent.ShowingSearchResult(true))
                                        active = false
                                    }
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                    imageVector = Icons.Default.History,
                                    contentDescription = "History icon"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = it.query,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        viewModel.onEvent(HomeEvent.DeleteSearchHistory(it.query))
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Delete history"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        ) { padding ->
            when (val recipeState = viewModel.recipeState.value) {
                is Resource.Error -> {
                    ErrorScreen(
                        message = recipeState.message ?: DEFAULT_ERROR_MESSAGE,
                        onClickAction = {
                            if (showingSearchResult) {
                                viewModel.onEvent(HomeEvent.SearchRecipe(query))
                            } else {
                                viewModel.onEvent(HomeEvent.PullRefresh)
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = padding.calculateBottomPadding())
                    )
                }

                is Resource.Loading -> {
                    if (showingSearchResult) {
                        RecipeGridSkeleton(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = padding.calculateBottomPadding()),
                            topPadding = padding.calculateTopPadding(),
                        )
                    } else {
                        HomeSkeleton(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = padding.calculateBottomPadding()),
                            topPadding = padding.calculateTopPadding(),
                        )
                    }
                }

                is Resource.Success -> {
                    refreshing = false
                    val data = recipeState.data ?: emptyList()

                    Box(
                        modifier = Modifier
                            .pullRefresh(pullRefreshState)
                            .padding(bottom = padding.calculateBottomPadding())
                    ) {
                        if (showingSearchResult) {
                            RecipeGrid(
                                data = data,
                                topPadding = padding.calculateTopPadding(),
                                onClickItem = onClickItem,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            HomeGrid(
                                data = data,
                                topPadding = padding.calculateTopPadding(),
                                onClickItem = onClickItem,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        PullRefreshIndicator(
                            refreshing = refreshing,
                            state = pullRefreshState,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = padding.calculateTopPadding())
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun HomeGrid(
    data: List<Recipe>,
    modifier: Modifier = Modifier,
    topPadding: Dp = 8.dp,
    onClickItem: (id: Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.background(backgroundLight),
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(start = 8.dp, top = topPadding, end = 8.dp, bottom = 8.dp)
    ) {
        item(
            span = {
                GridItemSpan(maxCurrentLineSpan)
            }
        ) {
            ImageSlide(
                data = data,
                onClick = { id -> onClickItem(id) }
            )
        }
        items(data) { recipe ->
            RecipeCard(
                recipe = recipe,
                modifier = Modifier.clickable { onClickItem(recipe.id) }
            )
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
            HomeGrid(data = recipes, onClickItem = {})
        }
    }
}

