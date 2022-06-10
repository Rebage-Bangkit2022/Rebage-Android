package trashissue.rebage.presentation.home.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import trashissue.rebage.domain.model.DetectionStatistic
import trashissue.rebage.presentation.theme3.ChartColors
import java.util.*

@Composable
fun Header(
    modifier: Modifier = Modifier,
    name: String? = null,
    stats: List<DetectionStatistic>,
    onClickShowMore: () -> Unit
) {
    Surface(color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary) {
        Column(modifier = modifier.animateContentSize()) {
            val firstName = remember(name) {
                name
                    ?.split(" ")
                    ?.firstOrNull()
                    ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            }

            Text(
                text = if (firstName == null) " " else "Selamat Datang, $firstName!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.surface
            )
            Text(
                text = "Ini adalah data sampah yang kamu kumpulkan",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.surface
            )

            val (data, total) = remember(stats){
                val statsSorted = stats.sortedByDescending { it.total }
                val colors = ChartColors.take(3).toMutableList()

                var displayed = statsSorted.take(3)
                    .take(colors.size)
                    .map {
                        ChartData(
                            name = it.label,
                            value = it.total.toDouble(),
                            color = colors.removeFirst()
                        )
                    }

                displayed = if (statsSorted.size > 3) displayed.plus(
                    ChartData(
                        name = "Other",
                        color = Color.Gray.copy(alpha = 0.2F),
                        value = statsSorted.drop(3).sumOf { it.total }.toDouble()
                    )
                ) else displayed

                displayed to statsSorted.sumOf { it.total }
            }

            Crossfade(targetState = data.isNotEmpty()) { isNotEmpty ->
                when (isNotEmpty) {
                    true -> Statistic(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        data = data,
                        total = "$total",
                        onClickShowMore = onClickShowMore
                    )
                    else -> StatisticPlaceHolder(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
