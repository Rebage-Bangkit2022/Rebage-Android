package trashissue.rebage.presentation.article.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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
    Box(modifier = modifier) {
        val pagerState = rememberPagerState()
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = photos.size,
            state = pagerState
        ) { index ->
            AsyncImage(
                model = photos[index],
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.BottomCenter),
            indicatorShape = RoundedCornerShape(8.dp),
            indicatorWidth = 32.dp,
            indicatorHeight = 6.dp,
            spacing = 12.dp,
            activeColor = MaterialTheme.colorScheme.primary
        )
    }
}
