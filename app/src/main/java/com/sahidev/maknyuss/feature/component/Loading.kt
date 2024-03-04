package com.sahidev.maknyuss.feature.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sahidev.maknyuss.feature.utils.shimmerEffect
import com.sahidev.maknyuss.ui.theme.backgroundLight

@Composable
fun CircularLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .semantics {
                stateDescription = "Loading"
            },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun RecipeGridSkeleton(
    modifier: Modifier = Modifier,
    topPadding: Dp = 8.dp,
) {
    LazyVerticalGrid(
        modifier = modifier
            .background(backgroundLight)
            .semantics {
                stateDescription = "Loading"
            },
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(start = 8.dp, top = topPadding, end = 8.dp, bottom = 8.dp),
        userScrollEnabled = false
    ) {
        items(10) {
            RecipeCardSkeleton()
        }
    }
}

@Composable
fun HomeSkeleton(
    modifier: Modifier = Modifier,
    topPadding: Dp = 8.dp,
) {
    LazyVerticalGrid(
        modifier = modifier
            .background(backgroundLight)
            .semantics {
                stateDescription = "Loading"
            },
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(start = 8.dp, top = topPadding, end = 8.dp, bottom = 8.dp),
        userScrollEnabled = false
    ) {
        item(
            span = {
                GridItemSpan(maxCurrentLineSpan)
            }
        ) {
            SlideSkeleton()
        }
        items(10) {
            RecipeCardSkeleton()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlideSkeleton(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { 1 })

    HorizontalPager(
        state = pagerState,
        modifier = modifier.wrapContentSize()
    ) {
        Card(
            modifier = modifier
                .wrapContentSize()
                .padding(4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = modifier
                    .shimmerEffect()
                    .fillMaxSize()
                    .aspectRatio(480f / 360f)
            )
        }
    }
}

@Composable
fun RecipeCardSkeleton(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(250.dp)
            .padding(3.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .shimmerEffect()
                    .fillMaxHeight()
                    .align(Alignment.TopCenter)
                    .aspectRatio(480f / 360f)
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .align(Alignment.BottomCenter)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .shimmerEffect()
                        .height(7.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(horizontal = 6.dp)
                        .shimmerEffect()
                        .height(7.dp)
                )
            }
        }
    }
}