package trashissue.rebage.presentation.home.component

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

@Composable
fun Articles(
    modifier: Modifier = Modifier,
    label: String
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
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
            modifier = Modifier.padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.width(4.dp))
            }
            items(10, key = { it }) {
                Article(
                    modifier = Modifier
                        .width(240.dp)
                        .padding(bottom = 12.dp),
                    title = "Cara Indonesia Kurangi Sampah Plastik hingga 70 Persen"
                )
            }
            item {
                Spacer(modifier = Modifier.width(4.dp))
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
