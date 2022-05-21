package trashissue.rebage.presentation.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import trashissue.rebage.presentation.home.component.ChartData
import trashissue.rebage.presentation.home.component.Statistic
import trashissue.rebage.presentation.theme.ForestGreen200
import trashissue.rebage.presentation.theme.ForestGreen500
import trashissue.rebage.presentation.theme.Jasmine500

val GarbageStat = listOf(
    ChartData("Plastik", Color.Red, 4.0),
    ChartData("Kardus", Color.Blue, 3.0),
    ChartData("Bebek", Color.Blue, 16.0),
    ChartData("Ayam", Color.Black, 12.0),
    ChartData("Plastik", Jasmine500, 14.0),
    ChartData("Kardus", Color.Gray, 5.0),
    ChartData("Bebek", Color.Magenta, 6.0),
    ChartData("Ayam", Color.LightGray, 8.0),
    ChartData("Plastik", ForestGreen500, 9.0),
    ChartData("Kardus", ForestGreen500, 20.0),
    ChartData("Bebek", ForestGreen200, 11.0),
    ChartData("Ayam", Color.Gray, 13.0),
)

@Composable
fun HomeScreen() {
    Column {
        Header(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        )
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier
) {
    Surface(color = if (isSystemInDarkTheme()) MaterialTheme.colors.background else MaterialTheme.colors.primary) {
        Column(modifier = modifier) {
            Text(
                text = "Selamat Datang, Tubagus!",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "Ini adalah data sampah yang kamu kumpulkan",
                style = MaterialTheme.typography.body1
            )

            val (data, total) = remember {
                val d = GarbageStat.sortedByDescending { it.value }
                d to d.sumOf { it.value }
            }

            Statistic(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                data = data,
                total = "${total.toInt()}"
            )
        }
    }
}
