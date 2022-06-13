package trashissue.rebage.presentation.detection.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import trashissue.rebage.R

@Composable
fun HelperDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Outlined.HelpOutline,
                contentDescription = null
            )
        },
        text = {
            Column {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.helper_preview),
                    contentDescription = "Tap image to se preview",
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.helper_content),
                    contentDescription = "Tap image to navigate to 3R screen",
                    contentScale = ContentScale.FillWidth
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.text_ok))
            }
        }
    )
}
