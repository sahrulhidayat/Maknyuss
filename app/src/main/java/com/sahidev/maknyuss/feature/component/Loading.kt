package com.sahidev.maknyuss.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sahidev.maknyuss.feature.utils.shimmerEffect

@Composable
fun HomeSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(5) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(312f / 231f)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(250.dp),
        ) {
            items(10) {
                RecipeCardSkeleton()
            }
        }
    }
}

@Composable
fun RecipeCardSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .shimmerEffect()
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            ),
    ) {
        Box(
            modifier = Modifier
                .shimmerEffect()
                .fillMaxWidth()
                .aspectRatio(240f / 150f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .shimmerEffect()
                .height(4.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}