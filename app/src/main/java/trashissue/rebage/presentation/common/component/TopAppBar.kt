package trashissue.rebage.presentation.common.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import okhttp3.internal.notify
import androidx.compose.material.TopAppBar as MaterialTopAppBar

@Composable
@Suppress("NOTHING_TO_INLINE")
inline fun TopAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    contentPadding: PaddingValues = PaddingValues(
        start = 4.dp,
        top = with(LocalDensity.current) { WindowInsets.statusBars.getTop(this).toDp() },
        end = 4.dp
    ),
    noinline content: @Composable RowScope.() -> Unit
) {
    MaterialTopAppBar(
        modifier,
        backgroundColor,
        contentColor,
        elevation,
        contentPadding,
        content = content
    )
}

fun Modifier.topAppBarHeight() = composed {
    TODO()
}
