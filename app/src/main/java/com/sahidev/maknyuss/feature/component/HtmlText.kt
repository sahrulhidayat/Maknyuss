package com.sahidev.maknyuss.feature.component

import android.graphics.text.LineBreaker
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    val text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    val color = MaterialTheme.colorScheme.onBackground.toArgb()

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = text.toString()
            },
        factory = { context ->
            TextView(context).apply {
                setTextColor(color)
                setLinkTextColor(color)
            }
        },
        update = {
            it.text = text
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                it.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            }
        }
    )
}