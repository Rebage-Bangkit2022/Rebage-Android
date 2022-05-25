package trashissue.rebage.presentation.threers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import trashissue.rebage.R
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.threers.component.Pages3R
import trashissue.rebage.presentation.threers.component.SwipeableContainer
import trashissue.rebage.presentation.threers.component.TabRow3R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun ThreeRsScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Plastic")
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
        }
    ) { innerPadding ->
        SwipeableContainer(modifier = Modifier.padding(innerPadding)) {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(Color.Gray)
                    .layoutId("image")
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
            Pages3R(
                modifier = Modifier.layoutId("pager3r"),
                state = pagerState,
                onClickArticle = { articleId ->
                    navController.navigate(Route.Article(articleId))
                }
            )
        }
    }
}
