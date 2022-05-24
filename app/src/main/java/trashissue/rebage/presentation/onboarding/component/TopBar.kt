package trashissue.rebage.presentation.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import trashissue.rebage.R
import trashissue.rebage.presentation.common.noRippleClickable
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onClickSkip: () -> Unit,
    text: String
) {
    Row(
        modifier = modifier
            .systemBarsPadding()
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logohorizontal),
            contentDescription = null
        )
        Text(
            modifier = Modifier.noRippleClickable(onClick = onClickSkip),
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    RebageTheme3 {
        TopBar(
            onClickSkip = {},
            text = stringResource(R.string.text_skip)
        )
    }
}
