package trashissue.rebage.presentation.detection.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import trashissue.rebage.R
import trashissue.rebage.presentation.theme3.RebageTheme3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownGarbage(
    modifier: Modifier = Modifier,
    options: List<String>,
    value: String,
    onValueChange: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
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
            value = value,
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
            options.forEach { selected ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(selected)
                        focusManager.clearFocus()
                        expanded = false
                    },
                    text = {
                        Text(text = selected)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DropdownGarbagePreview() {
    RebageTheme3 {
        DropdownGarbage(
            options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5"),
            value = "Duck",
            onValueChange = {}
        )
    }
}
