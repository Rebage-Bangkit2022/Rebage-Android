package trashissue.rebage.presentation.price

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.StateFlow
import trashissue.rebage.R
import trashissue.rebage.domain.model.Garbage
import trashissue.rebage.presentation.common.component.AnimatedCounter
import trashissue.rebage.presentation.common.component.Dropdown
import trashissue.rebage.presentation.main.Route

@Composable
fun PriceScreen(
    navController: NavHostController,
    viewModel: PriceViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    PriceScreen(
        snackbarHostState = snackbarHostState,
        garbageState = viewModel.garbage,
        onNavigateToGarbageBank = {
            navController.navigate(Route.GarbageBank())
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceScreen(
    snackbarHostState: SnackbarHostState,
    garbageState: StateFlow<List<Garbage>>,
    onNavigateToGarbageBank: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = stringResource(R.string.text_price))
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToGarbageBank,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface,
                text = {
                    Text(text = stringResource(R.string.text_garbage_bank))
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Map,
                        contentDescription = null
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .animateContentSize()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val garbage by garbageState.collectAsState()
                    val options = rememberSaveable(garbage) { garbage.map { it.name } }
                    var selected by rememberSaveable { mutableStateOf<Garbage?>(null) }

                    Dropdown(
                        options = options,
                        value = selected?.name ?: "",
                        onValueChange = { value ->
                            selected = garbage.find { it.name == value }
                        }
                    )

                    val image = selected?.image
                    val name = selected?.name
                    val price = selected?.price

                    if (image != null && name != null && price != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(height = 120.dp, width = 100.dp)
                                    .clip(MaterialTheme.shapes.small),
                                model = image,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                            var totalItem by rememberSaveable { mutableStateOf(1) }

                            AnimatedCounter(
                                modifier = Modifier.align(Alignment.Bottom),
                                value = totalItem,
                                onClickDecrement = { if (totalItem != 1) totalItem-- },
                                onClickIncrement = { totalItem++ }
                            )
                            Spacer(modifier = Modifier.weight(1F))
                            Text(
                                modifier = Modifier.align(Alignment.Bottom),
                                text = "Rp${price * totalItem}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
