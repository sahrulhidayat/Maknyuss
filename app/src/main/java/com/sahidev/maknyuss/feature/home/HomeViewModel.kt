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

    init {
        getRandomRecipes()
    }

    private fun getRandomRecipes() {
        viewModelScope.launch {
            recipeUseCase.getRandomRecipe()
                .collect { data ->
                    recipeState.value = data
                }
        }
    }
}