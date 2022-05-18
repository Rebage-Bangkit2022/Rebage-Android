package trashissue.rebage.presentation.navhost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) = CompositionLocalProvider(LocalNavController provides navController) {

    NavHost(
        navController = navController,
        startDestination = Route.SignIn()
    ) {
        Route.Onboarding.composable()
        Route.SignUp.composable()
        Route.SignIn.composable()
        Route.Home.composable()
        Route.Detection.composable()
        Route.Estimation.composable()
        Route.Profile.composable()
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("Nav Controller not provided")
}
