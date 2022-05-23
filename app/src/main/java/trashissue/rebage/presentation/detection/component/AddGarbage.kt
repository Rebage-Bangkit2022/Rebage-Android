package trashissue.rebage.presentation.detection.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import trashissue.rebage.R

val DefaultButtonContentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGarbage(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit
) {
    Card {
        Column(
            modifier = modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DropdownGarbage()
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.End)
            ) {
                TextButton(
                    onClick = onCancel,
                    contentPadding = DefaultButtonContentPadding
                ) {
                    Text(
                        text = stringResource(R.string.text_cancel),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                TextButton(
                    onClick = onCancel,
                    contentPadding = DefaultButtonContentPadding
                ) {
                    Text(text = stringResource(R.string.text_save))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownGarbage(
    modifier: Modifier = Modifier
) {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = {
                Text(stringResource(R.string.text_choose_item))
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                focusManager.clearFocus()
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        focusManager.clearFocus()
                        expanded = false
                    },
                    text = {
                        Text(text = selectionOption)
                    }
                )
            }
        }
    }
}
