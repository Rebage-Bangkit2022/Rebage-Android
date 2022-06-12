package trashissue.rebage.presentation.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import trashissue.rebage.R
import trashissue.rebage.presentation.common.component.noRippleClickable

val AppearanceOptions = mapOf(
    R.string.text_dark_theme to true,
    R.string.text_light_theme to false,
    R.string.text_default to null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceDialog(
    modifier: Modifier = Modifier,
    selected: Boolean?,
    onChange: (Boolean?) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {},
        title = {
            Text(text = "Appearance")
        },
        text = {
            Column {
                for ((nameRes, value) in AppearanceOptions) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable {
                                onChange(value)
                            },
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selected == value,
                            onClick = {
                                onChange(value)
                            }
                        )
                        Text(text = stringResource(nameRes))
                    }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.text_cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.text_save))
            }
        }
    )
}
