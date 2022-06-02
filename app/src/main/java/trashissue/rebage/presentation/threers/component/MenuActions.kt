package trashissue.rebage.presentation.threers.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import trashissue.rebage.presentation.common.noRippleClickable
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun MenuActions(
    modifier: Modifier = Modifier,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit
) {
    Box(modifier = modifier.wrapContentSize(Alignment.TopStart)) {
        var expanded by remember { mutableStateOf(false) }

        Icon(
            Icons.Default.MoreVert,
            contentDescription = "More options",
            modifier = Modifier.noRippleClickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text("Edit")
                },
                onClick = {
                    expanded = false
                    onClickEdit()
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = null
                    )
                })
            MenuDefaults.Divider()
            DropdownMenuItem(
                text = {
                    Text("Delete")
                },
                onClick = {
                    expanded = false
                    onClickDelete()
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun MenuActionsPreview() {
    RebageTheme3 {
        MenuActions(onClickEdit = { }) {}
    }
}
