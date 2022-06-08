package trashissue.rebage.presentation.price

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import trashissue.rebage.presentation.common.component.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceScreen(
    viewModel: PriceViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Price")
                }
            )
        }
    ) { innerPadding ->
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                repeat(1000) {
                    delay(1000)
                    Timber.i("HELLO $it")
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(
                    CardDefaults
                        .cardColors()
                        .containerColor(enabled = true).value
                )
        ) {
            var enabled by remember {
                mutableStateOf(false)
            }

            Button(
                onClick = {
                    enabled = !enabled
                }
            ) {
                Text(text = "Hello")
            }
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .shimmer(enabled, CircleShape)
            )
        }
    }
}
