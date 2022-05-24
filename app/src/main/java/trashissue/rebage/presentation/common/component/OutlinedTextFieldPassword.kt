package trashissue.rebage.presentation.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import trashissue.rebage.R
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun OutlinedTextFieldPassword(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        label = {
            Text(text = stringResource(R.string.text_password))
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    visible = !visible
                }
            ) {
                if (visible) {
                    Icon(
                        imageVector = Icons.Outlined.Visibility,
                        contentDescription = stringResource(R.string.cd_pass_visible)
                    )
                    return@IconButton
                }
                Icon(
                    imageVector = Icons.Outlined.VisibilityOff,
                    contentDescription = stringResource(R.string.cd_pass_visible)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldPasswordPreview() {
    RebageTheme3 {
        OutlinedTextFieldPassword(
            value = "naruto",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
