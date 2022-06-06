package trashissue.rebage.presentation.article

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import trashissue.rebage.R
import trashissue.rebage.domain.model.Article
import trashissue.rebage.presentation.article.component.Photos
import trashissue.rebage.presentation.common.component.shimmer

@Composable
fun ArticleScreen(
    navController: NavHostController,
    viewModel: ArticleViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    ArticleScreen(
        snackbarHostState = snackbarHostState,
        articleState = viewModel.article,
        onNavigationBack = navController::popBackStack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    snackbarHostState: SnackbarHostState,
    articleState: StateFlow<Article?>,
    onNavigationBack: () -> Unit
) {
    val article by articleState.collectAsState()

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = article?.title ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationBack) {
                        Icon(
                            Icons.Rounded.ArrowBackIos,
                            stringResource(R.string.cd_back)
                        )
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            Photos(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .shimmer(article == null),
                photos = article?.photo ?: emptyList()
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .shimmer(article == null),
                text = article?.title ?: "Title Placeholder",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .shimmer(article == null),
                text = article?.author ?: "Author Placeholder",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .shimmer(article == null),
                text = article?.createdAt ?: "Date Placeholder",
                style = MaterialTheme.typography.bodySmall
            )
            article?.body?.let { body ->
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .shimmer(article == null),
                    text = body,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            } ?: run {
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        .weight(1F)
                        .shimmer(article == null)
                )
            }
        }
    }
}
