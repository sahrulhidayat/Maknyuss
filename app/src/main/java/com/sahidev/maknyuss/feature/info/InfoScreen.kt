package com.sahidev.maknyuss.feature.info

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Equipment
import com.sahidev.maknyuss.domain.model.Ingredient
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import com.sahidev.maknyuss.feature.component.HtmlText

@Composable
fun InfoScreen(
    viewModel: InfoViewModel = hiltViewModel()
) {
    Scaffold { padding ->
        when (val recipeState = viewModel.recipeState.value) {
            is Resource.Error -> {
                Text(modifier = Modifier.padding(padding), text = "Error")
            }

            is Resource.Loading -> {
                Text(modifier = Modifier.padding(padding), text = "Loading")
            }

            is Resource.Success -> {
                val data = recipeState.data
                    ?: RecipeAndInstructions(Recipe(0, "", ""), emptyList())

                InfoColumn(
                    data,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun InfoColumn(data: RecipeAndInstructions, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            Box(
                modifier = modifier.wrapContentSize()
            ) {
                AsyncImage(
                    model = data.recipe.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(556f / 370f)
                        .fillMaxWidth()
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                )
            }
            Column(modifier = modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = data.recipe.title,
                    style = MaterialTheme.typography.titleMedium
                )
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 10.dp)
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
                                imageVector = Icons.Default.AttachMoney,
                                contentDescription = "Price",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${data.recipe.price} per serving",
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
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${data.recipe.servings} servings",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
                HtmlText(html = data.recipe.summary ?: "<b>No descriptions</b>")
                Spacer(modifier = Modifier.size(4.dp))
                IngredientRow(ingredients = data.recipe.ingredients)
                Spacer(modifier = Modifier.size(6.dp))
                EquipmentRow(equipments = data.recipe.equipments)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = "Instructions:")
            }
        }
        items(data.instructions) { instruction ->
            InstructionCard(instruction)
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
fun IngredientRow(ingredients: List<Ingredient>, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.clickable { expanded = !expanded },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
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
            if (!expanded) {
                Icon(imageVector = Icons.Default.ExpandMore, contentDescription = "")
            } else {
                Icon(imageVector = Icons.Default.ExpandLess, contentDescription = "")
            }
        }

        if (expanded) {
            FlowRow(
                modifier = Modifier.padding(4.dp)
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EquipmentRow(equipments: List<Equipment>, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.clickable { expanded = !expanded },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
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
            if (!expanded) {
                Icon(imageVector = Icons.Default.ExpandMore, contentDescription = "")
            } else {
                Icon(imageVector = Icons.Default.ExpandLess, contentDescription = "")
            }
        }

        if (expanded) {
            FlowRow(
                modifier = Modifier.padding(4.dp)
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

@Composable
fun InstructionCard(instruction: Instruction, modifier: Modifier = Modifier) {
    val showEquipmentAndIngredient = remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                showEquipmentAndIngredient.value = !showEquipmentAndIngredient.value
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
            if (showEquipmentAndIngredient.value) {
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
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (ingredients.isNotEmpty()) {
            Text(
                text = "Ingredients:",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.size(2.dp))
            FlowRow {
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
            FlowRow {
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
