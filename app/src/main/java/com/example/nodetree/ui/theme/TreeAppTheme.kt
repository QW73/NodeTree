package com.example.nodetree.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TreeAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors, typography = Typography, content = content
    )
}

private val LightColors = lightColorScheme(
    primary = Color(0xFF455A64),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF212121),
    background = Color(0xFFF2F2F2),
    error = Color(0xFF9B2929),
)

private val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 18.sp
    ), bodyMedium = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 14.sp
    )
)

