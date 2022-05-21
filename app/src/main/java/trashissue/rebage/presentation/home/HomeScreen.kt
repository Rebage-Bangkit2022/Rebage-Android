package trashissue.rebage.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import trashissue.rebage.R
import trashissue.rebage.presentation.home.component.ChartData
import trashissue.rebage.presentation.home.component.Header
import trashissue.rebage.presentation.theme.ForestGreen200
import trashissue.rebage.presentation.theme.ForestGreen500
import trashissue.rebage.presentation.theme.Jasmine500

val GarbageStat = listOf(
    ChartData("Plastik", Color.Red, 4.0),
    ChartData("Kardus", Color.Blue, 3.0),
    ChartData("Bebek", Color.Blue, 16.0),
    ChartData("Ayam", Color.Black, 12.0),
    ChartData("Plastik", Jasmine500, 14.0),
    ChartData("Kardus", Color.Gray, 5.0),
    ChartData("Bebek", Color.Magenta, 6.0),
    ChartData("Ayam", Color.LightGray, 8.0),
    ChartData("Plastik", ForestGreen500, 9.0),
    ChartData("Kardus", ForestGreen500, 20.0),
    ChartData("Bebek", ForestGreen200, 11.0),
    ChartData("Ayam", Color.Gray, 13.0),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val density = LocalDensity.current
    val statusBarHeight = with(density) { WindowInsets.statusBars.getTop(this).toDp() }

    LazyColumn {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(statusBarHeight)
                    .background(MaterialTheme.colors.primary)
            )
        }
        item {
            Header(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)
            )
        }
        item {
            Articles()
        }
        item {
            Articles()
        }
    }
}

@Composable
fun Articles(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(R.string.text_recommended_articles),
                style = MaterialTheme.typography.h6
            )
            Text(
                style = MaterialTheme.typography.subtitle2,
                text = stringResource(R.string.text_see_more),
                color = MaterialTheme.colors.primary
            )
        }
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
            style = MaterialTheme.typography.subtitle2,
            text = stringResource(R.string.text_recommendation_subtitle),
            color = MaterialTheme.colors.onBackground.copy(0.7F)
        )
        LazyRow(
            modifier = Modifier.padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.width(4.dp))
            }
            items(10, key = { it }) {
                Article(modifier = Modifier.width(240.dp))
            }
            item {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun Article(
    modifier: Modifier = Modifier
) {
    Card(elevation = 4.dp) {
        Column(
            modifier = modifier.wrapContentSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Gray)
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Cara Indonesia Kurangi Sampah Plastik hingga 70 Persen",
                style = MaterialTheme.typography.subtitle2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
