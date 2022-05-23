package trashissue.rebage.presentation.detection.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import trashissue.rebage.presentation.common.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ScannedGarbage(
    modifier: Modifier = Modifier
) {
    Card {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)

        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray)
            )

            var isInEditMode by rememberSaveable { mutableStateOf(false) }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Plastic",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "17:05 12 Mei 2019",
                            modifier = Modifier.padding(top = 4.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F)
                        )
                    }

                    if (isInEditMode) {
                        var item by rememberSaveable { mutableStateOf(0) }

                        Counter(
                            onClickDecrement = { item-- },
                            onClickIncrement = { item++ }
                        ) {
                            AnimatedContent(item) {
                                Text(
                                    text = "$item",
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "3 Items",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }

                DropDownScannedGarbage(
                    onClickEdit = {
                        isInEditMode = true
                    },
                    onClickDelete = {}
                )
            }
        }
    }
}

@Composable
fun DropDownScannedGarbage(
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit
) {
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
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
                text = { Text("Edit") },
                onClick = onClickEdit,
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = null
                    )
                })
            MenuDefaults.Divider()
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = onClickDelete,
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
