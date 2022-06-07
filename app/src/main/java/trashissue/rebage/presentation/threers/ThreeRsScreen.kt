package trashissue.rebage.presentation.threers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.model.Detection
import trashissue.rebage.domain.model.Garbage
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.threers.component.*

private val DefaultLazyColumnContentPadding = PaddingValues(16.dp)

@Composable
fun ThreeRsScreen(
    navController: NavHostController,
    viewModel: ThreeRsViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    ThreeRsScreen(
        snackbarHostState = snackbarHostState,
        loadingState = viewModel.loading,
        detectionState = viewModel.detection,
        articlesReduceState = viewModel.articlesReduce,
        articlesReuseState = viewModel.articleReuse,
        garbageState = viewModel.garbage,
        onUpdateDetection = viewModel::update,
        onNavigationBack = navController::popBackStack,
        onNavigateToDetailArticle = { id ->
            navController.navigate(Route.Article(id))
        },
        onNavigateToGarbageBank = {
            navController.navigate(Route.GarbageBank())
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun ThreeRsScreen(
    snackbarHostState: SnackbarHostState,
    loadingState: StateFlow<Boolean>,
    detectionState: StateFlow<Detection?>,
    articlesReduceState: StateFlow<List<Article>>,
    articlesReuseState: StateFlow<List<Article>>,
    garbageState: StateFlow<Garbage?>,
    onUpdateDetection: (Int, Int) -> Unit,
    onNavigationBack: () -> Unit,
    onNavigateToDetailArticle: (Int) -> Unit,
    onNavigateToGarbageBank: () -> Unit
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            val detection by detectionState.collectAsState()
            TopAppBar(
                title = detection?.label,
                onClickNavigation = onNavigationBack
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToGarbageBank,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface,
                text = {
                    Text(text = "Bank")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Map,
                        contentDescription = null
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            SwipeableContainer(modifier = Modifier.fillMaxSize()) {
                val detection by detectionState.collectAsState()

                AsyncImage(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraLarge)
                        .layoutId("image"),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(detection?.image)
                        .crossfade(500)
                        .build(),
                    contentDescription = detection?.label,
                    contentScale = ContentScale.Crop
                )

                val pagerState = rememberPagerState()
                val scope = rememberCoroutineScope()

                TabRow3R(
                    modifier = Modifier.layoutId("tab3r"),
                    selectedTabIndex = pagerState.currentPage,
                    onClickTab = { index ->
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )

                val articlesReduce by articlesReduceState.collectAsState()
                val articlesReuse by articlesReuseState.collectAsState()

                HorizontalPager(
                    count = ArticlesList.size,
                    state = pagerState,
                    modifier = Modifier.layoutId("pager3r"),
                ) { page ->

                    if (page == 0) {
                        LazyColumn(
                            contentPadding = DefaultLazyColumnContentPadding,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(items = articlesReduce, key = { it.id }) { article ->
                                Article(
                                    title = article.title,
                                    description = article.body,
                                    onClick = { onNavigateToDetailArticle(article.id) },
                                    photo = article.photo.getOrNull(0)
                                )
                            }
                        }
                    }

                    if (page == 1) {
                        LazyColumn(
                            contentPadding = DefaultLazyColumnContentPadding,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(items = articlesReuse, key = { it.id }) { article ->
                                Article(
                                    title = article.title,
                                    description = article.body,
                                    onClick = { onNavigateToDetailArticle(article.id) },
                                    photo = article.photo.getOrNull(0)
                                )
                            }
                        }
                    }
                    if (page == 2) {
                        detection?.let { detection ->
                            val garbage by garbageState.collectAsState()
                            @Suppress("NAME_SHADOWING")
                            garbage?.let { garbage ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(DefaultLazyColumnContentPadding)
                                ) {
                                    EstimatedGarbage(
                                        onClickButtonSave = { total ->
                                            onUpdateDetection(detection.id, total)
                                        },
                                        label = detection.label,
                                        date = detection.createdAt,
                                        total = detection.total,
                                        price = garbage.price,
                                        image = detection.image
                                    )
                                }
                            }
                        }
                    }
                }
            }

            val isLoading by loadingState.collectAsState()

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
