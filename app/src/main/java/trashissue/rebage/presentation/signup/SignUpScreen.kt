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
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import trashissue.rebage.R
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.model.isLoading
import trashissue.rebage.domain.model.onError
import trashissue.rebage.presentation.common.component.*
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.signup.component.NavigateToSignInButton
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    SignUpScreen(
        navController = navController,
        formStateFlow = viewModel.formState,
        isEnabledFlow = viewModel.isEnabled,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        signUpResultFlow = viewModel.signUpResult,
        signUp = viewModel::signUp,
        authGoogle = viewModel::authGoogle
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    formStateFlow: StateFlow<FormState>,
    isEnabledFlow: StateFlow<Boolean>,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    signUpResultFlow: StateFlow<Result<User>>,
    signUp: () -> Unit,
    authGoogle: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val signUpResult by signUpResultFlow.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(snackbarHostState, signUpResult) {
        signUpResult.onError { error ->
            val message = error.message ?: context.getString(R.string.text_unknown_error)
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val scrollState = rememberScrollState()
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

                val formState by formStateFlow.collectAsState()

                OutlinedTextField(
                    value = formState.name,
                    onValueChange = onNameChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    label = {
                        Text(text = stringResource(R.string.text_name))
                    }
                )
                TextError(
                    textRes = formState.nameErrorMessage,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextField(
                    value = formState.email,
                    onValueChange = onEmailChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = stringResource(R.string.text_email))
                    }
                )
                TextError(
                    textRes = formState.emailErrorMessage,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextFieldPassword(
                    value = formState.password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                TextError(
                    textRes = formState.passwordErrorMessage,
                    modifier = Modifier.align(Alignment.Start)
                )
                OutlinedTextFieldPassword(
                    value = formState.confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = stringResource(R.string.text_confirm_password)
                )
                TextError(
                    textRes = formState.confirmPasswordErrorMessage,
                    modifier = Modifier.align(Alignment.Start)
                )

                val isEnabled by isEnabledFlow.collectAsState()

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    enabled = isEnabled,
                    onClick = { signUp() }
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

                val scope = rememberCoroutineScope()
                val googleSignInClient = rememberGoogleSignInClient()
                val googleAuthLauncher = rememberGoogleAuthLauncher(
                    onResult = { account ->
                        val idToken = account.idToken
                        if (idToken == null) {
                            scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.text_unknown_error)) }
                            return@rememberGoogleAuthLauncher
                        }
                        authGoogle(idToken)
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
                    onClick = {
                        navController.navigate(Route.SignIn()) {
                            popUpTo(Route.SignUp()) { inclusive = true }
                        }
                    }
                )
            }

            if (signUpResult.isLoading) {
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
            navController = rememberNavController(),
            formStateFlow = MutableStateFlow(FormState()),
            isEnabledFlow = MutableStateFlow(true),
            onNameChange = { },
            onEmailChange = { },
            onPasswordChange = { },
            onConfirmPasswordChange = {},
            signUpResultFlow = MutableStateFlow(Result.NoData()),
            signUp = { },
            authGoogle = { }
        )
    }
}
