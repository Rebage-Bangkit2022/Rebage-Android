package trashissue.rebage.presentation.threers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import trashissue.rebage.R
import trashissue.rebage.domain.model.success
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.threers.component.*

private val DefaultLazyColumnContentPadding = PaddingValues(16.dp)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun ThreeRsScreen(
    navController: NavHostController,
    viewModel: ThreeRsViewModel = hiltViewModel(),
    name: String,
    image: String
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = name)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIos,
                            contentDescription = stringResource(R.string.cd_back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface,
                text = {
                    Text(text = "Bank Sampah")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Map,
                        contentDescription = null
                    )
                }
            )
        }
    ) { innerPadding ->
        SwipeableContainer(modifier = Modifier.padding(innerPadding)) {
            AsyncImage(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(Color.Gray)
                    .layoutId("image"),
                model = image,
                contentDescription = name,
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
            HorizontalPager(
                count = ArticlesList.size,
                state = pagerState,
                modifier = Modifier.layoutId("pager3r"),
            ) { page ->
                val reuseResult by viewModel.reuse.collectAsState()

                if (page == 0) {
                    reuseResult.success { articles ->
                        LazyColumn(
                            contentPadding = DefaultLazyColumnContentPadding,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(items = articles, key = { it.id }) { article ->
                                Article(
                                    title = article.title,
                                    description = article.body,
                                    onClick = {
                                        navController.navigate(Route.Article(article.id))
                                    },
                                    photo = article.photo.getOrNull(0)
                                )
                            }
                        }
                    }
                }

                val reduceArticle by viewModel.reduce.collectAsState()

                if (page == 1) {
                    reduceArticle.success { articles ->
                        LazyColumn(
                            contentPadding = DefaultLazyColumnContentPadding,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(items = articles, key = { it.id }) { article ->
                                Article(
                                    title = article.title,
                                    description = article.body,
                                    onClick = {
                                        navController.navigate(Route.Article(article.id))
                                    },
                                    photo = article.photo.getOrNull(0)
                                )
                            }
                        }
                    }
                }
                if (page == 2) {
                    LazyColumn(
                        contentPadding = DefaultLazyColumnContentPadding,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            EstimatedGarbage(
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}
