package com.sahidev.maknyuss.feature.info

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
        modifier = modifier,
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
                if (data.recipe.veryPopular) {
                    Icon(
                        imageVector = Icons.Default.Stars,
                        tint = Color.Yellow,
                        contentDescription = "Very Popular",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(top = 6.dp, end = 6.dp)
                            .align(Alignment.TopEnd)
                    )
                }
            }
            Column(modifier = modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = data.recipe.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                ) {
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
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = data.recipe.price ?: "-",
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
                            modifier = Modifier.size(18.dp)
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
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = data.recipe.likes ?: "-",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
                HtmlText(html = data.recipe.summary ?: "<b>No descriptions</b>")
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
                Text(text = "Bon Appetit")
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun InstructionCard(instruction: Instruction, modifier: Modifier = Modifier) {
    val showEquipmentAndIngredient = remember { mutableStateOf(false) }

    Card(modifier = modifier
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .clickable {
            showEquipmentAndIngredient.value = !showEquipmentAndIngredient.value
        }
    ) {
        Row(
            modifier = modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = instruction.number.toString())
            Spacer(modifier = Modifier.width(4.dp))
            Column(modifier = modifier.fillMaxWidth()) {
                Text(
                    text = instruction.step,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                if (showEquipmentAndIngredient.value) {
                    EquipmentAndIngredient(
                        instruction.ingredients,
                        instruction.equipments
                    )
                }
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
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        if (ingredients.isNotEmpty()) {
            Text(
                text = "Used ingredients:",
                style = MaterialTheme.typography.titleSmall
            )
            FlowRow {
                ingredients.forEach { ingredient ->
                    Column {
                        AsyncImage(
                            model = ingredient.image,
                            contentDescription = null,
                            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                        )
                        Text(
                            text = ingredient.name,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        if (equipments.isNotEmpty()) {
            Text(
                text = "Used equipments:",
                style = MaterialTheme.typography.titleSmall
            )
            FlowRow {
                equipments.forEach { equipment ->
                    Column {
                        AsyncImage(
                            model = equipment.image,
                            contentDescription = null,
                            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                        )
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
