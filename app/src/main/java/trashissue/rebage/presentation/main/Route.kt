package trashissue.rebage.presentation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import trashissue.rebage.presentation.detection.DetectionScreen
import trashissue.rebage.presentation.favoritearticle.FavoriteArticleScreen
import trashissue.rebage.presentation.home.HomeScreen
import trashissue.rebage.presentation.onboarding.OnboardingScreen
import trashissue.rebage.presentation.price.PriceScreen
import trashissue.rebage.presentation.profile.ProfileScreen
import trashissue.rebage.presentation.signin.SignInScreen
import trashissue.rebage.presentation.signup.SignUpScreen
import trashissue.rebage.presentation.threers.ThreeRsScreen

sealed class Route(
    protected val route: String
) {
    object Onboarding : Route("onboarding") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            OnboardingScreen(LocalNavController.current)
        }

        operator fun invoke() = route
    }

    object SignIn : Route("sign_in") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            SignInScreen(LocalNavController.current)
        }

        operator fun invoke() = route
    }

    object SignUp : Route("sign_up") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            SignUpScreen()
        }

        operator fun invoke() = route
    }

    object Home : Route("home") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            HomeScreen()
        }

        operator fun invoke() = route
    }

    object Detection : Route("detection") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            DetectionScreen(LocalNavController.current)
        }

        operator fun invoke() = route
    }

    object Price : Route("price") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            PriceScreen()
        }

        operator fun invoke() = route
    }

    object Profile : Route("profile") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            ProfileScreen(LocalNavController.current)
        }

        operator fun invoke() = route
    }

    object FavoriteArticle : Route("favorite_article") {

        context (NavGraphBuilder)
        fun composable() = composable(route) {
            FavoriteArticleScreen(LocalNavController.current)
        }

        operator fun invoke() = route
    }

    object ThreeRs : Route("three_r_s}") {

        context (NavGraphBuilder)
        fun composable() = composable(route) {
            ThreeRsScreen(LocalNavController.current)
        }

        operator fun invoke() = route
    }
}
