package trashissue.rebage.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import trashissue.rebage.R
import trashissue.rebage.presentation.navhost.NavGraph
import trashissue.rebage.presentation.navhost.Route
import trashissue.rebage.presentation.theme.RebageTheme

val BotNavMenu = listOf(
    Triple(Route.Home(), R.string.text_home, R.drawable.ic_home),
    Triple(Route.Detection(), R.string.text_detection, R.drawable.ic_detection),
    Triple(Route.Price(), R.string.text_price, R.drawable.ic_price),
    Triple(Route.Profile(), R.string.text_profile, R.drawable.ic_profile)
)

@Composable
fun Main(
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            NavGraph()
        }
        MainBottomNavigation(
            onClickItem = { route ->
                navController.navigate(route) {
                    popUpTo(Route.Home()) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

@Composable
fun MainBottomNavigation(
    modifier: Modifier = Modifier,
    onClickItem: (String) -> Unit
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        for (menu in BotNavMenu) {
            val (route, title, icon) = menu

            BottomNavigationItem(
                selected = route == Route.Home(),
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onBackground.copy(alpha = 0.3F),
                onClick = { onClickItem(route) },
                icon = {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = stringResource(title)
                    )
                },
                label = {
                    Text(text = stringResource(title))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    RebageTheme {
        Main(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainBotNavPreview() {
    RebageTheme {
        MainBottomNavigation(onClickItem = {})
    }
}