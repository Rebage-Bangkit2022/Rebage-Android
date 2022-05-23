package trashissue.rebage.presentation.detection.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import trashissue.rebage.presentation.common.noRippleClickable

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
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                DropdownGarbage()
                Icon(
                    modifier = Modifier.noRippleClickable(onClick = onCancel),
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete"
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jumlah Deteksi",
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RowScope.DropdownGarbage(
    modifier: Modifier = Modifier
) {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        modifier = modifier.weight(1F),
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
                Text("Pilih Sampah")
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
