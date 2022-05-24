package trashissue.rebage.presentation.camera

import android.Manifest.permission
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import trashissue.rebage.R
import trashissue.rebage.presentation.camera.component.CameraCapture
import trashissue.rebage.presentation.camera.component.rememberCameraState
import trashissue.rebage.presentation.common.component.toast

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun CameraActivity.CameraScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        val camera = rememberCameraState()
        val scope = rememberCoroutineScope()
        var loading by remember { mutableStateOf(false) }
        val permission = rememberPermissionState(permission.CAMERA) { isGranted ->
            if (!isGranted) {
                toast(R.string.camera_permission, Toast.LENGTH_LONG)
                openPermissionSettings()
                finish()
            }
        }

        LaunchedEffect(Unit) {
            if (!permission.status.isGranted) {
                permission.launchPermissionRequest()
            }
        }

        if (permission.status.isGranted) {
            CameraCapture(
                modifier = Modifier.fillMaxSize(),
                state = camera,
                onClickGallery = {},
                onClickCapture = {
                    scope.launch {
                        loading = true
                        val capturedImage = camera.takePicture()
                        val intent = Intent().apply {
                            putExtra(CameraActivity.KEY_IMAGE_RESULT, capturedImage)
                        }

                        setResult(CameraActivity.RESULT_SUCCESS, intent)
                        loading = false
                        finish()
                    }
                },
                onClickSwitchCamera = {
                    camera.selector = when (camera.selector) {
                        CameraSelector.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
                        else -> CameraSelector.DEFAULT_BACK_CAMERA
                    }
                }
            )
        }

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

private fun CameraActivity.openPermissionSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}
