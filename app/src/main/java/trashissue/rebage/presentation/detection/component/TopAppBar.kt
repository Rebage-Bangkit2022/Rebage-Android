package trashissue.rebage.presentation.detection.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import trashissue.rebage.R

@Composable
fun TopAppBar(
    onClickCameraScan: () -> Unit,
    enabledCameraScan: Boolean = true
) {
    SmallTopAppBar(
        title = {
            Text(text = stringResource(R.string.text_detection))
        },
        actions = {
            TextButton(
                onClick = onClickCameraScan,
                enabled = enabledCameraScan
            ) {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = stringResource(R.string.cd_scan_object)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(R.string.text_scan))
            }
        }
    )
}
