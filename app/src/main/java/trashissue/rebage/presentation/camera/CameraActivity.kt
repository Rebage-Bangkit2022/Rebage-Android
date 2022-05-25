package trashissue.rebage.presentation.camera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import trashissue.rebage.presentation.theme3.RebageTheme3

@AndroidEntryPoint
class CameraActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            RebageTheme3(dynamicColor = false) {
                CameraScreen()
            }
        }
    }

    companion object {
        const val KEY_IMAGE_RESULT = "image_result"
        const val RESULT_SUCCESS = 1
    }
}
