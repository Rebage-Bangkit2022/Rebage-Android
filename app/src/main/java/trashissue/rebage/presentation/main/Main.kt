package trashissue.rebage.presentation.main

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import trashissue.rebage.R

val BotNavMenus = listOf(
    Triple(R.string.text_home, Icons.Outlined.Home to Icons.Filled.Home, Route.Home()),
    Triple(R.string.text_detection, Icons.Outlined.DocumentScanner to Icons.Filled.DocumentScanner, Route.Detection()),
    Triple(R.string.text_price, Icons.Outlined.Paid to Icons.Filled.Paid, Route.Price()),
    Triple(R.string.text_profile, Icons.Outlined.AccountCircle to Icons.Filled.AccountCircle, Route.Profile())
)

@Composable
fun Main(
    isAlreadyOnboardingFlow: StateFlow<Boolean?>,
    isLoggedInFlow: StateFlow<Boolean?>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryFlow.collectAsState(navController.currentBackStackEntry)
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
                val isAlreadyOnboarding by isAlreadyOnboardingFlow.collectAsState()
                val isLoggedIn by isLoggedInFlow.collectAsState()

                SideEffect {
                    Timber.i("IS LOGGED IN $isLoggedIn")
                    Timber.i("IS ALREADY ONBOARDING $isAlreadyOnboarding")
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
            navController = navController,
            botNavVisible = { isBotNavVisible },
            currentBackStackProvider = { currentBackStack }
        )
    }
}

@Composable
fun NavigationBarMain(
    navController: NavHostController,
    botNavVisible: () -> Boolean,
    currentBackStackProvider: () -> NavBackStackEntry?
) {
    AnimatedVisibility(
        visible = botNavVisible(),
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Column {
            NavigationBar(
                tonalElevation = 4.dp,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                val currentDestination = currentBackStackProvider()?.destination

                for (menu in BotNavMenus) {
                    val (title, icons, route) = menu
                    val (outlined, filled) = icons

                    val selected = currentDestination?.hierarchy?.any { it.route == route } == true

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
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
                        icon = {
                            Icon(
                                imageVector = if (selected) filled else outlined,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(text = stringResource(title))
                        }
                    )
                }
            }
            Surface(tonalElevation = 4.dp) {
                val density = LocalDensity.current
                val navigationBarHeight = with(density) {
                    WindowInsets.navigationBars.getBottom(density).toDp()
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(navigationBarHeight)
                )
            }
        }
    }
}
