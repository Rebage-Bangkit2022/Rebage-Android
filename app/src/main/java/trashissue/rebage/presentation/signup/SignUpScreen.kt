package trashissue.rebage.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import trashissue.rebage.R
import trashissue.rebage.presentation.common.component.*
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.signup.component.NavigateToSignInButton
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    SignUpScreen(
        snackbarHostState = snackbarHostState,
        onChangeName = viewModel::changeName,
        onChangeEmail = viewModel::changeEmail,
        onChangePassword = viewModel::changePassword,
        onChangeConfirmPassword = viewModel::changeConfirmPassword,
        nameFieldState = viewModel.nameField,
        emailFieldState = viewModel.emailField,
        passwordFieldState = viewModel.passwordField,
        confirmPasswordFieldState = viewModel.confirmPasswordField,
        fulfilledState = viewModel.fulfilled,
        loadingState = viewModel.loading,
        onClickButtonSignUp = viewModel::signUp,
        onAuthGoogle = viewModel::authGoogle,
        onNavigateToSignInScreen = {
            navController.navigate(Route.SignIn()) {
                popUpTo(Route.SignUp()) { inclusive = true }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    snackbarHostState: SnackbarHostState,
    onChangeName: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeConfirmPassword: (String) -> Unit,
    nameFieldState: StateFlow<Pair<String, Int?>>,
    emailFieldState: StateFlow<Pair<String, Int?>>,
    passwordFieldState: StateFlow<Pair<String, Int?>>,
    confirmPasswordFieldState: StateFlow<Pair<String, Int?>>,
    fulfilledState: StateFlow<Boolean>,
    loadingState: StateFlow<Boolean>,
    onClickButtonSignUp: () -> Unit,
    onAuthGoogle: (String?) -> Unit,
    onNavigateToSignInScreen: () -> Unit
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
                    painter = painterResource(R.drawable.ic_rebage),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = stringResource(R.string.text_welcome),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                val nameField by nameFieldState.collectAsState()
                val (name, nameError) = nameField

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = name,
                    onValueChange = onChangeName,
                    label = {
                        Text(text = stringResource(R.string.text_name))
                    },
                    maxLines = 1
                )
                TextError(
                    textRes = nameError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val emailField by emailFieldState.collectAsState()
                val (email, emailError) = emailField

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = email,
                    onValueChange = onChangeEmail,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = stringResource(R.string.text_email))
                    },
                    maxLines = 1
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                TextError(
                    textRes = passwordError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val confirmPasswordField by confirmPasswordFieldState.collectAsState()
                val (confirmPassword, confirmPasswordError) = confirmPasswordField

                OutlinedTextFieldPassword(
                    value = confirmPassword,
                    onValueChange = onChangeConfirmPassword,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = stringResource(R.string.text_confirm_password)
                )
                TextError(
                    textRes = confirmPasswordError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val isFulfilled by fulfilledState.collectAsState()

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    enabled = isFulfilled && !isLoading,
                    onClick = onClickButtonSignUp
                ) {
                    Text(text = stringResource(R.string.text_sign_up))
                }
                TwoLineDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 4.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.text_sign_up_alternative),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                val context = LocalContext.current
                val scope = rememberCoroutineScope()
                val googleSignInClient = rememberGoogleSignInClient()
                val googleAuthLauncher = rememberGoogleAuthLauncher(
                    onResult = { account ->
                        onAuthGoogle(account.idToken)
                    },
                    onError = { error ->
                        val message =
                            error?.message ?: context.getString(R.string.text_unknown_error)
                        scope.launch { snackbarHostState.showSnackbar(message) }
                    },
                )

                GoogleOauth(
                    onClick = {
                        val intent = googleSignInClient.signInIntent
                        googleAuthLauncher.launch(intent)
                    }
                )
                NavigateToSignInButton(
                    modifier = Modifier.padding(vertical = 32.dp),
                    onClick = onNavigateToSignInScreen
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
fun SignUpScreenPreview() {
    RebageTheme3 {
        SignUpScreen(
            snackbarHostState = remember { SnackbarHostState() },
            onChangeName = { },
            onChangeEmail = { },
            onChangePassword = { },
            onChangeConfirmPassword = { },
            nameFieldState = MutableStateFlow("tubagus" to null),
            emailFieldState = MutableStateFlow("tubagus@gmail.com" to null),
            passwordFieldState = MutableStateFlow("secret" to null),
            confirmPasswordFieldState = MutableStateFlow("secret" to null),
            fulfilledState = MutableStateFlow(true),
            loadingState = MutableStateFlow(true),
            onClickButtonSignUp = { },
            onAuthGoogle = { },
            onNavigateToSignInScreen = { }
        )
    }
}
