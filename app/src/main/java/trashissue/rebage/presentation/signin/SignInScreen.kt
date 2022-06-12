package trashissue.rebage.presentation.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import trashissue.rebage.R
import trashissue.rebage.presentation.common.component.*
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.signin.component.NavigateToSignUpButton
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    SignInScreen(
        snackbarHostState = snackbarHostState,
        onChangeEmail = viewModel::changeEmail,
        onChangePassword = viewModel::changePassword,
        emailFieldState = viewModel.emailField,
        passwordFieldState = viewModel.passwordField,
        fulfilledState = viewModel.fulfilled,
        loadingState = viewModel.loading,
        onClickButtonSignIn = viewModel::signIn,
        onAuthGoogle = viewModel::authGoogle,
        onNavigateToSignUpScreen = {
            navController.navigate(Route.SignUp()) {
                popUpTo(Route.SignIn()) { inclusive = true }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    snackbarHostState: SnackbarHostState,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    emailFieldState: StateFlow<Pair<String, Int?>>,
    passwordFieldState: StateFlow<Pair<String, Int?>>,
    fulfilledState: StateFlow<Boolean>,
    loadingState: StateFlow<Boolean>,
    onClickButtonSignIn: () -> Unit,
    onAuthGoogle: (String?) -> Unit,
    onNavigateToSignUpScreen: () -> Unit
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val focusManager = LocalFocusManager.current
            val scrollState = rememberScrollState()
            val isLoading by loadingState.collectAsState()

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(R.drawable.ic_rebage),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = stringResource(R.string.text_welcome_to_rebage),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                val emailField by emailFieldState.collectAsState()
                val (email, emailError) = emailField

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = email,
                    onValueChange = onChangeEmail,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    maxLines = 1,
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null
                        )
                    },
                    isError = emailError != null,
                    label = {
                        Text(text = stringResource(R.string.text_email))
                    }
                )
                TextError(
                    textRes = emailError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val passwordField by passwordFieldState.collectAsState()
                val (password, passwordError) = passwordField

                OutlinedTextFieldPassword(
                    value = password,
                    onValueChange = onChangePassword,
                    isError = passwordError != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                )
                TextError(
                    textRes = passwordError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val isFulfilled by fulfilledState.collectAsState()

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    enabled = isFulfilled && !isLoading,
                    onClick = onClickButtonSignIn
                ) {
                    Text(text = stringResource(R.string.text_sign_in))
                }
                TwoLineDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 4.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.text_sign_in_alternative),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                val googleSignInClient = rememberGoogleSignInClient()
                val googleAuthLauncher = rememberGoogleAuthLauncher(
                    onResult = {
                        onAuthGoogle(it.idToken)
                    },
                    onError = Timber::e,
                )

                GoogleOauth(
                    onClick = {
                        val intent = googleSignInClient.signInIntent
                        googleAuthLauncher.launch(intent)
                    }
                )
                NavigateToSignUpButton(
                    modifier = Modifier.padding(vertical = 32.dp),
                    onClick = onNavigateToSignUpScreen
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    RebageTheme3 {
        SignInScreen(
            snackbarHostState = remember { SnackbarHostState() },
            onChangeEmail = { },
            onChangePassword = { },
            emailFieldState = MutableStateFlow("tubagus@gmail.com" to null),
            passwordFieldState = MutableStateFlow("secret" to null),
            fulfilledState = MutableStateFlow(true),
            loadingState = MutableStateFlow(true),
            onClickButtonSignIn = { },
            onAuthGoogle = { },
            onNavigateToSignUpScreen = { }
        )
    }
}

