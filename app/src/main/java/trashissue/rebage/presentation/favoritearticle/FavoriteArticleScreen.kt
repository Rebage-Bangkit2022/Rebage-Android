package trashissue.rebage.presentation.favoritearticle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import trashissue.rebage.R
import trashissue.rebage.presentation.common.statusBarsPaddingWithColor

@Composable
fun FavoriteArticleScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPaddingWithColor()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.text_favorite_article),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBackIos,
                            stringResource(R.string.cd_back)
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(20, key = { it }) {
                    Article(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        title = "Kerajinan Tempat Pensil dari Botol Plastik",
                        description = "Kerajinan Tempat Pensil dari Botol Plastik"
                    )
                }
            }
        }
    }
}

@Composable
fun Article(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Card(
        modifier = modifier.height(120.dp),
        elevation = 0.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Gray)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
