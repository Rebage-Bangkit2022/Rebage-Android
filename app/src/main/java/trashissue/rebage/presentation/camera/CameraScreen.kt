package trashissue.rebage.presentation.camera

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun CameraActivity.CameraScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

        LaunchedEffect(cameraPermissionState.status) {
            if (!cameraPermissionState.status.isGranted) {
                cameraPermissionState.launchPermissionRequest()
            }
        }

        if(cameraPermissionState.status.isGranted) {
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

        if (!cameraPermissionState.status.isGranted) {
            Text(text = "Require permission")
        }
    }
}
