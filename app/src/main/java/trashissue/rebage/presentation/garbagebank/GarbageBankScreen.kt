package trashissue.rebage.presentation.garbagebank

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import trashissue.rebage.R
import trashissue.rebage.domain.model.Place
import trashissue.rebage.presentation.common.awaitLastLocation
import trashissue.rebage.presentation.garbagebank.component.GarbageBankCard
import trashissue.rebage.presentation.main.Route

private val DefaultLazyColumnPadding = PaddingValues(16.dp)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GarbageBankScreen(
    navController: NavHostController,
    viewModel: GarbageBankViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val locationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    LaunchedEffect(locationPermissionState.status) {
        if (context.isLocationPermissionNotGranted) {
            return@LaunchedEffect
        }
        @SuppressLint("MissingPermission")
        val location = locationProviderClient.awaitLastLocation()

        viewModel.loadPlaces(location.latitude, location.longitude)
    }

    when (locationPermissionState.status) {
        PermissionStatus.Granted -> {
            GarbageBankScreen(
                snackbarHostState = snackbarHostState,
                placesState = viewModel.places,
                onNavigationBack = navController::popBackStack,
                onClickCheckLocation = { placeId ->
                    navController.navigate(Route.Maps(placeId))
                }
            )
        }
        is PermissionStatus.Denied -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        locationPermissionState.launchPermissionRequest()
                    }
                ) {
                    Text(text = "Launch Permission")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarbageBankScreen(
    snackbarHostState: SnackbarHostState,
    placesState: StateFlow<List<Place>>,
    onNavigationBack: () -> Unit,
    onClickCheckLocation: (String) -> Unit
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
        val places by placesState.collectAsState()

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = DefaultLazyColumnPadding
        ) {
            items(items = places, key = { it.id }) { place ->
                GarbageBankCard(
                    name = place.name,
                    vicinity = place.vicinity,
                    onClickCheckLocation = { onClickCheckLocation(place.id) }
                )
            }
        }
    }
}

private val Context.isLocationPermissionNotGranted: Boolean
    get() {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }
