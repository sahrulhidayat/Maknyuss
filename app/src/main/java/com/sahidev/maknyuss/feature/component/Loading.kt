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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sahidev.maknyuss.feature.util.shimmerEffect
import com.sahidev.maknyuss.ui.theme.backgroundLight

@Composable
fun SearchSkeleton(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier.background(backgroundLight),
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(8.dp),
        userScrollEnabled = false
    ) {
        items(10) {
            RecipeCardSkeleton()
        }
    }
}

@Composable
fun HomeSkeleton(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier.background(backgroundLight),
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(8.dp),
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
                    .aspectRatio(312f / 231f)
            )
        }
    }
}

@Composable
fun RecipeCardSkeleton(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(200.dp)
            .padding(3.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .shimmerEffect()
                    .fillMaxHeight()
                    .align(Alignment.TopCenter)
                    .aspectRatio(240f / 150f)
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .height(70.dp)
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