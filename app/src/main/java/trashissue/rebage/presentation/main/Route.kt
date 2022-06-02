package trashissue.rebage.presentation.main

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import trashissue.rebage.presentation.article.ArticleScreen
import trashissue.rebage.presentation.detection.DetectionScreen
import trashissue.rebage.presentation.favoritearticle.FavoriteArticleScreen
import trashissue.rebage.presentation.home.HomeScreen
import trashissue.rebage.presentation.onboarding.OnboardingScreen
import trashissue.rebage.presentation.price.PriceScreen
import trashissue.rebage.presentation.profile.ProfileScreen
import trashissue.rebage.presentation.signin.SignInScreen
import trashissue.rebage.presentation.signup.SignUpScreen
import trashissue.rebage.presentation.threers.ThreeRsScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
            SignUpScreen(LocalNavController.current)
        }

        operator fun invoke() = route
    }

    object Home : Route("home") {

        context(NavGraphBuilder)
        fun composable() = composable(route) {
            HomeScreen(LocalNavController.current)
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

    object ThreeRs : Route("three_r_s/{$KEY_NAME}/{$KEY_IMAGE}") {

        context (NavGraphBuilder)
        fun composable() = composable(route) {
            val name = it.arguments?.getString(KEY_NAME)
            val image = it.arguments?.getString(KEY_IMAGE)
            if (name != null && image != null) {
                val decodedUrl = remember {
                    URLDecoder.decode(image, StandardCharsets.UTF_8.toString())
                }
                ThreeRsScreen(LocalNavController.current, name = name, image = decodedUrl)
            }
        }

        operator fun invoke(name: String, image: String): String {
            val encodedUrl = URLEncoder.encode(image, StandardCharsets.UTF_8.toString())
            return "three_r_s/$name/$encodedUrl"
        }
    }

    object Article : Route("article/{$KEY_ARTICLE_ID}") {
        private val arguments = listOf(
            navArgument(KEY_ARTICLE_ID) {
                type = NavType.IntType
            }
        )

        context (NavGraphBuilder)
        fun composable() = composable(route, arguments) {
            it.arguments?.getInt(KEY_ARTICLE_ID)?.let { articleId ->
                ArticleScreen(LocalNavController.current, articleId)
            }
        }

        operator fun invoke(articleId: Int) = "article/$articleId"
    }

    companion object {
        protected const val KEY_ARTICLE_ID = "articleId"
        protected const val KEY_NAME = "name"
        protected const val KEY_IMAGE = "image"
    }
}
