package trashissue.rebage.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import trashissue.rebage.R
import trashissue.rebage.presentation.common.statusBarsPaddingWithColor
import trashissue.rebage.presentation.home.component.Articles
import trashissue.rebage.presentation.home.component.ChartData
import trashissue.rebage.presentation.home.component.Header
import trashissue.rebage.presentation.theme.ForestGreen200
import trashissue.rebage.presentation.theme.ForestGreen500
import trashissue.rebage.presentation.theme.Jasmine500
import trashissue.rebage.presentation.theme.PastelChart

val DummyGarbageStat = listOf(
    ChartData("Plastik", Color.Red, 4.0),
    ChartData("Kardus", Color.Blue, 3.0),
    ChartData("Bebek", PastelChart, 16.0),
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

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPaddingWithColor()
            .verticalScroll(rememberScrollState())
    ) {

        val isLight = MaterialTheme.colorScheme.isLight()
        val systemUiController = rememberSystemUiController()

        DisposableEffect(isLight) {
            systemUiController.setStatusBarColor(Color.Transparent, darkIcons = false)

            onDispose {
                systemUiController.setStatusBarColor(Color.Transparent, darkIcons = isLight)
            }
        }

        Header(modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp))
        Articles(label = stringResource(R.string.text_recommended_articles))
        Articles(label = stringResource(R.string.text_handicraft_product))
        Articles(label = stringResource(R.string.text_recommended_articles))
    }
}

@Composable
fun ColorScheme.isLight() = this.background.luminance() > 0.5
