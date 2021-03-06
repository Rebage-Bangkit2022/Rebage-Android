package trashissue.rebage.presentation.threers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import trashissue.rebage.R
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.model.Detection
import trashissue.rebage.domain.model.Garbage
import trashissue.rebage.presentation.common.DateFormatter
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

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalPagerApi::class,
    ExperimentalAnimationApi::class
)
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
    val pagerState = rememberPagerState()
    val isFabVisible by remember {
        derivedStateOf {
            Timber.i(pagerState.currentPage.toString())
            pagerState.currentPage == 2
        }
    }

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
            AnimatedVisibility(
                visible = isFabVisible,
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                ExtendedFloatingActionButton(
                    onClick = onNavigateToGarbageBank,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface,
                    text = {
                        Text(text = stringResource(R.string.text_garbage_bank))
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Map,
                            contentDescription = null
                        )
                    }
                )
            }
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
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(R.raw.empty_box)
                )

                HorizontalPager(
                    count = ArticlesList.size,
                    state = pagerState,
                    modifier = Modifier.layoutId("pager3r"),
                ) { page ->
                    if (page == 0) {
                        if (articlesReduce.isNotEmpty()) {
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
                        } else {
                            LottieAnimation(
                                modifier = Modifier
                                    .padding(32.dp)
                                    .size(164.dp)
                                    .offset(y = (-24).dp),
                                composition = composition,
                                iterations = LottieConstants.IterateForever,
                            )
                        }
                    }

                    if (page == 1) {
                        if (articlesReuse.isNotEmpty()) {
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
                        } else {
                            LottieAnimation(
                                modifier = Modifier
                                    .padding(32.dp)
                                    .size(164.dp)
                                    .offset(y = (-24).dp),
                                composition = composition,
                                iterations = LottieConstants.IterateForever,
                            )
                        }
                    }
                    if (page == 2) {
                        val garbage by garbageState.collectAsState()

                        detection?.let { detection ->
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
                                        date = DateFormatter.format(detection.createdAt),
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
