package trashissue.rebage.presentation.detection.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import trashissue.rebage.R
import trashissue.rebage.presentation.common.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ScannedGarbage(
    modifier: Modifier = Modifier
) {
    var editMode by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.animateContentSize()) {
        Card {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(120.dp)

            ) {
                Box(
                    modifier = Modifier
                        .size(height = 120.dp, width = 100.dp)
                        .background(Color.Gray)
                )

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
                                modifier = Modifier.padding(top = 4.dp),
                                text = "17:05 12 Mei 2019",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F)
                            )
                        }

                        if (editMode) {
                            var item by rememberSaveable { mutableStateOf(0) }

                            Counter(
                                onClickDecrement = { item-- },
                                onClickIncrement = { item++ }
                            ) {
                                AnimatedContent(item) {
                                    Text(
                                        text = "$item",
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier
                                            .padding(horizontal = 12.dp)
                                            .widthIn(min = 16.dp),
                                        textAlign = TextAlign.Center,
                                        textDecoration = TextDecoration.Underline
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = "3 Items",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    DropDownScannedGarbage(
                        onClickEdit = {
                            editMode = true
                        },
                        onClickDelete = {}
                    )
                }
            }
        }
        if (editMode) {
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                OutlinedButton(
                    onClick = { editMode = false },
                    contentPadding = DefaultButtonContentPadding
                ) {
                    Text(text = stringResource(R.string.text_cancel))
                }
                Button(
                    onClick = { editMode = false },
                    contentPadding = DefaultButtonContentPadding
                ) {
                    Text(text = stringResource(R.string.text_save))
                }
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
