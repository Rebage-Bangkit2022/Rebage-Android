package trashissue.rebage.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import trashissue.rebage.presentation.common.component.isLight
import trashissue.rebage.presentation.theme3.RebageTheme3

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isDarkTheme by viewModel.darkTheme.collectAsState()
            val navController = rememberNavController()

            RebageTheme3(
                dynamicColor = false,
                darkTheme = isDarkTheme ?: isSystemInDarkTheme()
            ) {
                val isLight = MaterialTheme.colorScheme.isLight
                val systemUiController = rememberSystemUiController()

                Timber.i("ONBO HALO")

                LaunchedEffect(isLight) {
                    navController.currentBackStackEntryFlow.collectLatest { backStack ->
                        if (backStack.destination.route == Route.Home()) {
                            systemUiController.setStatusBarColor(
                                color = Color.Transparent,
                                darkIcons = false
                            )
                        } else {
                            systemUiController.setStatusBarColor(
                                color = Color.Transparent,
                                darkIcons = isLight
                            )
                        }
                    }
                }

                MainScreen(
                    onboardingState = viewModel.onboarding,
                    loggedInState = viewModel.loggedIn,
                    navController = navController
                )
            }
        }
    }
}
