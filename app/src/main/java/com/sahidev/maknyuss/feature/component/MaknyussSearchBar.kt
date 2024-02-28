package com.sahidev.maknyuss.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Launch
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sahidev.maknyuss.data.utils.Constant
import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaknyussSearchBar(
    query: MutableState<String>,
    searchHistory: List<Search>,
    autoCompleteSearch: Resource<List<Search>>,
    onClearAutoComplete: () -> Unit,
    onQueryChange: (query: String) -> Unit,
    onSearch: (query: String) -> Unit,
    onAutoCompleteSearch: (id: Int?) -> Unit,
    onDeleteSearchHistory: (query: String) -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var active by remember { mutableStateOf(false) }

    val colorGradient = arrayOf(
        0.0f to Color(0xFF008A9F),
        0.31f to Color(0xFF00A179),
        1f to Color(0x0000CC30)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(colorStops = colorGradient))
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        SearchBar(
            query = query.value,
            onQueryChange = {
                query.value = it
                onQueryChange(it)
            },
            onSearch = {
                onSearch(it)
                active = false
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(
                    text = "Search Recipe",
                    color = Color.Gray
                )
            },
            leadingIcon = {
                if (active) {
                    IconButton(
                        onClick = {
                            active = false
                            query.value = ""
                            onClearAutoComplete()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back icon"
                        )
                    }
                } else {
                    IconButton(
                        onClick = { onMenuClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Toggle navigation drawer"
                        )
                    }
                }
            },
            trailingIcon = {
                if (active && query.value.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            query.value = ""
                            onClearAutoComplete()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close icon"
                        )
                    }
                }
            },
            colors = SearchBarDefaults.colors(containerColor = Color.White)
        ) {
            when (autoCompleteSearch) {
                is Resource.Error -> {
                    ErrorScreen(
                        message = autoCompleteSearch.message ?: Constant.DEFAULT_ERROR_MESSAGE,
                        showAction = false,
                        onClickAction = {}
                    )
                }

                is Resource.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = "Loading recipes ...")
                        }
                    }
                }

                is Resource.Success -> {
                    val queryBlank = query.value.isBlank()
                    val autoCompleteEmpty = autoCompleteSearch.data?.isEmpty() == true
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (!queryBlank) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        onSearch(query.value)
                                        active = false
                                    }
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                                Text(
                                    text = "Search \"${query.value}\"",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        if (autoCompleteEmpty) {
                            searchHistory.forEach { search ->
                                Row(
                                    modifier = Modifier
                                        .clickable {
                                            onSearch(search.query)
                                            query.value = search.query
                                            active = false
                                        }
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.padding(8.dp),
                                        imageVector = Icons.Default.History,
                                        contentDescription = "History icon"
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = search.query,
                                        modifier = Modifier.weight(1f)
                                    )
                                    IconButton(
                                        onClick = {
                                            onDeleteSearchHistory(search.query)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Delete history"
                                        )
                                    }
                                }
                            }
                        }
                        if (!autoCompleteEmpty && !queryBlank) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                autoCompleteSearch.data?.forEach {
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                onAutoCompleteSearch(it.id)
                                                query.value = it.query
                                                active = false
                                            }
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .size(150.dp, 100.dp)
                                        ) {
                                            AsyncImage(
                                                model = it.image,
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .aspectRatio(312f / 231f)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = it.query,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(
                                            modifier = Modifier.padding(8.dp),
                                            imageVector = Icons.AutoMirrored.Default.Launch,
                                            contentDescription = "Launch icon"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}