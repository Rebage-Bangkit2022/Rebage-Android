package trashissue.rebage.presentation.home.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import trashissue.rebage.presentation.home.GarbageStat

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
