package trashissue.rebage.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import trashissue.rebage.R
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.model.DetectionStatistic
import trashissue.rebage.presentation.home.component.Articles
import trashissue.rebage.presentation.home.component.Header
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    HomeScreen(
        snackbarHostState = snackbarHostState,
        usernameState = viewModel.username,
        allArticlesState = viewModel.allArticles,
        articlesReduce = viewModel.articlesReduce,
        articlesReuse = viewModel.articleReuse,
        statsState = viewModel.stats,
        onNavigateToDetailArticle = { id ->
            navController.navigate(Route.Article(id))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    snackbarHostState: SnackbarHostState,
    usernameState: StateFlow<String?>,
    allArticlesState: StateFlow<List<Article>>,
    articlesReduce: StateFlow<List<Article>>,
    articlesReuse: StateFlow<List<Article>>,
    statsState: StateFlow<List<DetectionStatistic>>,
    onNavigateToDetailArticle: (Int) -> Unit,
) {

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {

            val isLight = MaterialTheme.colorScheme.isLight()
            val systemUiController = rememberSystemUiController()

            DisposableEffect(isLight) {
                systemUiController.setStatusBarColor(Color.Transparent, darkIcons = false)
                onDispose {
                    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = isLight)
                }
            }

            val username by usernameState.collectAsState()
            val stats by statsState.collectAsState()
            val allArticles by allArticlesState.collectAsState()
            val reuse by articlesReuse.collectAsState()
            val reduce by articlesReduce.collectAsState()

            Header(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                name = username,
                stats = stats
            )
            Articles(
                label = stringResource(R.string.text_all_articles),
                articles = allArticles,
                onClickArticle = onNavigateToDetailArticle
            )
            Articles(
                label = stringResource(R.string.text_handicraft_product),
                articles = reuse,
                onClickArticle = onNavigateToDetailArticle
            )
            Articles(
                label = stringResource(R.string.text_reduce),
                articles = reduce,
                onClickArticle = onNavigateToDetailArticle
            )
        }
    }
}

@Composable
fun ColorScheme.isLight() = this.background.luminance() > 0.5

@Preview
@Composable
fun HomeScreenPreview() {
    RebageTheme3 {
        HomeScreen(
            snackbarHostState = remember { SnackbarHostState() },
            usernameState = MutableStateFlow("Tubagus"),
            allArticlesState = MutableStateFlow(emptyList()),
            articlesReuse = MutableStateFlow(emptyList()),
            articlesReduce = MutableStateFlow(emptyList()),
            statsState = MutableStateFlow(emptyList()),
            onNavigateToDetailArticle = { }
        )
    }
}
