package com.sahidev.maknyuss.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.Search
import com.sahidev.maknyuss.domain.usecase.RecipeUseCase
import com.sahidev.maknyuss.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val searchUseCase: SearchUseCase,
) : ViewModel() {
    var recipeState = mutableStateOf<Resource<List<Recipe>>>(Resource.Loading())
        private set
    var searchHistory = mutableStateOf<List<Search>>(emptyList())
        private set
    var showingSearchResult = mutableStateOf(false)
        private set

    init {
        getRandomRecipes()
        getSearchHistory()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SearchRecipe -> {
                viewModelScope.launch {
                    recipeUseCase.searchRecipe(event.query, event.offset)
                        .collect { data ->
                            recipeState.value = data
                        }

                    searchUseCase.addSearchHistory(
                        Search(
                            event.query,
                            System.currentTimeMillis()
                        )
                    )
                }
            }

            is HomeEvent.DeleteSearchHistory -> {
                viewModelScope.launch {
                    searchUseCase.deleteSearchHistory(event.query)
                }
            }

            is HomeEvent.ShowingSearchResult -> {
                showingSearchResult.value = event.value
            }

            HomeEvent.ClearSearchHistory -> {
                viewModelScope.launch {
                    searchUseCase.clearSearchHistory()
                }
            }

        }
    }

    fun getRandomRecipes() {
        viewModelScope.launch {
            recipeUseCase.getRandomRecipe()
                .collect { data ->
                    recipeState.value = data
                }
        }
    }

    private fun getSearchHistory() {
        viewModelScope.launch {
            searchUseCase.getSearchHistory()
                .collect { data ->
                    searchHistory.value = data
                }
        }
    }
}

sealed class HomeEvent {
    data class SearchRecipe(val query: String, val offset: Int = 0) : HomeEvent()
    data class DeleteSearchHistory(val query: String) : HomeEvent()
    data class ShowingSearchResult(val value: Boolean) : HomeEvent()
    data object ClearSearchHistory : HomeEvent()
}