package trashissue.rebage.presentation.detection.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.*
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
                Text(
                    text = stringResource(R.string.text_helper_preview),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.helper_preview),
                    contentDescription = stringResource(R.string.text_helper_preview),
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.text_helper_content),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.helper_content),
                    contentDescription = stringResource(R.string.text_helper_content),
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
