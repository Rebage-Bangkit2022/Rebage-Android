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
import trashissue.rebage.presentation.home.DummyGarbageStat

@Composable
fun Header(
    modifier: Modifier = Modifier
) {
    Surface(color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary) {
        Column(modifier = modifier) {
            Text(
                text = "Selamat Datang, Tubagus!",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Ini adalah data sampah yang kamu kumpulkan",
                style = MaterialTheme.typography.bodyLarge
            )

            val (data, total) = remember {
                val d = DummyGarbageStat.sortedByDescending { it.value }
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
