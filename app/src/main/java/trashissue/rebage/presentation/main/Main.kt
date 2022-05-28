package trashissue.rebage.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.SharedFlow
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
    isAlreadyOnboardingFlow: SharedFlow<Boolean>,
    isLoggedInFlow: SharedFlow<Boolean?>
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

        Box(modifier = Modifier.weight(1F)) {

            CompositionLocalProvider(LocalNavController provides navController) {
                val isAlreadyOnboarding by isAlreadyOnboardingFlow.collectAsState(null)
                val isLoggedIn by isLoggedInFlow.collectAsState(null)

                Timber.i("IS LOGGED IN $isLoggedIn")
                Timber.i("IS ALREADY ONBOARDING $isAlreadyOnboarding")

                if (isAlreadyOnboarding != null && isLoggedIn != null) {
                    NavGraph(
                        startDestination = when (isAlreadyOnboarding) {
                            false -> Route.Onboarding()
                            else -> when (isLoggedIn) {
                                true -> Route.Home()
                                else -> Route.SignUp()
                            }
                        },
                        navController = navController
                    )
                }
            }
        }
        NavigationBarMain(
            navController = navController,
            isBotNavVisible = isBotNavVisible,
            currentBackStack = { currentBackStack }
        )
    }
}

@Composable
fun NavigationBarMain(
    navController: NavHostController,
    isBotNavVisible: Boolean,
    currentBackStack: () -> NavBackStackEntry?
) {
    AnimatedVisibility(
        visible = isBotNavVisible,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        NavigationBar(
            modifier = Modifier.navigationBarsPadding(),
            tonalElevation = 4.dp,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            val currentDestination = currentBackStack()?.destination

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
                            popUpTo(Route.Home()) {
                                saveState = true
                            }
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
    }
}
