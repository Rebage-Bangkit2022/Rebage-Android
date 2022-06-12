package trashissue.rebage.presentation.listarticle

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import trashissue.rebage.R
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.threers.component.Article

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListArticleScreen(
    navController: NavHostController,
    viewModel: ListArticleViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadArticles()
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            val type by viewModel.type.collectAsState()

            SmallTopAppBar(
                title = {
                    Text(
                        text = when (type) {
                            ListArticleType.Favorite -> stringResource(R.string.text_favorite_article)
                            ListArticleType.Latest -> stringResource(R.string.text_latest_articles)
                            ListArticleType.Reduce -> stringResource(R.string.text_reduce)
                            ListArticleType.Reuse -> stringResource(R.string.text_handicraft_product)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.Rounded.ArrowBackIos,
                            stringResource(R.string.cd_back)
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        val articles by viewModel.articles.collectAsState()

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(articles, key = { it.id }) { article ->
                    Article(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .animateItemPlacement(),
                        title = article.title,
                        description = article.body,
                        onClick = {
                            navController.navigate(Route.Article(article.id))
                        },
                        photo = article.photo.getOrNull(0)
                    )
                }
            }

            val isLoading by viewModel.loading.collectAsState()
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.empty_box)
            )

            if (articles.isEmpty() && !isLoading) {
                LottieAnimation(
                    modifier = Modifier
                        .padding(32.dp)
                        .size(164.dp)
                        .align(Alignment.Center)
                        .offset(y = (-24).dp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
