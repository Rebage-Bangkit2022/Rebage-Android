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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import trashissue.rebage.domain.model.Detection
import trashissue.rebage.presentation.camera.CameraActivity
import trashissue.rebage.presentation.detection.component.AddGarbage
import trashissue.rebage.presentation.detection.component.PreviewDetectionDialog
import trashissue.rebage.presentation.detection.component.ScannedGarbage
import trashissue.rebage.presentation.detection.component.TopAppBar
import trashissue.rebage.presentation.main.Route
import java.io.File

private val ContentPadding = PaddingValues(16.dp)

@Composable
fun DetectionScreen(
    navController: NavHostController,
    viewModel: DetectionViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    DetectionScreen(
        snackbarHostState = snackbarHostState,
        detectionsState = viewModel.detections,
        previewState = viewModel.preview,
        loadingState = viewModel.loading,
        onDetect = viewModel::detect,
        onUpdateDetection = viewModel::update,
        onDeleteDetection = viewModel::delete,
        onPreview = viewModel::showPreview,
        onClosePreview = viewModel::deletePreview,
        onNavigateToThreeRs = { id ->
            navController.navigate(Route.ThreeRs(id))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetectionScreen(
    snackbarHostState: SnackbarHostState,
    detectionsState: StateFlow<List<Detection>>,
    previewState: StateFlow<List<Detection>>,
    loadingState: StateFlow<Boolean>,
    onDetect: (File) -> Unit,
    onUpdateDetection: (Int, Int) -> Unit,
    onDeleteDetection: (Int) -> Unit,
    onPreview: (List<Detection>) -> Unit,
    onClosePreview: () -> Unit,
    onNavigateToThreeRs: (Int) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val cameraLauncher = rememberCameraLauncher(
        onSuccess = onDetect,
        onFailed = {
            scope.launch { snackbarHostState.showSnackbar("Failed to take picture") }
        }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            val isLoading by loadingState.collectAsState()

            TopAppBar(
                enabledCameraScan = !isLoading,
                onClickCameraScan = {
                    val intent = Intent(context, CameraActivity::class.java)
                    cameraLauncher.launch(intent)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val detections by detectionsState.collectAsState()

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = ContentPadding
            ) {
                item {
                    var isAddItemMode by rememberSaveable { mutableStateOf(false) }

                    if (isAddItemMode) {
                        AddGarbage(onCancel = { isAddItemMode = false })
                    } else {
                        OutlinedButton(
                            onClick = { isAddItemMode = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Add item")
                        }
                    }
                }
                items(items = detections, key = { it.id }) { detection ->
                    ScannedGarbage(
                        modifier = Modifier.animateItemPlacement(),
                        onClickCard = { onNavigateToThreeRs(detection.id) },
                        onClickButtonSave = { onUpdateDetection(detection.id, it) },
                        onClickButtonDelete = { onDeleteDetection(detection.id) },
                        onClickImage = { onPreview(listOf(detection)) },
                        image = detection.image,
                        name = detection.label,
                        total = detection.total,
                        date = detection.createdAt
                    )
                }
            }

            val preview by previewState.collectAsState()
            val isLoading by loadingState.collectAsState()

            if (preview.isNotEmpty()) {
                PreviewDetectionDialog(
                    onClosePreview = onClosePreview,
                    detections = preview
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun rememberCameraLauncher(
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
