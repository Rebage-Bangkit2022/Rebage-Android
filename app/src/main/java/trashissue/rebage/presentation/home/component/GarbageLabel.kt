package trashissue.rebage.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun GarbageLabel(
    modifier: Modifier = Modifier,
    name: String,
    color: Color,
    value: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )
        Text(
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            text = name,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
fun GarbageLabelPreview() {
    RebageTheme3 {
        GarbageLabel(
            name = "",
            color = MaterialTheme.colorScheme.primary,
            value = "Trash"
        )
    }
}
