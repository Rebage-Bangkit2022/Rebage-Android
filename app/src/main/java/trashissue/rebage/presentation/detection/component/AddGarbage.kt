package trashissue.rebage.presentation.detection.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import trashissue.rebage.R
import trashissue.rebage.presentation.theme3.RebageTheme3

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
                var item by remember { mutableStateOf(0) }

                AnimatedCounter(
                    value = item,
                    onClickDecrement = { if (item != 0) item-- },
                    onClickIncrement = { item++ }
                )
                Spacer(modifier = Modifier.weight(1F))
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

@Preview
@Composable
fun AddGarbagePreview() {
    RebageTheme3 {
        AddGarbage {}
    }
}
