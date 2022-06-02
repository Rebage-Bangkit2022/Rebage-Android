package trashissue.rebage.presentation.threers.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import trashissue.rebage.R
import trashissue.rebage.presentation.theme3.RebageTheme3

private val DefaultButtonContentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstimatedGarbage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var editMode by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.animateContentSize()) {
        Card(modifier = Modifier.clickable(onClick = onClick)) {
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (editMode) {
                                var item by rememberSaveable { mutableStateOf(0) }
                                AnimatedCounter(
                                    value = item,
                                    onClickDecrement = { if (item != 0) item-- },
                                    onClickIncrement = { item++ }
                                )
                            } else {
                                Text(
                                    text = "3 Items",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                modifier = Modifier.offset(x = 24.dp),
                                text = "Rp18,000",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    MenuActions(
                        modifier.offset(x = 8.dp),
                        onClickEdit = { editMode = true },
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

@Preview
@Composable
fun EstimatedGarbagePreview() {
    RebageTheme3 {
        EstimatedGarbage(onClick = {})
    }
}
