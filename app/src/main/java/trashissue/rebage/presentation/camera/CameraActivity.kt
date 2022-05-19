package trashissue.rebage.presentation.camera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import trashissue.rebage.presentation.theme.RebageTheme

@AndroidEntryPoint
class CameraActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            RebageTheme {
                CameraScreen()
            }
        }
    }

    companion object {
        const val KEY_IMAGE_RESULT = "image_result"
        const val RESULT_SUCCESS = 1
    }
}
