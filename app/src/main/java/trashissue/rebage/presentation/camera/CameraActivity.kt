package trashissue.rebage.presentation.camera

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import dagger.hilt.android.AndroidEntryPoint
import trashissue.rebage.presentation.common.BitmapUtils
import trashissue.rebage.presentation.theme3.RebageTheme3

@AndroidEntryPoint
class CameraActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemBars()
        super.onCreate(savedInstanceState)

        setContent {
            RebageTheme3(dynamicColor = false) {
                CameraScreen(
                    onImageTakenFromGallery = { file ->
                        val intent = Intent().apply {
                            BitmapUtils.reduceSize(file = file, rotate = false)
                            putExtra(KEY_IMAGE_RESULT, file)
                        }
                        setResult(RESULT_SUCCESS, intent)
                        finish()
                    },
                    onImageTakenFromCamera = { file, isBackCamera ->
                        val intent = Intent().apply {
                            BitmapUtils.reduceSize(file, isBackCamera)
                            putExtra(KEY_IMAGE_RESULT, file)
                        }
                        setResult(RESULT_SUCCESS, intent)
                        finish()
                    }
                )
            }
        }
    }

    private fun hideSystemBars() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val insetsControllerCompat = WindowInsetsControllerCompat(window, window.decorView)
        insetsControllerCompat.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        insetsControllerCompat.hide(WindowInsetsCompat.Type.systemBars())
    }

    companion object {
        const val KEY_IMAGE_RESULT = "image_result"
        const val RESULT_SUCCESS = 1
    }
}
