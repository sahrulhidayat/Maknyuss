package com.sahidev.maknyuss.feature.info

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahidev.maknyuss.data.source.local.media.ImagesMediaProvider
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import com.sahidev.maknyuss.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipeUseCase: RecipeUseCase,
    private val imagesProvider: ImagesMediaProvider
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

            is InfoEvent.ShareRecipe -> {
                val displayName = "RecipeImage_${event.id}"
                viewModelScope.launch {
                    val imageBitmap = imagesProvider.getBitmapFromUrl(event.image)
                    var imageUri = imagesProvider.getImage("${displayName}.jpg")?.uri
                    if (imageUri == null) {
                        imageUri = imagesProvider.saveBitmap(
                            imageBitmap,
                            displayName,
                            Bitmap.CompressFormat.JPEG,
                            "image/jpeg"
                        )
                    }

                    val text = "Hey, I have found an interesting recipe:\n\n*${event.title}*\n" +
                            "\nWanna try cooking it? Get the recipe from Maknyuss App. Download from PlayStore:" +
                            "\nhttps://play.google.com/store/apps/details?id=com.sahidev.maknyuss"

                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_STREAM, imageUri)
                        type = "image/jpeg"
                        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        putExtra(Intent.EXTRA_TEXT, text)
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    event.context.startActivity(shareIntent)
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
    data class ShareRecipe(
        val context: Context,
        val id: Int,
        val title: String,
        val image: String
    ) : InfoEvent()

    data object PullRefresh : InfoEvent()
}