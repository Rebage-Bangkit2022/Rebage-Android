package trashissue.rebage.presentation.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import trashissue.rebage.presentation.main.BotNavMenus

@Composable
fun NavigationBarMain(
    onNavigation: (String) -> Unit,
    botNavVisibilityProvider: () -> Boolean,
    currentRouteProvider: (String) -> Boolean
) {
    AnimatedVisibility(
        visible = botNavVisibilityProvider(),
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Column {
            NavigationBar(
                tonalElevation = 4.dp,
                containerColor = MaterialTheme.colorScheme.surface
            ) {

                for (menu in BotNavMenus) {
                    val (title, icons, route) = menu
                    val (outlined, filled) = icons

                    val selected = currentRouteProvider(route)

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            onNavigation(route)
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
