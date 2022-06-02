package trashissue.rebage.presentation.detection

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import trashissue.rebage.R
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.empty
import trashissue.rebage.domain.model.error
import trashissue.rebage.domain.model.success
import trashissue.rebage.presentation.camera.CameraActivity
import trashissue.rebage.presentation.detection.component.AddGarbage
import trashissue.rebage.presentation.detection.component.BoundingBoxScaffold
import trashissue.rebage.presentation.detection.component.ScannedGarbage
import trashissue.rebage.presentation.detection.component.rememberDetectionScaffoldState
import trashissue.rebage.presentation.main.Route
import java.io.File
import java.util.*

private val ContentPadding = PaddingValues(16.dp)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetectionScreen(
    navController: NavHostController,
    viewModel: DetectionViewModel = hiltViewModel()
) {
    val detectionScaffoldState = rememberDetectionScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }
    var firstPreview by rememberSaveable { mutableStateOf(true) }

    BoundingBoxScaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        state = detectionScaffoldState,
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = stringResource(R.string.text_detection))
                },
                actions = {
                    val context = LocalContext.current
                    val cameraLauncher = rememberCameraLauncher(
                        onSuccess = { file ->
                            viewModel.detectGarbage(file)
                            firstPreview = true
                        },
                        onFailed = { }
                    )

                    TextButton(
                        onClick = {
                            val intent = Intent(context, CameraActivity::class.java)
                            cameraLauncher.launch(intent)
                        },
                        colors = ButtonDefaults.textButtonColors()
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CameraAlt,
                            contentDescription = stringResource(R.string.cd_add_manually)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(R.string.text_scan))
                    }
                }
            )
        }
    ) { innerPadding ->
        val context = LocalContext.current
        val detectGarbageResult by viewModel.detectGarbageResult.collectAsState(Result.Empty)
        val scope = rememberCoroutineScope()

        LaunchedEffect(detectGarbageResult) {
            detectGarbageResult.empty { detectionScaffoldState.isLoading = it }
            detectGarbageResult.success {
                if (firstPreview) {
                    detectionScaffoldState.showPreview = Result.Success(it)
                }
                detectionScaffoldState.isLoading = false
                firstPreview = false
            }
            detectGarbageResult.error {
                scope.launch {
                    val message = it.message ?: context.getString(R.string.text_unknown_error)
                    snackbarHostState.showSnackbar(message)
                    detectionScaffoldState.isLoading = false
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            detectGarbageResult.success { detectedGarbage ->
                val detectedGarbageItems = remember(detectedGarbage) { detectedGarbage.groupByLabel() }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = ContentPadding
                ) {
                    item {
                        var addItemMode by rememberSaveable { mutableStateOf(false) }

                        if (addItemMode) {
                            AddGarbage(onCancel = { addItemMode = false })
                        } else {
                            OutlinedButton(
                                onClick = { addItemMode = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Add item")
                            }
                        }
                    }

                    items(items = detectedGarbageItems) { detected ->
                        ScannedGarbage(
                            modifier = Modifier.animateItemPlacement(),
                            onClick = {
                                val route = Route.ThreeRs(detected.label, detected.image)
                                navController.navigate(route)
                            },
                            image = detected.image,
                            name = detected.label,
                            total = detected.total,
                            date = Date().toString()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun rememberCameraLauncher(
    onFailed: () -> Unit = { },
    onSuccess: (File) -> Unit = { }
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode != CameraActivity.RESULT_SUCCESS) {
                onFailed()
                return@rememberLauncherForActivityResult
            }

            val data = result.data
            val imageFile = data?.getSerializableExtra(CameraActivity.KEY_IMAGE_RESULT) as File
            onSuccess(imageFile)
        }
    )
}
