package com.toanitdev.taskmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(

    primary = DarkBlue6,//
    onPrimary = LightBlue,
    primaryContainer = Red,
    onPrimaryContainer = LightBlue,
    secondary = Red2,
    onSecondary = LightBlue,
    tertiary = Red,
    onTertiary = LightBlue,
    surface = DarkBlue3,
    surfaceVariant = DarkBlue4,
    surfaceContainerHigh = DarkBlue5,
    onSurface = LightBlue,
    onSurfaceVariant = LightBlue,
    background = DarkBlue2,
    onBackground = LightBlue,
    outline = DarkGray,
    outlineVariant = DarkGray2
)

private val LightColorScheme = lightColorScheme(
    primary = Red,
    onPrimary = White,
    primaryContainer = Cyan,
    onPrimaryContainer = White,
    secondary = Red2,
    onSecondary = White,
    tertiary = Red,
    onTertiary = White,
    surface = LightRed2,
    surfaceVariant = LightRed2,
    surfaceContainerHigh = LightRed3,
    onSurface = DarkBlue,
    onSurfaceVariant = DarkBlue,
    background = LightRed,
    onBackground = Black,
    outline = LightGray2,
    outlineVariant = LightGray3
)

@Composable
fun TaskManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}