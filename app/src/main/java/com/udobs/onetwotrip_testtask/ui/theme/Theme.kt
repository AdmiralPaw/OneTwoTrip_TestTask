package com.udobs.onetwotrip_testtask.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = AntiFlashWhite,
    primaryVariant = AntiFlashWhite,
    secondary = VividCerulean,
    secondaryVariant = SilverChalice,
    background = RaisinBlack,
    surface = AntiFlashWhite,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = AntiFlashWhite,
    primaryVariant = AntiFlashWhite,
    secondary = VividCerulean,
    secondaryVariant = SilverChalice,
    background = RaisinBlack,
    surface = AntiFlashWhite,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.Black,
)

@Composable
fun OneTwoTrip_TestTaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}