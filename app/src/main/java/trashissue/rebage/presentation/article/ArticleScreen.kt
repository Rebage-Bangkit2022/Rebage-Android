package trashissue.rebage.presentation.article

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import trashissue.rebage.R
import trashissue.rebage.presentation.article.component.Images

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    navController: NavHostController,
    articleId: Int
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = stringResource(R.string.text_recycle))
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
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            Images(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                text = "Title Title Title Title Title Title Title Title Title Title Title Title",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                text = "Author name",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "24 November 2022",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Body Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body  Body ",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify
            )
        }
    }
}
