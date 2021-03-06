package trashissue.rebage.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import trashissue.rebage.R
import trashissue.rebage.presentation.common.component.noRippleClickable
import trashissue.rebage.presentation.common.component.shimmer

private val DefaultContentPadding = PaddingValues(12.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Statistic(
    modifier: Modifier = Modifier,
    data: List<ChartData>,
    total: String,
    onClickShowMore: () -> Unit = { },
    contentPadding: PaddingValues = DefaultContentPadding,
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .wrapContentSize(),
        ) {

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
                        data = data,
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
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .weight(4F)
                    .padding(12.dp)
            ) {

                for (garbage in data) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticPlaceHolder(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = DefaultContentPadding,
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .fillMaxHeight()
                .wrapContentSize(),
        ) {

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
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1F)
                            .shimmer(
                                shape = CircleShape,
                                startColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2F),
                                endColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5F),
                                drawBehind = true
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .size(maxWidth - 32.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .align(Alignment.Center)
                        )
                    }
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "0",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.text_total_garbage),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .weight(4F)
                    .padding(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.text_dont_have_garbage),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.text_use_detection_feature),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
