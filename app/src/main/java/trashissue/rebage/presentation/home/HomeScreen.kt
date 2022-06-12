package trashissue.rebage.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import trashissue.rebage.R
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.model.DetectionStatistic
import trashissue.rebage.presentation.home.component.Articles
import trashissue.rebage.presentation.home.component.Header
import trashissue.rebage.presentation.listarticle.ListArticleType
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.loadDetectionsStatistic()
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    HomeScreen(
        snackbarHostState = snackbarHostState,
        usernameState = viewModel.username,
        latestArticlesState = viewModel.latestArticles,
        articlesReduce = viewModel.articlesReduce,
        articlesReuse = viewModel.articleReuse,
        statsState = viewModel.stats,
        onClickShowMoreChart = {
            navController.navigate(Route.ChartDetail())
        },
        onClickShowMoreArticle = { type ->
            navController.navigate(Route.ListArticle(type))
        },
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
    latestArticlesState: StateFlow<List<Article>>,
    articlesReduce: StateFlow<List<Article>>,
    articlesReuse: StateFlow<List<Article>>,
    statsState: StateFlow<List<DetectionStatistic>>,
    onClickShowMoreChart: () -> Unit,
    onClickShowMoreArticle: (ListArticleType) -> Unit,
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

            val username by usernameState.collectAsState()
            val stats by statsState.collectAsState()
            val latestArticles by latestArticlesState.collectAsState()
            val reuse by articlesReuse.collectAsState()
            val reduce by articlesReduce.collectAsState()

            Header(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                name = username,
                stats = stats,
                onClickShowMore = onClickShowMoreChart
            )
            Articles(
                label = stringResource(R.string.text_latest_articles),
                articles = latestArticles,
                onClickShowMore = { onClickShowMoreArticle(ListArticleType.Latest) },
                onClickArticle = onNavigateToDetailArticle
            )
            Articles(
                label = stringResource(R.string.text_handicraft_product),
                articles = reuse,
                onClickShowMore = { onClickShowMoreArticle(ListArticleType.Reuse) },
                onClickArticle = onNavigateToDetailArticle
            )
            Articles(
                label = stringResource(R.string.text_reduce),
                articles = reduce,
                onClickShowMore = { onClickShowMoreArticle(ListArticleType.Reduce) },
                onClickArticle = onNavigateToDetailArticle
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    RebageTheme3 {
        HomeScreen(
            snackbarHostState = remember { SnackbarHostState() },
            usernameState = MutableStateFlow("Tubagus"),
            latestArticlesState = MutableStateFlow(emptyList()),
            articlesReuse = MutableStateFlow(emptyList()),
            articlesReduce = MutableStateFlow(emptyList()),
            statsState = MutableStateFlow(emptyList()),
            onNavigateToDetailArticle = { },
            onClickShowMoreChart = { },
            onClickShowMoreArticle = { }
        )
    }
}
