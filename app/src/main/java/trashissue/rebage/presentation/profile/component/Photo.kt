package trashissue.rebage.presentation.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun Photo(
    modifier: Modifier = Modifier,
    photo: String?,
    size: Dp = 120.dp,
    onClick: () -> Unit
) {
    if (photo != null) {
        AsyncImage(
            model = photo,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.5F))
                .clickable(onClick = onClick)
        )
    } else {
        Box(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.5F))
                .clickable(onClick = onClick)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoPreview() {
    RebageTheme3 {
        Photo(onClick = {}, photo = null)
    }
}
