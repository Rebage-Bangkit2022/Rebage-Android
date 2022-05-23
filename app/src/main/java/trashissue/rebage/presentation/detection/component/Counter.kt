package trashissue.rebage.presentation.detection.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import trashissue.rebage.presentation.common.noRippleClickable

@Composable
fun Counter(
    modifier: Modifier = Modifier,
    onClickIncrement: () -> Unit,
    onClickDecrement: () -> Unit,
    text: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = Modifier.noRippleClickable(onClick = onClickDecrement)
        )
        text()
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = Modifier.noRippleClickable(onClick = onClickIncrement)
        )
    }
}
