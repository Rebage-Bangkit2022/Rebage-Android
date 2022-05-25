package trashissue.rebage.presentation.threers.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TabRow3R(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    onClickTab: (Int) -> Unit
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex
    ) {
        ArticlesList.forEachIndexed { index, titleRes ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onClickTab(index) }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(titleRes)
                )
            }
        }
    }
}
