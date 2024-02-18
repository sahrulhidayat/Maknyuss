package com.sahidev.maknyuss.feature.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.sahidev.maknyuss.data.utils.Constant
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.feature.component.EmptyScreen
import com.sahidev.maknyuss.feature.component.ErrorScreen
import com.sahidev.maknyuss.feature.component.Menu
import com.sahidev.maknyuss.feature.component.RecipeGrid
import com.sahidev.maknyuss.feature.component.RecipeGridSkeleton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    onBack: () -> Unit,
    onClickItem: (id: Int) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = Menu.FAVORITE.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
            )
        }
    ) { padding ->
        when (val state = viewModel.state.value) {
            is Resource.Error -> {
                ErrorScreen(
                    message = state.message ?: Constant.DEFAULT_ERROR_MESSAGE,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = padding.calculateBottomPadding())
                )
            }

            is Resource.Loading -> {
                RecipeGridSkeleton(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = padding.calculateBottomPadding())
                )
            }

            is Resource.Success -> {
                val data = state.data

                if (data.isNullOrEmpty()) {
                    EmptyScreen(modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = padding.calculateBottomPadding()))
                } else {
                    RecipeGrid(
                        data = data,
                        topPadding = padding.calculateTopPadding(),
                        onClickItem = onClickItem,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = padding.calculateBottomPadding())
                    )
                }

            }
        }
    }
}