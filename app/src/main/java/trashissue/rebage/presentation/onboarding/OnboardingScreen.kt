package trashissue.rebage.presentation.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import trashissue.rebage.R
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.onboarding.component.OnboardingContent
import trashissue.rebage.presentation.onboarding.component.Pages
import trashissue.rebage.presentation.onboarding.component.TopBar
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun OnboardingScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    OnboardingScreen(
        navController = navController,
        onboarding = viewModel::onboarding
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(
    navController: NavHostController,
    onboarding: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .heightIn(760.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(
            onClickSkip = {
                navController.navigate(Route.SignIn())
            },
            text = stringResource(R.string.text_skip)
        )

        val pagerState = rememberPagerState()

        Pages(
            modifier = Modifier.weight(1F),
            pagerState = pagerState
        )
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.padding(vertical = 32.dp),
            indicatorShape = RoundedCornerShape(8.dp),
            indicatorWidth = 32.dp,
            indicatorHeight = 6.dp,
            spacing = 12.dp,
            activeColor = MaterialTheme.colorScheme.primary
        )

        val isLastPage by remember { derivedStateOf { pagerState.currentPage == OnboardingContent.size - 1 } }
        val scope = rememberCoroutineScope()

        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                scope.launch {
                    if (isLastPage) {
                        onboarding(true)
                        return@launch
                    }
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        ) {
            Text(text = if (!isLastPage) stringResource(R.string.text_next) else stringResource(R.string.text_get_started))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    RebageTheme3 {
        OnboardingScreen(navController = rememberNavController())
    }
}
