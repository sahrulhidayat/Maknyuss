package com.sahidev.maknyuss.feature.favorite

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
class FavoriteViewModel @Inject constructor(
    private val useCase: RecipeUseCase
) : ViewModel() {
    var state = mutableStateOf<Resource<List<Recipe>>>(Resource.Loading())
        private set

    init {
        getFavoriteRecipe()
    }

    fun onEvent(event: FavoriteEvent) {
        when (event) {
            FavoriteEvent.RefreshPage -> {
                getFavoriteRecipe()
            }
        }
    }

    private fun getFavoriteRecipe() {
        viewModelScope.launch {
            useCase.getSavedRecipes()
                .collect {
                    state.value = it
                }
        }
    }
}

sealed class FavoriteEvent {
    data object RefreshPage : FavoriteEvent()
}
