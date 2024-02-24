package com.sahidev.maknyuss.feature.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.sahidev.maknyuss.data.utils.Constant.DEFAULT_ERROR_MESSAGE
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.feature.component.AppSearchBar
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onClickItem: (id: Int) -> Unit,
    navigateToMenu: (item: MenuItem) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val view = LocalView.current
    val window = (view.context as Activity).window

    val searchHistory = viewModel.searchHistory.value
    val autoCompleteSearch = viewModel.autoCompleteSearch.value
    val showingSearchResult = viewModel.showingSearchResult.value
    val query = remember { mutableStateOf("") }

    var refreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            if (showingSearchResult) {
                viewModel.onEvent(HomeEvent.SearchRecipe(query.value))
            } else {
                viewModel.onEvent(HomeEvent.PullRefresh)
            }
        }
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    DisposableEffect(view) {
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        onDispose {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

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
                AppSearchBar(
                    query = query,
                    searchHistory = searchHistory,
                    autoCompleteSearch = autoCompleteSearch,
                    onClearAutoComplete = { viewModel.onEvent(HomeEvent.ClearAutoComplete) },
                    onQueryChange = { viewModel.onEvent(HomeEvent.InputQuery(it)) },
                    onSearch = { query ->
                        if (query.isNotBlank()) {
                            viewModel.onEvent(HomeEvent.ShowingSearchResult(true))
                            viewModel.onEvent(HomeEvent.SearchRecipe(query))
                        }
                    },
                    onAutoCompleteSearch = { id ->
                        if (id != null) {
                            onClickItem(id)
                        }
                    },
                    onDeleteSearchHistory = { viewModel.onEvent(HomeEvent.DeleteSearchHistory(it)) },
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }
        ) { padding ->
            when (val recipeState = viewModel.recipeState.value) {
                is Resource.Error -> {
                    ErrorScreen(
                        message = recipeState.message ?: DEFAULT_ERROR_MESSAGE,
                        showAction = !showingSearchResult,
                        onClickAction = {
                            if (showingSearchResult) {
                                viewModel.onEvent(HomeEvent.SearchRecipe(query.value))
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

@Preview(showBackground = true, showSystemUi = true)
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

