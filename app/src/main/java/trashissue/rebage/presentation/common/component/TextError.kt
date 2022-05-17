package trashissue.rebage.presentation.common.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import trashissue.rebage.R
import trashissue.rebage.presentation.common.FieldState
import trashissue.rebage.presentation.theme.RebageTheme

@Composable
fun TextError(
    field: FieldState,
    modifier: Modifier = Modifier
) {
    Text(
        text = field.errorMessage?.let { stringResource(it) } ?: " ",
        color = MaterialTheme.colors.error,
        style = MaterialTheme.typography.caption,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TextErrorPreview() {
    RebageTheme {
        TextError(
            field = FieldState(
                value = "bagus",
                errorMessage = R.string.error_invalid_email
            )
        )
    }
}
