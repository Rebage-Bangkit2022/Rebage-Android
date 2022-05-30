package trashissue.rebage.presentation.detection.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        var counter by remember { mutableStateOf(0) }

        LaunchedEffect(Unit) {
            while (true) {
                delay(1000)
                counter++
            }
        }

        Text(
            text = "$counter",
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = "Loading...",
            style = MaterialTheme.typography.titleSmall
        )
    }
}
