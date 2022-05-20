package trashissue.rebage.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
            .background(MaterialTheme.colors.background)
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

        SetupSystemBar({ currentBackStack })
        Box(modifier = Modifier.weight(1F)) {
            val onboarding = true

            NavGraph(
                startDestination = if (onboarding) Route.Onboarding() else Route.Home(),
                navController = navController
            )
        }

        BottomNavigationMain(
            navController = navController,
            isBotNavVisible = isBotNavVisible,
            currentBackStack = { currentBackStack }
        )
    }
}

private val LightStatusBar = listOf(
    Route.Home(),
    Route.Detection(),
    Route.Price(),
    Route.Profile()
)

@Composable
fun SetupSystemBar(
    currentBackStack: () -> NavBackStackEntry?,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val systemUiController = rememberSystemUiController()
    val lightIcons by remember {
        derivedStateOf {
            val current = currentBackStack()?.destination
            current?.hierarchy?.any { it.route in LightStatusBar } == true
        }
    }

    LaunchedEffect(lightIcons, darkTheme) {
        if (lightIcons && !darkTheme) {
            systemUiController.setStatusBarColor(Color.Transparent, false)
        }

        if (darkTheme || !lightIcons) {
            systemUiController.setStatusBarColor(Color.Transparent, true)
        }
    }
}

@Composable
fun BottomNavigationMain(
    navController: NavHostController,
    isBotNavVisible: Boolean,
    currentBackStack: () -> NavBackStackEntry?
) {
    AnimatedVisibility(
        visible = isBotNavVisible,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier
                .border(width = 1.dp, color = MaterialTheme.colors.onSurface.copy(0.05F))
                .navigationBarsPadding(),
            elevation = 0.dp
        ) {
            val currentDestination = currentBackStack()?.destination

            for (menu in BotNavMenus) {
                val (title, icon, route) = menu

                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any { it.route == route } == true,
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.onSurface.copy(0.4F),
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
