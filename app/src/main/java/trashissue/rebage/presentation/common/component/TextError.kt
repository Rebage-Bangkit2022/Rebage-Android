package trashissue.rebage.presentation.common.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import trashissue.rebage.R
import trashissue.rebage.presentation.common.FieldState
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun TextError(
    field: FieldState,
    modifier: Modifier = Modifier
) {
    Text(
        text = field.errorMessage?.let { stringResource(it) } ?: " ",
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelSmall,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TextErrorPreview() {
    RebageTheme3 {
        TextError(
            field = FieldState(
                value = "bagus",
                errorMessage = R.string.error_invalid_email
            )
        )
    }
}
