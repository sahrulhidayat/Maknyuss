package com.sahidev.maknyuss.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.sahidev.maknyuss.domain.model.Price
import com.sahidev.maknyuss.feature.utils.RandomColors
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PriceBreakDown(
    prices: List<Price>,
    totalPrice: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chartInput = remember { mutableStateOf(emptyList<PieChartInput>()) }
    val isCenterTapped = remember { mutableStateOf(true) }

    prices.map { price ->
        val priceValue = (price.price.toDouble() * 100).roundToInt()
        val color = RandomColors.getColor()
        PieChartInput(
            color = Color(color),
            value = priceValue,
            description = price.ingredientName,
        )
    }.also {
        chartInput.value = it
    }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            color = Color.White
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Price Breakdown",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PieChart(
                        chartInput = chartInput,
                        isCenterTapped = isCenterTapped,
                        radius = 400f,
                        innerRadius = 200f,
                        transparentWidth = 50f,
                        centerText = "$${totalPrice}\nTotal"
                    )
                    Spacer(modifier = Modifier.height(16.dp))


                    LazyColumn {
                        item {
                            FlowRow(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (isCenterTapped.value) {
                                    prices.forEachIndexed { index, price ->
                                        val color = chartInput.value[index].color
                                        PriceItem(
                                            price = price,
                                            color = color
                                        )
                                    }
                                } else {
                                    chartInput.value.find { it.isTapped }
                                        .also { chart ->
                                            if (chart != null) {
                                                val color = chart.color
                                                val price = prices.find {
                                                    it.ingredientName == chart.description
                                                }
                                                PriceItem(
                                                    price = price,
                                                    color = color
                                                )
                                            }
                                        }
                                }
                            }
                        }
                    }

                }
                IconButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = "Close dialog",
                        tint = Color.White,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.DarkGray.copy(0.4f))
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PriceItem(
    price: Price?,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(100.dp)) {
            AsyncImage(
                model = price?.image,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(80f / 80f)
                    .fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (price?.price != null) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            color = color,
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = "$${price.price}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = price.ingredientName,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = price.amountMetric ?: "",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}