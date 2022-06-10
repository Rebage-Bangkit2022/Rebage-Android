package trashissue.rebage.presentation.common.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun CounterButton(
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
            imageVector = Icons.Outlined.Remove,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.2F)
                )
                .clickable(onClick = onClickDecrement),
            tint = MaterialTheme.colorScheme.onSurface
        )
        text()
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.2F)
                )
                .clickable(onClick = onClickIncrement),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CounterPreview() {
    RebageTheme3 {
        CounterButton(onClickIncrement = { }, onClickDecrement = { }) {}
    }
}
