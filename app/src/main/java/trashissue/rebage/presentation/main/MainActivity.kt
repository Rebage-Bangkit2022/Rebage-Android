package trashissue.rebage.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import trashissue.rebage.presentation.theme3.RebageTheme3

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            RebageTheme3(dynamicColor = false) {
                Main(
                    isAlreadyOnboardingFlow = viewModel.isAlreadyOnboarding,
                    isLoggedInFlow = viewModel.isLoggedIn
                )
            }
        }
    }
}
