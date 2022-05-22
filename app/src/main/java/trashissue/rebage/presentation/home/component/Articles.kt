package trashissue.rebage.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import trashissue.rebage.R
import trashissue.rebage.presentation.theme.RebageTheme

private val ContentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)

@Composable
fun Articles(
    modifier: Modifier = Modifier,
    label: String
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = label,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                style = MaterialTheme.typography.button,
                text = stringResource(R.string.text_see_more),
                color = MaterialTheme.colors.primary
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = ContentPadding
        ) {
            items(10, key = { it }) {
                Article(
                    modifier = Modifier.width(240.dp),
                    title = "Cara Indonesia Kurangi Sampah Plastik hingga 70 Persen"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesPreview() {
    RebageTheme {
        Articles(label = stringResource(R.string.text_recommended_articles))
    }
}
