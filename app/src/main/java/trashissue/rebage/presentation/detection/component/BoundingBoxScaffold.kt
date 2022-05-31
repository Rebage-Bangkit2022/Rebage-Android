package trashissue.rebage.presentation.detection.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import trashissue.rebage.domain.model.DetectedGarbage
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.isLoading
import trashissue.rebage.domain.model.onSuccess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoundingBoxScaffold(
    modifier: Modifier = Modifier,
    state: BoundingBoxScaffoldState = rememberDetectionScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (PaddingValues) -> Unit
) {
    Box {
        Scaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            containerColor = containerColor,
            contentColor = contentColor,
            content = content
        )
        val detectedGarbageWithBoundingBox by state.showPreview.drawBoundingBox()

        if (state.isLoading || detectedGarbageWithBoundingBox.isLoading) {
            Dialog(onDismissRequest = { }) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }

        detectedGarbageWithBoundingBox.onSuccess { imageBitmap ->
            Dialog(
                onDismissRequest = {
                    state.showPreview = Result.Empty
                }
            ) {
                val scale = remember { mutableStateOf(1f) }
                val rotationState = remember { mutableStateOf(1f) }

                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .graphicsLayer(
                                scaleX = maxOf(.5f, minOf(3f, scale.value)),
                                scaleY = maxOf(.5f, minOf(3f, scale.value)),
                                rotationZ = rotationState.value
                            )
                            .pointerInput(Unit) {
                                detectTransformGestures { _, _, zoom, rotation ->
                                    scale.value *= zoom
                                    rotationState.value += rotation
                                }
                            },
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}

@Composable
private fun Result<DetectedGarbage>.drawBoundingBox(
    color: Color = Color.Red,
    strokeWidth: Dp = 4.dp
): State<Result<ImageBitmap>> {
    val result = remember { mutableStateOf<Result<ImageBitmap>>(Result.NoData()) }
    val context = LocalContext.current
    val density = LocalDensity.current

    LaunchedEffect(this) {
        when (val detectedGarbage = this@drawBoundingBox) {
            is Result.NoData -> result.value = Result.NoData(detectedGarbage.loading)
            is Result.Success -> {
                result.value = Result.NoData(loading = true)
                try {
                    val request = ImageRequest.Builder(context)
                        .allowConversionToBitmap(true)
                        .diskCacheKey(detectedGarbage.data.image)
                        .data(detectedGarbage.data.image)
                        .build()
                    val immutableBitmap = ImageLoader(context)
                        .execute(request)
                        .drawable
                        ?.toBitmap()
                        ?: throw RuntimeException("Failed to load image")
                    val mutableBitmap = immutableBitmap
                        .copy(Bitmap.Config.ARGB_8888, true)
                        .asImageBitmap()
                    val drawn = detectedGarbage.data.draw(
                        imageBitmap = mutableBitmap,
                        color = color,
                        strokeWidth = with(density) { strokeWidth.toPx() }
                    )
                    result.value = Result.Success(drawn)
                } catch (e: Exception) {
                    result.value = Result.Error(e)
                }
            }
            is Result.Error -> result.value = Result.Error(detectedGarbage.throwable)
        }
    }

    return result
}

private fun DetectedGarbage.draw(
    imageBitmap: ImageBitmap,
    color: Color,
    strokeWidth: Float,
    style: PaintingStyle = PaintingStyle.Stroke
): ImageBitmap {
    val garbageList = this.detected
    val paint = Paint().apply {
        this.color = color
        this.strokeWidth = strokeWidth
        this.style = style
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
