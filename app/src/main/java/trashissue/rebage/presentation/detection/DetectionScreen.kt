package trashissue.rebage.presentation.detection

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import trashissue.rebage.R
import trashissue.rebage.domain.model.*
import trashissue.rebage.presentation.camera.CameraActivity
import trashissue.rebage.presentation.detection.component.Loading
import java.io.File
import java.net.URL

private val ContentPadding = PaddingValues(16.dp)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetectionScreen(
    navController: NavHostController,
    viewModel: DetectionViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
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
        val detectGarbageResult by viewModel.detectGarbageResult.collectAsState()
        val detectedGarbageWithBoundingBox by detectGarbageResult.drawBoundingBox()

        Timber.i("HASIL detectGarbageResult $detectGarbageResult")

        Timber.i("HASIL detectedGarbageWithBoundingBox $detectedGarbageWithBoundingBox")

        if (detectGarbageResult.isLoading || detectedGarbageWithBoundingBox.isLoading) {
            Loading(modifier = Modifier.fillMaxSize())
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                detectedGarbageWithBoundingBox.onSuccess { imageBitmap ->
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = null,
                        modifier = Modifier
                            .systemBarsPadding()
                            .fillMaxWidth()
                    )
                }

                detectedGarbageWithBoundingBox.onError { error ->
                    Text(text = "$error", modifier = Modifier.systemBarsPadding())
                }

//                LazyColumn(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalArrangement = Arrangement.spacedBy(12.dp),
//                    contentPadding = ContentPadding
//                ) {
//                    item {
//                        var addItemMode by rememberSaveable { mutableStateOf(false) }
//
//                        if (addItemMode) {
//                            AddGarbage(onCancel = { addItemMode = false })
//                        } else {
//                            OutlinedButton(
//                                onClick = { addItemMode = true },
//                                modifier = Modifier.fillMaxWidth()
//                            ) {
//                                Text(text = "Add item")
//                            }
//                        }
//                    }
//                    items(30, key = { it }) {
//                        ScannedGarbage(
//                            modifier = Modifier.animateItemPlacement(),
//                            onClick = { navController.navigate(Route.ThreeRs()) }
//                        )
//                    }
//                }
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

@Composable
fun Result<DetectedGarbage>.drawBoundingBox(): State<Result<ImageBitmap>> {
    val result = remember { mutableStateOf<Result<ImageBitmap>>(Result.NoData()) }

    LaunchedEffect(this) {
        when (val detectedGarbage = this@drawBoundingBox) {
            is Result.NoData -> result.value = Result.NoData(detectedGarbage.loading)
            is Result.Success -> {
                try {
                    val imageBitmap = detectedGarbage.data.draw()
                    result.value = Result.Success(imageBitmap)
                } catch (e: Exception) {
                    result.value = Result.Error(e)
                }
            }
            is Result.Error -> result.value = Result.Error(detectedGarbage.throwable)
        }
    }

    return result
}

suspend fun DetectedGarbage.draw(): ImageBitmap {
    val resultBitmap = decodeStringURLasBitmap(image)
    if (resultBitmap.isFailure) throw  resultBitmap.exceptionOrNull()
        ?: RuntimeException("Failed to load object")
    val bitmap = resultBitmap.getOrNull()
        ?: throw RuntimeException("Failed to load object")
    val imageBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true).asImageBitmap()

    val garbageList = this.detected

    val paint = Paint().apply {
        color = Color.Red
        style = PaintingStyle.Stroke.apply {
            strokeWidth = 10F
        }
    }

    Canvas(imageBitmap).apply {
        val w = imageBitmap.width
        val h = imageBitmap.height

        garbageList.forEach { detected ->
            val (y1, x1, y2, x2) = detected.boundingBox
            val rect = Rect(left = x1 * w, top = y1 * h, right = x2 * w, bottom = y2 * h)
            drawRect(rect, paint)
        }
    }

    return imageBitmap
}

suspend fun decodeStringURLasBitmap(url: String): kotlin.Result<Bitmap> {
    return runCatching {
        withContext(Dispatchers.IO) {
            val stream = URL(url).openConnection().getInputStream()
            BitmapFactory.decodeStream(stream)
        }
    }
}
