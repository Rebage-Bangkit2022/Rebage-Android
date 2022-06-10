package trashissue.rebage.presentation.garbagebank.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import trashissue.rebage.R

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    onClickNavigation: () -> Unit
) {
    SmallTopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(R.string.text_garbage_bank))
        },
        navigationIcon = {
            IconButton(onClick = onClickNavigation) {
                Icon(
                    Icons.Outlined.ArrowBackIos,
                    stringResource(R.string.cd_back)
                )
            }
        },
    )
}
