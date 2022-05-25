package trashissue.rebage.presentation.favoritearticle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import trashissue.rebage.R
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.threers.component.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteArticleScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = stringResource(R.string.text_favorite_article))
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(20, key = { it }) {
                Article(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = "Kerajinan Tempat Pensil dari Botol Plastik",
                    description = "Kerajinan Tempat Pensil dari Botol Plastik",
                    onClick = {
                        navController.navigate(Route.Article(1))
                    }
                )
            }
        }
    }
}
