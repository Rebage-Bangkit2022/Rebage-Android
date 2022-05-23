package trashissue.rebage.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import timber.log.Timber
import trashissue.rebage.R

val BotNavMenus = listOf(
    Triple(R.string.text_home, R.drawable.ic_home, Route.Home()),
    Triple(R.string.text_detection, R.drawable.ic_detection, Route.Detection()),
    Triple(R.string.text_price, R.drawable.ic_price, Route.Price()),
    Triple(R.string.text_profile, R.drawable.ic_profile, Route.Profile()),
)

@Composable
fun Main() {
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
                val onboarding = false

                NavGraph(
                    startDestination = if (onboarding) Route.Onboarding() else Route.Home(),
                    navController = navController
                )
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
                val (title, icon, route) = menu

                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == route } == true,
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
                            painter = painterResource(icon),
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
