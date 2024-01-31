package com.sahidev.maknyuss.feature.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {

        }
    }
}