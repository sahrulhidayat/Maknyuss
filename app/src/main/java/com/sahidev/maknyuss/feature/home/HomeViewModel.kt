package com.sahidev.maknyuss.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {
    var recipeState = mutableStateOf<Resource<List<Recipe>>>(Resource.Loading())
        private set

    fun onEvent(event: HomeEvent) {

    }
    private fun searchRecipe(query: String, offset: Int) {
        viewModelScope.launch {
            recipeUseCase.searchRecipe(query, offset)
                .collect { data ->
                    recipeState = mutableStateOf(data)
                }
        }
    }

    private fun getRandomRecipes() {

    }
}

sealed class HomeEvent {
    data class SearchRecipes(val query: String, val offset: Int) : HomeEvent()
}