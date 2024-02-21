package com.sahidev.maknyuss.feature.info

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import com.sahidev.maknyuss.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {
    var recipeState = mutableStateOf<Resource<RecipeAndInstructions>>(Resource.Loading())
        private set
    var showPriceBreakDown = mutableStateOf(false)
        private set

    private val recipeId: Int = checkNotNull(savedStateHandle[RECIPE_ID])

    init {
        getRecipeInfo(recipeId)
    }

    fun onEvent(event: InfoEvent) {
        when (event) {
            is InfoEvent.ToggleFavorite -> {
                viewModelScope.launch {
                    val recipe = event.data.recipe
                        .copy(
                            favorite = event.value,
                            timestamp = System.currentTimeMillis()
                        )
                    val instructions = event.data.instructions

                    if (event.value) {
                        recipeUseCase.addRecipe(recipe)
                        instructions.forEach { instruction ->
                            recipeUseCase.addInstruction(instruction)
                        }
                        getRecipeInfo(recipeId)
                    } else {
                        recipeUseCase.deleteRecipe(recipe)
                        getRecipeInfo(recipeId)
                    }
                }
            }

            is InfoEvent.ShowPriceBreakDown -> {
                showPriceBreakDown.value = event.value

                if (event.value) {
                    viewModelScope.launch {
                        recipeUseCase
                            .getPriceBreakDown(recipeState.value.data ?: return@launch)
                            .collect { data ->
                                recipeState.value = data
                            }
                    }
                }
            }

            InfoEvent.PullRefresh -> {
                getRecipeInfo(recipeId)
            }
        }
    }

    private fun getRecipeInfo(id: Int) {
        viewModelScope.launch {
            recipeUseCase.getRecipeInfo(id)
                .collect { data ->
                    recipeState.value = data
                }
        }
    }
}

sealed class InfoEvent {
    data class ToggleFavorite(val value: Boolean, val data: RecipeAndInstructions) : InfoEvent()
    data class ShowPriceBreakDown(val value: Boolean) : InfoEvent()
    data object PullRefresh : InfoEvent()
}