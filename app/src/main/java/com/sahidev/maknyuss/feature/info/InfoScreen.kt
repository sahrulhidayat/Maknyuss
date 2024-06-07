package com.sahidev.maknyuss.feature.info

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sahidev.maknyuss.data.utils.Constant
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import com.sahidev.maknyuss.feature.component.CircularLoading
import com.sahidev.maknyuss.feature.component.EquipmentRow
import com.sahidev.maknyuss.feature.component.ErrorScreen
import com.sahidev.maknyuss.feature.component.HtmlText
import com.sahidev.maknyuss.feature.component.IngredientRow
import com.sahidev.maknyuss.feature.component.InstructionRow
import com.sahidev.maknyuss.feature.component.PriceBreakDown
import com.sahidev.maknyuss.feature.component.TagsRow
import com.sahidev.maknyuss.feature.component.showInterstitialAd
import com.sahidev.maknyuss.ui.theme.MaknyussTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InfoScreen(
    onBack: () -> Unit,
    viewModel: InfoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    showInterstitialAd(context) {}

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

    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (!loading) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = onBack,
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = Color(0, 0, 0, 150),
                                containerColor = Color(255, 255, 255, 100)
                            )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier
                                    .clip(CircleShape)
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
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        when (val recipeState = viewModel.recipeState.value) {
            is Resource.Error -> {
                ErrorScreen(
                    message = recipeState.message ?: Constant.DEFAULT_ERROR_MESSAGE,
                    showAction = true,
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
                        onShareRecipe = {
                            val id = data.recipe.id
                            val title = data.recipe.title
                            val image = data.recipe.image
                            viewModel.onEvent(InfoEvent.ShareRecipe(context, id, title, image))
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
    onShareRecipe: () -> Unit,
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
                        contentDescription = data.recipe.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .aspectRatio(312f / 231f)
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                imageHeightDp =
                                    with(localDensity) { coordinates.size.height.toDp() }
                            }
                    )
                }
                Column(
                    modifier = Modifier.padding(top = imageHeightDp - 64.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(64.dp)
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    0f to Color.Transparent,
                                    1f to MaterialTheme.colorScheme.background
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = data.recipe.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.size(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = "Ready minutes",
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${data.recipe.readyMinutes.toString()} min",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.outline,
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Recommend,
                                    contentDescription = "Likes",
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = data.recipe.likes ?: "0",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Restaurant,
                                    contentDescription = "Servings",
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${data.recipe.servings} servings",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$${data.recipe.pricePerServing}",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = "per serving",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            IconButton(
                                onClick = { onPriceClick() }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = "Open price breakdown",
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            if (data.recipe.favorite) {
                                IconButton(
                                    onClick = { toggleFavorite(false) }
                                ) {
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
                                        contentDescription = "Toggle not favorite",
                                        tint = MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                            IconButton(onClick = { onShareRecipe() }) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share recipe",
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }
                        }

                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        HtmlText(html = data.recipe.summary ?: "<b>No descriptions</b>")
                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.size(8.dp))
                        IngredientRow(ingredients = data.recipe.ingredients)
                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = "Equipments",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.size(8.dp))
                        EquipmentRow(equipments = data.recipe.equipments)
                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = "Instructions",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        if (data.instructions.isEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
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
        }
        if (data.instructions.isNotEmpty()) {
            items(data.instructions) { instruction ->
                InstructionRow(instruction = instruction)
            }
        }
        if (data.recipe.dishTypes.isNotEmpty() || data.recipe.diets.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.size(8.dp))
                TagsRow(
                    dishTypes = data.recipe.dishTypes,
                    diets = data.recipe.diets
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
fun InfoScreenPreview(@PreviewParameter(LoremIpsum::class) lorem: String) {
    val dummyData = RecipeAndInstructions(
        recipe = Recipe(
            0,
            lorem.take(50),
            "image",
            pricePerServing = "2",
            summary = lorem.take(100),
            likes = "10",
            readyMinutes = 20,
            servings = 3,
            dishTypes = listOf("tag 1", "tag 2", "tag 3"),
            diets = listOf("tag 1", "tag 2", "tag 3"),
            favorite = true
        ),
        instructions = listOf(
            Instruction(1, lorem.take(100), emptyList(), emptyList(), 0)
        )
    )
    MaknyussTheme {
        InfoColumn(data = dummyData, toggleFavorite = { }, onPriceClick = { }, onShareRecipe = { })
    }
}