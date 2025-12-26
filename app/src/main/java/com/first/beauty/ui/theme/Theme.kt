package com.first.beauty.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)

private val DarkColorScheme = darkColorScheme(
    primary = ChampagneGold,
    onPrimary = OnyxBlack,

    secondary = WarmTaupe,
    onSecondary = OnyxBlack,

    background = OnyxBlack,
    onBackground = PorcelainWhite,

    surface = CharcoalGray,
    onSurface = PorcelainWhite,

    error = Pink80,
    onError = OnyxBlack
)

private val LightColorScheme = lightColorScheme(
    primary = ChampagneGold,      // Buttons, highlights
    onPrimary = OnyxBlack,

    secondary = WarmTaupe,        // Secondary UI elements
    onSecondary = OnyxBlack,

    background = SoftIvory,       // App background
    onBackground = CharcoalGray,

    surface = PorcelainWhite,     // Cards, surfaces
    onSurface = OnyxBlack,

    error = Pink40,
    onError = PorcelainWhite
)

@Composable
fun SchöneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Update status bar colors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}