package trashissue.rebage.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import trashissue.rebage.R

@Composable
fun OnboardingScreen() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logohorizontal),
                contentDescription = null
            )
            TextButton(
                onClick = {

                }
            ) {
                TextButton(
                    onClick = {

                    }
                ) {
                    Text(text = stringResource(R.string.text_skip))
                }
            }
        }
    }
}
