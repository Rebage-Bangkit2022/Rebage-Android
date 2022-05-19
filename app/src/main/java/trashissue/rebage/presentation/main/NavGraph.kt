package trashissue.rebage.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import timber.log.Timber

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String,
) = CompositionLocalProvider(LocalNavController provides navController) {
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
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("Nav Controller not provided")
}
