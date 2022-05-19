package trashissue.rebage.presentation.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import trashissue.rebage.R

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
            text = text,
            modifier = Modifier.clickable(onClick = onClickSkip),
            style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.onBackground)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(
        onClickSkip = {},
        text = stringResource(R.string.text_skip)
    )
}
