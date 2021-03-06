package trashissue.rebage.presentation.maps

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import trashissue.rebage.R
import trashissue.rebage.domain.model.Place
import trashissue.rebage.presentation.common.awaitLastLocation
import trashissue.rebage.presentation.maps.component.InfoWindow


@Composable
fun MapsScreen(
    navController: NavHostController,
    viewModel: MapsViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    MapsScreen(
        snackbarHostState = snackbarHostState,
        placeState = viewModel.place,
        onNavigationBack = navController::popBackStack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(
    snackbarHostState: SnackbarHostState,
    placeState: StateFlow<Place?>,
    onNavigationBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = stringResource(R.string.text_garbage_bank))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIos,
                            contentDescription = stringResource(R.string.cd_back)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            val context = LocalContext.current
            val place by placeState.collectAsState()
            val cameraPositionState = rememberCameraPositionState()
            val uiSettings = remember {
                MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = true
                )
            }
            val properties = remember {
                MapProperties(
                    isMyLocationEnabled = true,
                    isIndoorEnabled = true
                )
            }
            val lat = place?.lat
            val lng = place?.lng

            LaunchedEffect(place) {
                val locationProviderClient = LocationServices
                    .getFusedLocationProviderClient(context)

                try {
                    @SuppressLint("MissingPermission")
                    val location = locationProviderClient.awaitLastLocation()
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 20F)
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }

                if (lat != null && lng != null) {
                    val latLng = LatLng(lat, lng)
                    cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(latLng, 20F))
                }
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = uiSettings,
                properties = properties
            ) {
                if (lat != null && lng != null) {
                    val markerState = rememberMarkerState(position = LatLng(lat, lng))
                    Marker(state = markerState)
                }
            }

            val scope = rememberCoroutineScope()
            val name = place?.name
            val phoneNumber = place?.phoneNumber
            val vicinity = place?.vicinity

            if (name != null && vicinity != null) {
                InfoWindow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .align(Alignment.BottomCenter),
                    name = name,
                    phoneNumber = phoneNumber,
                    vicinity = vicinity,
                    onClickButtonCheckLocation = {
                        val placeId = place?.id ?: return@InfoWindow
                        val uri = Uri.parse(
                            "https://www.google.com/maps/search/?api=1&query=a&query_place_id=$placeId"
                        )
                        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        if (mapIntent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(mapIntent)
                        }
                    },
                    onClickCard = {
                        scope.launch {
                            if (lat != null && lng != null) {
                                val latLng = LatLng(lat, lng)
                                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(latLng, 20F))
                            }
                        }
                    }
                )
            }
        }
    }
}
