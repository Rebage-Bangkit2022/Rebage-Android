package trashissue.rebage.presentation.camera.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Cameraswitch
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import timber.log.Timber

private val DefaultActionCornerShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

@Composable
fun CameraCapture(
    modifier: Modifier = Modifier,
    state: CameraState,
    onClickGallery: () -> Unit = { },
    onClickCapture: () -> Unit = { },
    onClickSwitchCamera: () -> Unit = {}
) {
    Box(
        modifier = modifier
    ) {
        LaunchedEffect(state.selector) {
            try {
                state.bind()
            } catch (e: Exception) {
                Timber.e(e, "Failed to bind camera use cases")
            }
        }

        CameraPreview(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 98.dp),
            preview = { state.preview }
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(120.dp)
                .clip(DefaultActionCornerShape)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                onClick = onClickGallery
            ) {
                Icon(
                    imageVector = Icons.Outlined.ImageSearch,
                    contentDescription = "Gallery",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                onClick = onClickCapture
            ) {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = "Gallery",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                onClick = onClickSwitchCamera
            ) {
                Icon(
                    imageVector = Icons.Outlined.Cameraswitch,
                    contentDescription = "Gallery",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

