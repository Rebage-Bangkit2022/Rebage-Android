package trashissue.rebage.presentation.common.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import trashissue.rebage.presentation.theme.RebageTheme

@Composable
inline fun TwoLineDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    startIndent: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier.weight(1F),
            thickness = thickness,
            startIndent = startIndent
        )
        content()
        Divider(
            modifier = Modifier.weight(1F),
            thickness = thickness,
            startIndent = startIndent
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TwoLineDivider() {
    RebageTheme {
        TwoLineDivider {
            Text(text = "Sign in with google")
        }
    }
}
