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
import androidx.navigation.compose.rememberNavController
import trashissue.rebage.R
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
    ProfileScreen(
        navController = navController,
        signOut = viewModel::signOut
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    signOut: () -> Unit
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
                            signOut()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = stringResource(R.string.cd_sign_out)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Photo(
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                onClick = {}
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                text = "Tubagus",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                text = "tubagus@student.ub.ac.id",
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
                    Switch(
                        modifier = Modifier.height(24.dp),
                        checked = darkTheme,
                        onCheckedChange = {
                            darkTheme = !darkTheme
                        }
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
            navController = rememberNavController(),
            signOut = { }
        )
    }
}
