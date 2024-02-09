package com.sahidev.maknyuss.feature.component

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    val text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { it.text = text }
    )
}