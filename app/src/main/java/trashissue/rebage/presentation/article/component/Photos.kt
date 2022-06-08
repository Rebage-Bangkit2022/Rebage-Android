package trashissue.rebage.presentation.article.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Photos(
    modifier: Modifier = Modifier,
    photos: List<String>
) {
    val photos4 = remember(photos) { photos.take(4) }

    Box(modifier = modifier) {
        val pagerState = rememberPagerState()

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = photos4.size,
            state = pagerState
        ) { index ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photos4[index])
                    .crossfade(500)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        if (photos.size != 1) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.BottomCenter),
                indicatorShape = RoundedCornerShape(8.dp),
                indicatorWidth = 32.dp,
                indicatorHeight = 6.dp,
                spacing = 12.dp,
                activeColor = MaterialTheme.colorScheme.primary,
                inactiveColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}
