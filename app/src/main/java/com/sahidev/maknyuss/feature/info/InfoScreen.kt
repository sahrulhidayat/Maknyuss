package com.sahidev.maknyuss.feature.info

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sahidev.maknyuss.data.utils.Constant
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import com.sahidev.maknyuss.feature.component.CircularLoading
import com.sahidev.maknyuss.feature.component.EquipmentCard
import com.sahidev.maknyuss.feature.component.ErrorScreen
import com.sahidev.maknyuss.feature.component.HtmlText
import com.sahidev.maknyuss.feature.component.IngredientCard
import com.sahidev.maknyuss.feature.component.InstructionCard
import com.sahidev.maknyuss.feature.component.PriceBreakDown

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InfoScreen(
    onBack: () -> Unit,
    viewModel: InfoViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        snapAnimationSpec = spring(stiffness = Spring.StiffnessHigh)
    )
    var loading by remember { mutableStateOf(true) }
    val showPriceBreakDown = viewModel.showPriceBreakDown.value

    var refreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            viewModel.onEvent(InfoEvent.PullRefresh)
        }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (!loading) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.DarkGray.copy(0.4f))
                                    .padding(8.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    ),
                    scrollBehavior = scrollBehavior
                )
            }
        }
    ) { padding ->
        when (val recipeState = viewModel.recipeState.value) {
            is Resource.Error -> {
                ErrorScreen(
                    message = recipeState.message ?: Constant.DEFAULT_ERROR_MESSAGE,
                    onClickAction = { viewModel.onEvent(InfoEvent.PullRefresh) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = padding.calculateBottomPadding())
                )
            }

            is Resource.Loading -> {
                loading = true
                CircularLoading(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = padding.calculateBottomPadding())
                )
            }

            is Resource.Success -> {
                loading = false
                refreshing = false
                val data = recipeState.data ?: return@Scaffold

                Box(
                    modifier = Modifier
                        .pullRefresh(pullRefreshState)
                        .padding(bottom = padding.calculateBottomPadding())
                ) {
                    InfoColumn(
                        data = data,
                        toggleFavorite = { value ->
                            viewModel.onEvent(InfoEvent.ToggleFavorite(value, data))
                        },
                        onPriceClick = {
                            viewModel.onEvent(InfoEvent.ShowPriceBreakDown(true))
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                    if (data.recipe.priceBreakDown.isNotEmpty() && showPriceBreakDown) {
                        PriceBreakDown(
                            prices = data.recipe.priceBreakDown,
                            totalPrice = data.recipe.totalCost ?: "",
                            onDismissRequest = {
                                viewModel.onEvent(
                                    InfoEvent.ShowPriceBreakDown(false)
                                )
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    PullRefreshIndicator(
                        refreshing = loading,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}

@Composable
fun InfoColumn(
    data: RecipeAndInstructions,
    toggleFavorite: (Boolean) -> Unit,
    onPriceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val localDensity = LocalDensity.current
    var imageHeightDp by remember { mutableStateOf(250.dp) }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Box {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.TopCenter)
                ) {
                    AsyncImage(
                        model = data.recipe.image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .aspectRatio(556f / 370f)
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                imageHeightDp =
                                    with(localDensity) { coordinates.size.height.toDp() }
                            }
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = imageHeightDp - 16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (data.recipe.favorite) {
                            IconButton(onClick = { toggleFavorite(false) }) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Toggle favorite",
                                    tint = Color.Red
                                )
                            }
                        } else {
                            IconButton(onClick = { toggleFavorite(true) }) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Toggle not favorite"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = data.recipe.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    LazyRow(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .clickable { onPriceClick() }
                                    .background(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MonetizationOn,
                                    contentDescription = "Price per serving",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${data.recipe.pricePerServing} per serving",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = "Ready minutes",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${data.recipe.readyMinutes.toString()} min",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Recommend,
                                    contentDescription = "Likes",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = data.recipe.likes ?: "0",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Restaurant,
                                    contentDescription = "Servings",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${data.recipe.servings} servings",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                    HtmlText(html = data.recipe.summary ?: "<b>No descriptions</b>")
                    Spacer(modifier = Modifier.size(4.dp))
                    IngredientCard(ingredients = data.recipe.ingredients)
                    Spacer(modifier = Modifier.size(6.dp))
                    EquipmentCard(equipments = data.recipe.equipments)
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "Instructions:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (data.instructions.isEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No instructions provided",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Gray,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }
            }
        }
        if (data.instructions.isNotEmpty()) {
            items(data.instructions) { instruction ->
                InstructionCard(instruction = instruction)
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Bon Appetit \uD83D\uDC4C")
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}