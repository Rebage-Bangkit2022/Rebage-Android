package trashissue.rebage.presentation.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import trashissue.rebage.R
import trashissue.rebage.presentation.common.noRippleClickable

private val DefaultContentPadding = PaddingValues(12.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Statistic(
    modifier: Modifier = Modifier,
    data: List<ChartData>,
    total: String,
    onClickShowMore: () -> Unit = { },
    contentPadding: PaddingValues = DefaultContentPadding,
    elevation: Dp = 8.dp
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .wrapContentSize(),
        ) {
            val top3 = remember { data.take(3) }

            Column(
                modifier = Modifier
                    .weight(3F)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1F)
                ) {

                    Doughnut(
                        width = 16.dp,
                        data = top3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1F)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = total,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.text_total_garbage),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column(
                modifier = Modifier
                    .weight(4F)
                    .padding(12.dp)
            ) {

                for (garbage in top3) {
                    GarbageLabel(
                        name = garbage.name,
                        color = garbage.color,
                        value = "${garbage.value.toInt()}"
                    )
                }
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp)
                        .noRippleClickable(onClick = onClickShowMore),
                    text = stringResource(R.string.text_see_more),
                    style = MaterialTheme.typography.labelLarge,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}
