package trashissue.rebage.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import trashissue.rebage.R
import trashissue.rebage.domain.model.User
import trashissue.rebage.presentation.common.component.rememberGoogleSignInClient
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.profile.component.Photo
import trashissue.rebage.presentation.profile.component.ProfileMenuItem
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    ProfileScreen(
        snackbarHostState = snackbarHostState,
        userState = viewModel.user,
        onClickButtonSignOut = viewModel::signOut,
        onNavigateToFavoriteArticleScreen = { navController.navigate(Route.FavoriteArticle()) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    snackbarHostState: SnackbarHostState,
    userState: StateFlow<User?>,
    onClickButtonSignOut: () -> Unit,
    onNavigateToFavoriteArticleScreen: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.text_profile),
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                actions = {
                    val googleSignInClient= rememberGoogleSignInClient()

                    IconButton(
                        onClick = {
                            googleSignInClient.signOut()
                            onClickButtonSignOut()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = stringResource(R.string.cd_sign_out)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val user by userState.collectAsState()

            Photo(
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                photo = user?.photo,
                onClick = {}
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                text = user?.name ?: "-",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                text = user?.email ?: "-",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
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
                onClick = onNavigateToFavoriteArticleScreen,
                text = "Favorite Article"
            )
            ProfileMenuItem(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = "Theme",
                icon = {
                    var darkTheme by remember { mutableStateOf(false) }
                    Switch(
                        modifier = Modifier.height(24.dp),
                        checked = darkTheme,
                        onCheckedChange = { darkTheme = !darkTheme }
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    RebageTheme3 {
        ProfileScreen(
            snackbarHostState = remember { SnackbarHostState() },
            userState = MutableStateFlow(User(1, "Tubagus", "tubagus@student.ub.ac.id", null, "")),
            onClickButtonSignOut = {},
            onNavigateToFavoriteArticleScreen = {}
        )
    }
}
