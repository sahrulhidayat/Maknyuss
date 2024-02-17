package com.sahidev.maknyuss.feature.info

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sahidev.maknyuss.data.utils.Constant
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Equipment
import com.sahidev.maknyuss.domain.model.Ingredient
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import com.sahidev.maknyuss.feature.component.CircularLoading
import com.sahidev.maknyuss.feature.component.ErrorScreen
import com.sahidev.maknyuss.feature.component.HtmlText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    onBack: () -> Unit,
    viewModel: InfoViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        snapAnimationSpec = spring(stiffness = Spring.StiffnessHigh)
    )
    var loading by remember { mutableStateOf(true) }

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
                val data = recipeState.data
                    ?: RecipeAndInstructions(Recipe(0, "", ""), emptyList())

                InfoColumn(
                    data = data,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = padding.calculateBottomPadding())
                )
            }
        }
    }
}

@Composable
fun InfoColumn(
    data: RecipeAndInstructions,
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
                    Text(
                        text = data.recipe.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    LazyRow(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        item {
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
                                    imageVector = Icons.Default.MonetizationOn,
                                    contentDescription = "Price",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${data.recipe.price} per serving",
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
                }
            }
        }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IngredientCard(
    ingredients: List<Ingredient>,
    modifier: Modifier = Modifier
) {
    var expanded  by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .animateContentSize()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "All Ingredients",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (expanded) {
                    Icon(imageVector = Icons.Default.ExpandLess, contentDescription = "")
                } else {
                    Icon(imageVector = Icons.Default.ExpandMore, contentDescription = "")
                }
            }

            if (expanded) {
                FlowRow(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ingredients.forEach { ingredient ->
                        Column(
                            modifier = Modifier.padding(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier.size(80.dp)
                            ) {
                                AsyncImage(
                                    model = ingredient.image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .aspectRatio(80f / 80f)
                                        .fillMaxSize()
                                )
                            }
                            Text(
                                text = ingredient.name,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = ingredient.measures ?: "",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EquipmentCard(
    equipments: List<Equipment>,
    modifier: Modifier = Modifier
) {
    var expanded  by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .animateContentSize()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "All Equipments",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (expanded) {
                    Icon(imageVector = Icons.Default.ExpandLess, contentDescription = "")
                } else {
                    Icon(imageVector = Icons.Default.ExpandMore, contentDescription = "")
                }
            }

            if (expanded) {
                FlowRow(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    equipments.forEach { ingredient ->
                        Column(
                            modifier = Modifier.padding(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier.size(80.dp)
                            ) {
                                AsyncImage(
                                    model = ingredient.image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .aspectRatio(80f / 80f)
                                        .fillMaxSize()
                                )
                            }
                            Text(
                                text = ingredient.name,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InstructionCard(
    instruction: Instruction,
    modifier: Modifier = Modifier
) {
    var expanded  by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .animateContentSize()
            .clickable {
                expanded = !expanded
            },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Step: ${instruction.number}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = instruction.step,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            if (expanded) {
                Spacer(modifier = Modifier.size(4.dp))
                EquipmentAndIngredient(
                    instruction.ingredients,
                    instruction.equipments
                )
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = Icons.Default.ExpandLess,
                    contentDescription = "Expand less"
                )
            } else {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Expand more"
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EquipmentAndIngredient(
    ingredients: List<Ingredient>,
    equipments: List<Equipment>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (ingredients.isNotEmpty()) {
            Text(
                text = "Ingredients:",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.size(2.dp))
            FlowRow(
                horizontalArrangement = Arrangement.Center
            ) {
                ingredients.forEach { ingredient ->
                    Column(
                        modifier = Modifier.padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(70.dp)
                        ) {
                            AsyncImage(
                                model = ingredient.image,
                                contentDescription = null,
                                modifier = Modifier
                                    .aspectRatio(70f / 70f)
                                    .fillMaxSize()
                            )
                        }
                        Text(
                            text = ingredient.name,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        if (equipments.isNotEmpty()) {
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Equipments:",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.size(2.dp))
            FlowRow(
                horizontalArrangement = Arrangement.Center
            ) {
                equipments.forEach { equipment ->
                    Column(
                        modifier = Modifier.padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(70.dp)
                        ) {
                            AsyncImage(
                                model = equipment.image,
                                contentDescription = null,
                                modifier = Modifier
                                    .aspectRatio(70f / 70f)
                                    .fillMaxSize()
                            )
                        }
                        Text(
                            text = equipment.name,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}