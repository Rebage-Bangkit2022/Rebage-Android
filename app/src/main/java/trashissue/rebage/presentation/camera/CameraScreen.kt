package trashissue.rebage.presentation.camera

import android.Manifest.permission
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import trashissue.rebage.R
import trashissue.rebage.presentation.camera.component.CameraCapture
import trashissue.rebage.presentation.camera.component.rememberCameraState
import trashissue.rebage.presentation.common.toast
import trashissue.rebage.presentation.theme3.LightPrimary
import trashissue.rebage.presentation.theme3.LightSurface
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun CameraScreen(
    onImageTakenFromCamera: (File, Boolean) -> Unit,
    onImageTakenFromGallery: (File) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        val cameraState = rememberCameraState()
        val scope = rememberCoroutineScope()
        var isLoading by remember { mutableStateOf(false) }
        val permission = rememberPermissionState(permission.CAMERA) { isGranted ->
            if (!isGranted) {
                context.toast(R.string.camera_permission, Toast.LENGTH_LONG)
                context.launchPermissionSettings()
            }
        }
        val galleryLauncher = rememberGalleryLauncher(
            onSuccess = onImageTakenFromGallery,
            onError = { error ->
                Timber.e(error)
                context.toast(R.string.text_unknown_error)
            }
        )

        LaunchedEffect(Unit) {
            if (!permission.status.isGranted) {
                permission.launchPermissionRequest()
            }
        }

        if (permission.status.isGranted) {
            CameraCapture(
                modifier = Modifier.fillMaxSize(),
                state = cameraState,
                onClickGallery = {
                    val intent = Intent().apply {
                        action = Intent.ACTION_GET_CONTENT
                        type = "image/*"
                    }

                    val chooser = Intent.createChooser(intent, "Choose image")
                    galleryLauncher.launch(chooser)
                },
                onClickCapture = {
                    scope.launch(Dispatchers.IO) {
                        isLoading = true
                        val capturedImage = cameraState.takePicture()
                        val backCamera = cameraState.selector == CameraSelector.DEFAULT_BACK_CAMERA
                        onImageTakenFromCamera(capturedImage, backCamera)
                        isLoading = false
                    }
                },
                onClickSwitchCamera = {
                    cameraState.selector = when (cameraState.selector) {
                        CameraSelector.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
                        else -> CameraSelector.DEFAULT_BACK_CAMERA
                    }
                }
            )
        }

        var isHelperDialogOpen by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.weight(1F))
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(LightSurface),
                onClick = {
                    isHelperDialogOpen = true
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.QuestionMark,
                    contentDescription = "Help",
                    tint = LightPrimary
                )
            }
        }

        if (isHelperDialogOpen) {
            AlertDialog(
                onDismissRequest = {
                    isHelperDialogOpen = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            isHelperDialogOpen = false
                        }
                    ) {
                        Text(text = "Ok")
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Psychology,
                        contentDescription = null
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.text_available_objects),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

private fun Context.launchPermissionSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}

@Composable
private fun rememberGalleryLauncher(
    onSuccess: (File) -> Unit,
    onError: (Exception?) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        try {
            if (result.resultCode != Activity.RESULT_OK) {
                return@rememberLauncherForActivityResult
            }
            val selectedImg: Uri = result.data?.data as Uri
            val file = selectedImg.uriToFile(context)
            onSuccess(file)
        } catch (e: Exception) {
            onError(e)
        }
    }
}

private fun Uri.uriToFile(context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = context.createTempFile()

    val inputStream = contentResolver.openInputStream(this) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

private fun Context.createTempFile(): File {
    val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("${System.currentTimeMillis()}", ".jpg", storageDir)
}
