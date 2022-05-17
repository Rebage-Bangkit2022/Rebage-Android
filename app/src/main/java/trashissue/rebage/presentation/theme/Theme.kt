package trashissue.rebage.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = ForestGreen200,
    primaryVariant = ForestGreen700,
    secondary = Jasmine200
)

private val LightColorPalette = lightColors(
    primary = ForestGreen500,
    primaryVariant = ForestGreen700,
    secondary = Jasmine500,
    secondaryVariant = Jasmine700
)

@Composable
fun RebageTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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
