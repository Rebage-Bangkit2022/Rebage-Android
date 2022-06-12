package trashissue.rebage.presentation.main

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import trashissue.rebage.R
import trashissue.rebage.presentation.main.component.NavigationBarMain

val BotNavMenus = listOf(
    Triple(R.string.text_home, Icons.Outlined.Home to Icons.Filled.Home, Route.Home()),
    Triple(R.string.text_detection, Icons.Outlined.DocumentScanner to Icons.Filled.DocumentScanner, Route.Detection()),
    Triple(R.string.text_price, Icons.Outlined.Paid to Icons.Filled.Paid, Route.Price()),
    Triple(R.string.text_profile, Icons.Outlined.AccountCircle to Icons.Filled.AccountCircle, Route.Profile())
)

@Composable
fun MainScreen(
    navController: NavHostController,
    onboardingState: StateFlow<Boolean?>,
    loggedInState: StateFlow<Boolean?>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val currentBackStack by navController.currentBackStackEntryAsState()
        val isBotNavVisible by remember {
            val screenWithBotNav = BotNavMenus.map { it.third }

            derivedStateOf {
                when (currentBackStack?.destination?.route) {
                    in screenWithBotNav -> true
                    else -> false
                }
            }
        }

        Column(modifier = Modifier.weight(1F)) {
            CompositionLocalProvider(LocalNavController provides navController) {
                val isAlreadyOnboarding by onboardingState.collectAsState()
                val isLoggedIn by loggedInState.collectAsState()

                SideEffect {
                    Timber.d("IS LOGGED IN $isLoggedIn")
                    Timber.d("IS ALREADY ONBOARDING $isAlreadyOnboarding")
                }

                AnimatedVisibility(
                    visible = isAlreadyOnboarding != null && isLoggedIn != null,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    NavGraph(
                        startDestination = when (isAlreadyOnboarding) {
                            false -> Route.Onboarding()
                            else -> when (isLoggedIn) {
                                true -> Route.Home()
                                else -> Route.SignIn()
                            }
                        },
                        navController = navController
                    )
                }
            }
        }
        NavigationBarMain(
            onNavigation = { route ->
                navController.navigate(route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(Route.Home()) { saveState = true }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
            botNavVisibilityProvider = { isBotNavVisible },
            currentRouteProvider = { route ->
                currentBackStack?.destination?.hierarchy?.any { it.route == route } == true
            }
        )
    }
}
