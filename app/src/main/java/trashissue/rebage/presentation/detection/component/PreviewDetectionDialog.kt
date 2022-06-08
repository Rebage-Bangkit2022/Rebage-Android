package trashissue.rebage.presentation.detection.component

import android.graphics.Bitmap
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import trashissue.rebage.domain.model.Detection
import kotlin.math.roundToInt

private val DefaultBoundingBoxesColor = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)

@Composable
fun PreviewDetectionDialog(
    onClosePreview: () -> Unit,
    detections: List<Detection>
) {
    Dialog(onDismissRequest = onClosePreview) {
        Box(modifier = Modifier.fillMaxSize()) {
            val context = LocalContext.current
            val density = LocalDensity.current
            val scale = remember { mutableStateOf(1F) }
            val rotationState = remember { mutableStateOf(0F) }

            detections.firstOrNull()?.let { detection ->
                AsyncImage(
                    modifier = Modifier
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
                    model = ImageRequest.Builder(context)
                        .data(detection.image)
                        .transformations(
                            BoundingBoxTransformation(
                                detections = detections,
                                strokeWidth = with(density) { 2.dp.toPx() },
                                cacheKey = "preview-${detection.id}-${detections.size}"
                            )
                        )
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

class BoundingBoxTransformation(
    private val detections: List<Detection>,
    private val colors: List<Color> = DefaultBoundingBoxesColor,
    private val strokeWidth: Float,
    private val style: PaintingStyle = PaintingStyle.Stroke,
    override val cacheKey: String
) : Transformation {

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val boundingBoxesAllLabels = detections.groupBy { it.label }
        val aspectRatio: Float = input.width / input.height.toFloat()
        val width = 720
        val height = (width / aspectRatio).roundToInt()
        val output = Bitmap.createScaledBitmap(input, width, height, false).asImageBitmap()

        val paint = Paint()
        paint.strokeWidth = strokeWidth
        paint.style = style

        Canvas(output).apply {
            val w = output.width
            val h = output.height

            for ((_, detectedGarbageByLabels) in boundingBoxesAllLabels) {
                val color = colors.random()
                paint.color = color

                detectedGarbageByLabels.forEach { detectedGarbage ->
                    detectedGarbage.boundingBoxes.forEach { boundingBox ->
                        val (y1, x1, y2, x2) = boundingBox
                        val rect = Rect(
                            left = x1 * w,
                            top = y1 * h,
                            right = x2 * w,
                            bottom = y2 * h
                        )
                        drawRect(rect, paint)
                    }
                }
            }
        }

        return output.asAndroidBitmap()
    }

    override fun equals(other: Any?): Boolean {
        return other is BoundingBoxTransformation && this.detections == detections
    }

    override fun hashCode() = javaClass.hashCode()
}


