package trashissue.rebage.presentation.profile.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileMenuItem(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    text: String,
    icon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Outlined.ArrowForwardIos,
            contentDescription = null
        )
    }
) {
    Card(modifier = modifier.wrapContentSize()) {
        Row(
            modifier = Modifier
                .clickable(enabled = onClick != null, onClick = onClick ?: {})
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            icon()
        }
    }
}
