package trashissue.rebage.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val DefaultContentPadding = PaddingValues(12.dp)

@Composable
fun Statistic(
    modifier: Modifier = Modifier,
    data: List<ChartData>,
    total: String,
    onClickShowMore: () -> Unit = { },
    contentPadding: PaddingValues = DefaultContentPadding,
    elevation: Dp = 8.dp
) {
    Card(
        modifier = modifier,
        elevation = elevation
    ) {
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
                        style = MaterialTheme.typography.h4
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Total Sampah",
                    style = MaterialTheme.typography.h6
                )
            }
            Column(
                modifier = Modifier
                    .weight(4F)
                    .padding(12.dp)
            ) {


                for (garbage in top3) {
                    Garbage(
                        name = garbage.name,
                        color = garbage.color,
                        value = "${garbage.value.toInt()}"
                    )
                }
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp),
                    text = "Lihat selengkapnya",
                    style = MaterialTheme.typography.body1,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Composable
private fun Garbage(
    modifier: Modifier = Modifier,
    name: String,
    color: Color,
    value: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )
        Text(
            modifier = Modifier
                .weight(1F)
                .padding(8.dp),
            text = name,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2
        )
    }
}
