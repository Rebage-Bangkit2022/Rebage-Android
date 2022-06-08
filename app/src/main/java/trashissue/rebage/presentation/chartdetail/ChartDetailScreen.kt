package trashissue.rebage.presentation.chartdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import trashissue.rebage.R
import trashissue.rebage.domain.model.DetectionStatistic
import trashissue.rebage.presentation.home.component.ChartData
import trashissue.rebage.presentation.theme3.*

private val DefaultLazyColumnPadding = PaddingValues(16.dp)

@Composable
fun ChartDetailScreen(
    navController: NavHostController,
    viewModel: ChartDetailViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    ChartDetailScreen(
        snackbarHostState = snackbarHostState,
        statsState = viewModel.stats,
        loadingState = viewModel.loading,
        onNavigationBack = navController::popBackStack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartDetailScreen(
    snackbarHostState: SnackbarHostState,
    statsState: StateFlow<List<DetectionStatistic>>,
    loadingState: StateFlow<Boolean>,
    onNavigationBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = "Chart Detail",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationBack) {
                        Icon(
                            Icons.Rounded.ArrowBackIos,
                            stringResource(R.string.cd_back)
                        )
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        val stats by statsState.collectAsState()
        val chartData = remember(stats) {
            stats
                .sortedBy { it.total }
                .take(ChartColors.size)
                .mapIndexed { index, stat ->
                    ChartData(
                        name = stat.label,
                        value = stat.total.toDouble(),
                        color = ChartColors[index]
                    )
                }
        }

        Timber.i("HASIL $chartData $stats")

        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = DefaultLazyColumnPadding,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = chartData, key = { it.name }) { data ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(MaterialTheme.shapes.extraSmall)
                                .background(data.color)
                        )
                        Text(
                            modifier = Modifier.weight(1F),
                            text = data.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${data.value.toInt()}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .alpha(0.5F)
                    )
                }
            }

            val isLoading by loadingState.collectAsState()

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
