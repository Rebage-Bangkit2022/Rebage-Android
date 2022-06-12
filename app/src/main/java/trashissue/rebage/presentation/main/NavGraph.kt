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
        Route.ThreeRs.composable()
        Route.ListArticle.composable()
        Route.ThreeRs.composable()
        Route.Article.composable()
        Route.GarbageBank.composable()
        Route.Maps.composable()
        Route.ChartDetail.composable()
        Route.EditProfile.composable()
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("Nav Controller not provided")
}
