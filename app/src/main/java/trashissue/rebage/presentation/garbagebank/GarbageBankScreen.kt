package trashissue.rebage.presentation.garbagebank

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import trashissue.rebage.R
import trashissue.rebage.domain.model.Place
import trashissue.rebage.presentation.common.awaitLastLocation
import trashissue.rebage.presentation.common.toast
import trashissue.rebage.presentation.garbagebank.component.GarbageBankCard
import trashissue.rebage.presentation.garbagebank.component.TopAppBar
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
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    var isDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    LaunchedEffect(locationPermissionState.status) {
        if (context.isLocationPermissionNotGranted) {
            return@LaunchedEffect
        }
        try {
            val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)

            @SuppressLint("MissingPermission")
            val location = locationProviderClient.awaitLastLocation()
            if (location != null) {
                viewModel.loadPlaces(location.latitude, location.longitude)
            } else {
                isDialogOpen = true
            }
        } catch (e: Exception) {
            Timber.e(e)
            toast(context, R.string.text_unknown_error)
            navController.popBackStack()
        }
    }

    when (locationPermissionState.status) {
        PermissionStatus.Granted -> {
            GarbageBankScreen(
                snackbarHostState = snackbarHostState,
                placesState = viewModel.places,
                loadingState = viewModel.loading,
                onClickNavigation = navController::popBackStack,
                onClickCheckLocation = { placeId ->
                    navController.navigate(Route.Maps(placeId))
                }
            )
        }
        is PermissionStatus.Denied -> {
            GarbageBankScreen(
                onClickAllowAccess = locationPermissionState::launchPermissionRequest,
                onClickOpenSettings = context::launchPermissionSettings,
                onClickNavigation = navController::popBackStack
            )
        }
    }

    if (isDialogOpen) {
        AlertDialog(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null
                )
            },
            text = {
                Text(text = "Your GPS seems to be disabled, do you want to enable it?")
            },
            onDismissRequest = {
                isDialogOpen = false
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isDialogOpen = false
                        navController.popBackStack()
                    }
                ) {
                    Text(text = "Dismiss")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        context.launchLocationSourceSettings()
                        isDialogOpen = false
                        navController.popBackStack()
                    }
                ) {
                    Text(text = "Open Settings")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarbageBankScreen(
    snackbarHostState: SnackbarHostState,
    placesState: StateFlow<List<Place>>,
    loadingState: StateFlow<Boolean>,
    onClickNavigation: () -> Unit,
    onClickCheckLocation: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TopAppBar(onClickNavigation = onClickNavigation)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            val places by placesState.collectAsState()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
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

            val isLoading by loadingState.collectAsState()

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarbageBankScreen(
    onClickAllowAccess: () -> Unit,
    onClickOpenSettings: () -> Unit,
    onClickNavigation: () -> Unit
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopAppBar(onClickNavigation = onClickNavigation)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.location_marker)
            )
            LottieAnimation(
                modifier = Modifier
                    .padding(32.dp)
                    .size(164.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickAllowAccess
            ) {
                Text(text = "Allow Access")
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickOpenSettings
            ) {
                Text(text = "Open Settings")
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

private fun Context.launchLocationSourceSettings() {
    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
}

private fun Context.launchPermissionSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}
