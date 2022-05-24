package trashissue.rebage.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        Route.Onboarding.composable()
        Route.SignUp.composable()
        Route.SignIn.composable()
        Route.Home.composable()
        Route.Detection.composable()
        Route.Price.composable()
        Route.Profile.composable()
        Route.FavoriteArticle.composable()
        Route.ThreeRs.composable()
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("Nav Controller not provided")
}
