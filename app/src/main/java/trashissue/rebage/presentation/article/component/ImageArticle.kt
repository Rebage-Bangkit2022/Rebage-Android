package trashissue.rebage.presentation.article.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import trashissue.rebage.presentation.article.PagerSnapNestedScrollConnection
import trashissue.rebage.presentation.article.rememberPagerSnapState
import java.lang.Math.abs

//@Composable
//fun ImageArticle(
//    modifier: Modifier = Modifier,
//) {
//    val lazyListState = rememberLazyListState()
//
//    val state = rememberPagerSnapState()
//
//    val scope = rememberCoroutineScope()
//
//    val configuration = LocalConfiguration.current
//    val screenWidth = configuration.screenWidthDp.dp
//    val widthPx = with(LocalDensity.current) {
//        screenWidth.roundToPx()
//    }
//
//    val connection = remember(state, lazyListState) {
//        PagerSnapNestedScrollConnection(state, lazyListState) {
//
//            val firstItemIndex = state.firstVisibleItemIndex.value
//            val firstItemOffset = abs(state.offsetInfo.value)
//
//            val position = when {
//                firstItemOffset <= widthPx.div(2) -> firstItemIndex
//                else -> firstItemIndex.plus(1)
//            }
//
//            scope.launch {
//                state.scrollItemToSnapPosition(lazyListState, position)
//            }
//
//        }
//    }
//
//    val padding = 16.dp
//    Box(
//        modifier = modifier
//            .nestedScroll(connection = connection)
//            .fillMaxWidth()
//            .padding(horizontal = padding)
//            .padding(top = padding)
//    ) {
//        LazyRow(
//            state = lazyListState,
//            horizontalArrangement = Arrangement.spacedBy(padding),
//        ) {
//
//            items(imageList.size) { ind ->
//                Image(
//                    painter = painterResource(imageList[ind]),
//                    contentDescription = "Article Images",
//                    modifier = Modifier
//                        .fillParentMaxWidth(),
//                    contentScale = ContentScale.Fit,
//                )
//            }
//        }
//        LazyRow(
//            Modifier
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 8.dp),
//            horizontalArrangement = Arrangement.spacedBy(2.dp)
//        ) {
//            items(imageList.size) { ind ->
//                if (ind == lazyListState.firstVisibleItemIndex) {
//                    Text(
//                        text = ".",
//                        color = Color.White,
//                        fontSize = 28.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                } else {
//                    Text(
//                        text = ".",
//                        modifier = Modifier
//                            .alpha(.5f),
//                        color = Color.White,
//                        fontSize = 28.sp,
//                        fontWeight = FontWeight.Light
//                    )
//                }
//            }
//        }
//    }
//}
