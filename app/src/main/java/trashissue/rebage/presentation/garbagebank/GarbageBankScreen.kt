package trashissue.rebage.presentation.garbagebank

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import trashissue.rebage.R
import trashissue.rebage.domain.model.GarbageBank
import trashissue.rebage.presentation.garbagebank.component.GarbageBankCard
import trashissue.rebage.presentation.main.Route

private val DefaultLazyColumnPadding = PaddingValues(16.dp)

@Composable
fun GarbageBankScreen(
    navController: NavHostController,
    viewModel: GarbageBankViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.loadGarbageBank(-7.983908, 112.621391)

        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    GarbageBankScreen(
        snackbarHostState = snackbarHostState,
        garbageBankState = viewModel.garbageBanks,
        onNavigationBack = navController::popBackStack,
        onClickCheckLocation = { lat, lng ->
            navController.navigate(Route.Maps(lat, lng))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarbageBankScreen(
    snackbarHostState: SnackbarHostState,
    garbageBankState: StateFlow<List<GarbageBank>>,
    onNavigationBack: () -> Unit,
    onClickCheckLocation: (Double, Double) -> Unit
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Garbage Bank")
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
        val garbageBanks by garbageBankState.collectAsState()

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = DefaultLazyColumnPadding
        ) {
            items(items = garbageBanks, key = { it.id }) { garbageBank ->
                GarbageBankCard(
                    name = garbageBank.name,
                    vicinity = garbageBank.vicinity,
                    onClickCheckLocation = {
                        onClickCheckLocation(garbageBank.lat, garbageBank.lng)
                    }
                )
            }
        }
    }
}
