package trashissue.rebage.presentation.onboarding.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import trashissue.rebage.R
import trashissue.rebage.presentation.theme3.RebageTheme3

val OnboardingContent = listOf(
    Triple(
        R.drawable.ic_onboarding1,
        R.string.title_onboarding1,
        R.string.subtitle_onboarding1,
    ),
    Triple(
        R.drawable.ic_onboarding2,
        R.string.title_onboarding2,
        R.string.subtitle_onboarding2,
    ),
    Triple(
        R.drawable.ic_onboarding3,
        R.string.title_onboarding3,
        R.string.subtitle_onboarding3,
    )
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pages(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(),
) {
    HorizontalPager(
        modifier = modifier,
        count = OnboardingContent.size,
        state = pagerState
    ) { page ->
        val (illustration, title, subtitle) = OnboardingContent[page]

        Page(
            painter = painterResource(illustration),
            title = stringResource(title),
            subTitle = stringResource(subtitle)
        )
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalPagerApi::class)
fun PagesPreview() {
    RebageTheme3 {
        Pages(pagerState = rememberPagerState())
    }
}
