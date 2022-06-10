package trashissue.rebage.presentation.maps.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import trashissue.rebage.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoWindow(
    modifier: Modifier = Modifier,
    name: String,
    phoneNumber: String?,
    vicinity: String,
    onClickButtonCheckLocation: () -> Unit,
    onClickCard: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClickCard)
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = name,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                if (phoneNumber != null) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        text = phoneNumber,
                        maxLines = 2,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    text = vicinity,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickButtonCheckLocation
        ) {
            Text(text = stringResource(R.string.text_open_google_maps))
        }
    }
}
