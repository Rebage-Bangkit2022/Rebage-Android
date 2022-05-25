package trashissue.rebage.presentation.threers.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

private val DefaultContentPadding = PaddingValues(16.dp)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pages3R(
    modifier: Modifier = Modifier,
    state: PagerState = rememberPagerState(),
    onClickArticle: (Int) -> Unit
) {
    HorizontalPager(
        count = ArticlesList.size,
        state = state,
        modifier = modifier
    ) { page ->
        if (page == 0) {
            LazyColumn(
                contentPadding = DefaultContentPadding,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(10, key = { it }) {
                    Article(
                        title = "Title reduse $it",
                        description = "Check that graphics HAL is generating vsync timestamps using the correct timebase.",
                        onClick = { onClickArticle(it) }
                    )
                }
            }
        }
        if (page == 1) {
            LazyColumn(
                contentPadding = DefaultContentPadding,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(10, key = { it }) {
                    Article(
                        title = "Title reuse $it",
                        description = "Check that graphics HAL is generating vsync timestamps using the correct timebase.",
                        onClick = { onClickArticle(it) }
                    )
                }
            }
        }
        if (page == 2) {
            LazyColumn(
                contentPadding = DefaultContentPadding,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(10, key = { it }) {
                    Article(
                        title = "Title recy $it",
                        description = "Check that graphics HAL is generating vsync timestamps using the correct timebase.",
                        onClick = { onClickArticle(it) }
                    )
                }
            }
        }
    }
}
