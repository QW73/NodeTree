package com.example.nodetree.ui.utils

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SystemUiConfig() {
    val systemUiController = rememberSystemUiController()

    val isAndroid14orHigher =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE // API 34 (Android 14)

    if (isAndroid14orHigher) {
        // VERSION => Android 14
        systemUiController.setStatusBarColor(
            color = Color.Transparent, // Прозрачный статус-бар
            darkIcons = MaterialTheme.colorScheme.onPrimary.luminance() > 0.5f
        )
    } else {
        // VERSION << Android 13
        systemUiController.setStatusBarColor(
            color = MaterialTheme.colorScheme.primary,
            darkIcons = MaterialTheme.colorScheme.primary.luminance() > 0.5f
        )
    }
}