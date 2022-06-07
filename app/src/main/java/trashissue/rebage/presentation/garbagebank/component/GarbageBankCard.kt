package trashissue.rebage.presentation.garbagebank.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarbageBankCard(
    modifier: Modifier = Modifier,
    name: String,
    vicinity: String,
    onClickCheckLocation: () -> Unit
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = name,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                text = vicinity,
                maxLines = 2,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.End),
                onClick = onClickCheckLocation
            ) {
                Text(text = "Check Location")
            }
        }
    }
}
