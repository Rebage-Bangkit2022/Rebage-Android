package trashissue.rebage.presentation.camera

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import trashissue.rebage.presentation.camera.component.CameraCapture


@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun CameraActivity.CameraScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        val permission = rememberPermissionState(android.Manifest.permission.CAMERA) { isGranted ->
            if (!isGranted) {
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
                onCaptured = { file ->
                    val intent = Intent().apply {
                        putExtra(CameraActivity.KEY_IMAGE_RESULT, file)
                    }

                    setResult(CameraActivity.RESULT_SUCCESS, intent)
                    finish()
                }
            )
        }
    }
}

fun CameraActivity.openPermissionSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}
