package trashissue.rebage.presentation.common.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import trashissue.rebage.R
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun TextError(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int?
) {
    Text(
        text = textRes?.let { stringResource(it) } ?: " ",
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier.padding(top = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TextErrorPreview() {
    RebageTheme3 {
        TextError(textRes = R.string.error_name_is_required)
    }
}
