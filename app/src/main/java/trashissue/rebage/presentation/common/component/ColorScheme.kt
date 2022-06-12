package trashissue.rebage.presentation.common.component

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.luminance

val ColorScheme.isLight: Boolean
    get() = this.background.luminance() > 0.5

val ColorScheme.isDark: Boolean
    get() = !isLight
