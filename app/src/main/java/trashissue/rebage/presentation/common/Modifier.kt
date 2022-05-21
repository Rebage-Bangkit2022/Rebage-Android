package trashissue.rebage.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Modifier.statusBarsPaddingWithColor(color: Color? = null): Modifier = composed {
    val density = LocalDensity.current
    val height = WindowInsets.statusBars.getTop(density).toFloat()
    val darkMode = isSystemInDarkTheme()
    val backgroundColor = color ?: MaterialTheme.colors.primary

    drawBehind {
        val width = size.width
        drawRect(
            color = if (darkMode) Color.Transparent else backgroundColor,
            size = Size(width, height)
        )
    }.statusBarsPadding()
}
