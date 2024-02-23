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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private var searchQuery = MutableStateFlow("")
    var autoCompleteSearch = mutableStateOf<Resource<List<Search>>>(Resource.Loading())
        private set
    var showingSearchResult = mutableStateOf(false)
        private set

    private var getAutoCompleteSearchJob: Job? = null

    init {
        getRandomRecipes()
        getSearchHistory()
        getAutoCompleteSearch()
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

            is HomeEvent.InputQuery -> {
                searchQuery.value = event.query
            }

            HomeEvent.ClearAutoComplete -> {
                autoCompleteSearch.value = Resource.Loading()
            }

            HomeEvent.ClearSearchHistory -> {
                viewModelScope.launch {
                    searchUseCase.clearSearchHistory()
                }
            }

            HomeEvent.PullRefresh -> {
                getRandomRecipes()
            }

        }
    }

    @OptIn(FlowPreview::class)
    private fun getAutoCompleteSearch() {
        getAutoCompleteSearchJob?.cancel()

        viewModelScope.launch {
            searchQuery.debounce(300).collectLatest { query ->
                if (query.isNotBlank()) {
                    getAutoCompleteSearchJob = searchUseCase.getAutoCompleteRecipe(query)
                        .onEach {
                            autoCompleteSearch.value = it
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

    private fun getRandomRecipes() {
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
    data class InputQuery(val query: String) : HomeEvent()
    data object ClearAutoComplete : HomeEvent()
    data object ClearSearchHistory : HomeEvent()
    data object PullRefresh : HomeEvent()
}