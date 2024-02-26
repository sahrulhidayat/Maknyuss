package com.sahidev.maknyuss.feature.about

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sahidev.maknyuss.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About Maknyuss App",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Maknyuss app is a recipe app. The recipe data and images are obtained from Spoonacular API." +
                        "The information may not be 100 percents accurate, so use it wisely and only as an estimate." +
                        "\nThis application uses cache to optimize user experience and network usage. No user data " +
                        "collected within this app.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("sahrulhint@gmail.com"))
                        }
                        emailIntent.setPackage("com.google.android.gm")
                        context.startActivity(emailIntent)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                }
                Spacer(modifier = Modifier.size(8.dp))
                IconButton(
                    onClick = {
                        val instagramIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://instagram.com/sahidev_")
                        )
                        instagramIntent.setPackage("com.instagram.android")
                        context.startActivity(instagramIntent)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.instagram),
                        contentDescription = "Instagram"
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                IconButton(
                    onClick = {
                        val linkedInIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://linkedin.com/in/sahrul-hidayat")
                        )
                        linkedInIntent.setPackage("com.linkedin.android")
                        context.startActivity(linkedInIntent)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.linkedin),
                        contentDescription = "LinkedIn"
                    )
                }
            }
        }
    }
}