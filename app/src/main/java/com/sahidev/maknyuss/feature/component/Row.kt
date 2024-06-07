package com.sahidev.maknyuss.feature.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sahidev.maknyuss.domain.model.Equipment
import com.sahidev.maknyuss.domain.model.Ingredient
import com.sahidev.maknyuss.domain.model.Instruction

@Composable
fun IngredientRow(
    ingredients: List<Ingredient>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(ingredients) { ingredient ->
            Card(
                modifier = Modifier.padding(end = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(80.dp)
                    ) {
                        AsyncImage(
                            model = ingredient.image,
                            contentDescription = ingredient.name,
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
                        text = ingredient.amountMetric ?: "",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun EquipmentRow(
    equipments: List<Equipment>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(equipments) { equipment ->
            Card(
                modifier = Modifier.padding(end = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(80.dp)
                    ) {
                        AsyncImage(
                            model = equipment.image,
                            contentDescription = equipment.name,
                            modifier = Modifier
                                .aspectRatio(80f / 80f)
                                .fillMaxSize()
                        )
                    }
                    Text(
                        text = equipment.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun InstructionRow(
    instruction: Instruction,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { expanded = !expanded }
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .sizeIn(minHeight = 48.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${instruction.number}. ${instruction.step}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.weight(1f)
            )
        }
        if (expanded) {
            EquipmentAndIngredient(
                instruction.ingredients,
                instruction.equipments
            )
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
                                contentDescription = ingredient.name,
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
                                contentDescription = equipment.name,
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsRow(dishTypes: List<String>, diets: List<String>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = "Tags:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(4.dp))
        FlowRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            dishTypes.forEach { dishType ->
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(vertical = 2.dp, horizontal = 4.dp)
                ) {
                    Text(
                        text = dishType,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                    )
                }
            }
            diets.forEach { diet ->
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(vertical = 2.dp, horizontal = 4.dp)
                ) {
                    Text(
                        text = diet,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun IngredientRowPreview() {
    IngredientRow(
        ingredients = listOf(
            Ingredient("Ingredient 1", "", "Ingredient 1", "100g", "100g"),
            Ingredient("Ingredient 2", "", "Ingredient 2", "200g", "200g")
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun InstructionRowPreview(@PreviewParameter(LoremIpsum::class) lorem: String) {
    InstructionRow(
        instruction = Instruction(
            1,
            lorem.take(100),
            listOf(),
            listOf(),
            1,
            1
        )
    )
}