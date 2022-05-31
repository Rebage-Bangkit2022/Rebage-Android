package trashissue.rebage.presentation.camera.component

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CameraState(
    selector: CameraSelector,
    val preview: Preview,
    val imageCapture: ImageCapture,
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context
) {
    var selector by mutableStateOf(selector)
    private val Context.executor: Executor
        get() = ContextCompat.getMainExecutor(this)


    suspend fun unbind() {
        val cameraProvider = getCameraProvider()
        cameraProvider.unbindAll()
    }

    suspend fun bind() {
        val cameraProvider = getCameraProvider()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            selector,
            preview,
            imageCapture
        )
    }

    suspend fun takePicture(): File {
        return imageCapture.takePicture(context.executor)
    }

    private suspend fun ImageCapture.takePicture(executor: Executor): File {
        val photoFile = runCatching {
            File.createTempFile("image", ".jpg").apply {
                Timber.i("")
            }
        }.getOrElse { e ->
            Timber.e(e, "Failed to create temporary file")
            File("/dev/null")
        }

        return suspendCoroutine { continuation ->
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Timber.i("Image capture success")
                    continuation.resume(photoFile)
                }

                override fun onError(ex: ImageCaptureException) {
                    Timber.e(ex, "Image capture failed")
                    continuation.resumeWithException(ex)
                }
            })
        }
    }

    private suspend fun getCameraProvider(): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(context).also { future ->
                future.addListener({
                    continuation.resume(future.get())
                }, context.executor)
            }
        }
}

@Composable
fun rememberCameraState(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
) = remember {

    val preview = Preview
        .Builder()
        .build()
    val imageCapture = ImageCapture
        .Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
        .build()

    CameraState(
        selector = CameraSelector.DEFAULT_BACK_CAMERA,
        lifecycleOwner = lifecycleOwner,
        context = context,
        preview = preview,
        imageCapture = imageCapture
    )
}
