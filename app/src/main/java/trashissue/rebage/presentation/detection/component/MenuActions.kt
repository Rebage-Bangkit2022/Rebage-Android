package trashissue.rebage.presentation.detection.component

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import trashissue.rebage.R
import trashissue.rebage.presentation.common.component.noRippleClickable
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun MenuActions(
    modifier: Modifier = Modifier,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit,
    onClickHelp: () -> Unit
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
                    Text(stringResource(R.string.text_edit))
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
            DropdownMenuItem(
                text = {
                    Text(stringResource(R.string.text_delete))
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
            MenuDefaults.Divider()
            DropdownMenuItem(
                text = {
                    Text(stringResource(R.string.text_help))
                },
                onClick = {
                    expanded = false
                    onClickHelp()
                }
            )
        }
    }
}

@Preview
@Composable
fun MenuActionsPreview() {
    RebageTheme3 {
        MenuActions(
            onClickEdit = { },
            onClickDelete = { },
            onClickHelp = { }
        )
    }
}
