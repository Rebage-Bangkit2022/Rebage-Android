package trashissue.rebage.presentation.home.component

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
import androidx.compose.ui.unit.dp
import trashissue.rebage.domain.model.DetectionStatistic
import trashissue.rebage.presentation.theme3.BlueGChart
import trashissue.rebage.presentation.theme3.PastelChart
import trashissue.rebage.presentation.theme3.SiennaChart
import trashissue.rebage.presentation.theme3.TealChart
import java.util.*

@Composable
fun Header(
    modifier: Modifier = Modifier,
    name: String? = null,
    stats: List<DetectionStatistic>
) {
    Surface(color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary) {
        Column(modifier = modifier) {
            val firstName = remember(name) {
                name
                    ?.split(" ")
                    ?.firstOrNull()
                    ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            }

            Text(
                text = if (firstName == null) " " else "Selamat Datang, $firstName!",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Ini adalah data sampah yang kamu kumpulkan",
                style = MaterialTheme.typography.bodyLarge
            )

            val (data, total) = remember(stats){
                val colors = mutableListOf(
                    SiennaChart,
                    BlueGChart,
                    PastelChart,
                    TealChart
                )

                val statsSorted = stats.sortedByDescending { it.total }

                statsSorted
                    .take(colors.size)
                    .map {
                        ChartData(
                            name = it.label,
                            value = it.total.toDouble(),
                            color = colors.removeFirst()
                        )
                    } to statsSorted.sumOf { it.total }
            }

            Statistic(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                data = data,
                total = "$total"
            )
        }
    }
}
