package trashissue.rebage.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import trashissue.rebage.R
import trashissue.rebage.presentation.common.statusBarsPaddingWithColor
import trashissue.rebage.presentation.main.Route

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPaddingWithColor()
            .fillMaxSize(),
        topBar = {
            TopAppBar {
                Text(
                    text = stringResource(R.string.text_profile),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 16.dp)
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                text = "Tubagus",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                text = "tubagus@student.ub.ac.id",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
            ProfileMenuItem(
                modifier = Modifier
                    .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                onClick = {},
                text = "Edit Profile"
            )
            ProfileMenuItem(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = {
                    navController.navigate(Route.FavoriteArticle())
                },
                text = "Favorite Article"
            )
            ProfileMenuItem(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = "Theme",
                icon = {
                    var darkTheme by remember { mutableStateOf(false) }
                    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                        Switch(
                            checked = darkTheme,
                            onCheckedChange = {
                                darkTheme = !darkTheme
                            }
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileMenuItem(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    text: String,
    icon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Outlined.ArrowForwardIos,
            contentDescription = null
        )
    }
) {
    Card(
        modifier = modifier.wrapContentSize(),
        elevation = 0.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary)
    ) {
        Row(
            modifier = Modifier
                .clickable(enabled = onClick != null, onClick = onClick ?: {})
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                text = text,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.width(8.dp))
            icon()
        }
    }
}
