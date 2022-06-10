package trashissue.rebage.presentation.threers.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import trashissue.rebage.R
import trashissue.rebage.presentation.common.component.AnimatedCounter
import trashissue.rebage.presentation.theme3.RebageTheme3

private val DefaultButtonContentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstimatedGarbage(
    modifier: Modifier = Modifier,
    label: String,
    image: String?,
    date: String,
    total: Int,
    price: Int,
    onClickButtonSave: (Int) -> Unit,
) {
    var editMode by rememberSaveable { mutableStateOf(false) }

    Column {
        var totalItem by rememberSaveable { mutableStateOf(total) }

        Card(
            modifier = Modifier.border(
                width = 1.dp,
                color = CardDefaults
                    .cardColors()
                    .containerColor(enabled = true).value,
                shape = MaterialTheme.shapes.medium
            )
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(120.dp)

            ) {
                AsyncImage(
                    modifier = Modifier.size(height = 120.dp, width = 100.dp),
                    model = image,
                    contentDescription = label,
                    contentScale = ContentScale.Crop
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
                                text = label,
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 20.sp
                            )
                            Text(
                                modifier = Modifier.padding(top = 4.dp),
                                text = date,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (editMode) {
                                AnimatedCounter(
                                    value = totalItem,
                                    onClickDecrement = { if (totalItem != 1) totalItem-- },
                                    onClickIncrement = { totalItem++ }
                                )
                            } else {
                                Text(
                                    text = "$totalItem Items",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                modifier = Modifier.offset(x = 24.dp),
                                text = "Rp${price * totalItem}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    MenuActions(
                        modifier.offset(x = 8.dp),
                        onClickEdit = {
                            editMode = true
                        },
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
                    onClick = {
                        totalItem = total
                        editMode = false
                    },
                    contentPadding = DefaultButtonContentPadding
                ) {
                    Text(text = stringResource(R.string.text_cancel))
                }
                Button(
                    onClick = {
                        editMode = false
                        onClickButtonSave(totalItem)
                    },
                    contentPadding = DefaultButtonContentPadding
                ) {
                    Text(text = stringResource(R.string.text_save))
                }
            }
        }
    }
}

@Preview
@Composable
fun EstimatedGarbagePreview() {
    RebageTheme3 {
        EstimatedGarbage(
            label = "Plastic",
            image = "https://nexus.prod.postmedia.digital/wp-content/uploads/2018/07/garbage.jpg?quality=90&strip=all&w=400",
            total = 3,
            date = "19 February 2022",
            price = 3000,
            onClickButtonSave = {}
        )
    }
}
