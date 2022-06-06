package trashissue.rebage.presentation.threers.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import trashissue.rebage.R

@Composable
fun TopAppBar(
    title: String?,
    onClickNavigation: () -> Unit
) {
    SmallTopAppBar(
        title = {
            Text(text = title ?: "")
        },
        navigationIcon = {
            IconButton(onClick = onClickNavigation) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIos,
                    contentDescription = stringResource(R.string.cd_back)
                )
            }
        }
    )
}
