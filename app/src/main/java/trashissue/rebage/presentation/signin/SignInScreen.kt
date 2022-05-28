package trashissue.rebage.presentation.signin

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
import timber.log.Timber
import trashissue.rebage.R
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.model.isLoading
import trashissue.rebage.domain.model.onError
import trashissue.rebage.presentation.common.component.OutlinedTextFieldPassword
import trashissue.rebage.presentation.common.component.TextError
import trashissue.rebage.presentation.common.component.TwoLineDivider
import trashissue.rebage.presentation.main.Route
import trashissue.rebage.presentation.signin.component.NavigateToSignUpButton
import trashissue.rebage.presentation.theme3.RebageTheme3

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    SignInScreen(
        navController = navController,
        formStateFlow = viewModel.formState,
        isEnabledFlow = viewModel.isEnabled,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        signInResultFlow = viewModel.signInResult,
        signIn = viewModel::signIn
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavHostController,
    formStateFlow: StateFlow<FormState>,
    isEnabledFlow: StateFlow<Boolean>,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    signInResultFlow: StateFlow<Result<User>>,
    signIn: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val signInResult by signInResultFlow.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(snackbarHostState, signInResult) {
        Timber.i("RESULT $signInResult")
        signInResult.onError { error ->
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
                    onClick = { signIn() }
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
                IconButton(
                    modifier = Modifier.size(64.dp),
                    onClick = { }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = stringResource(R.string.cd_google_sign_in),
                        modifier = Modifier.size(32.dp),
                    )
                }
                NavigateToSignUpButton(
                    modifier = Modifier.padding(vertical = 32.dp),
                    onClick = {
                        navController.navigate(Route.SignUp()) {
                            popUpTo(Route.SignIn()) { inclusive = true }
                        }
                    }
                )
            }

            if (signInResult.isLoading) {
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
            navController = rememberNavController(),
            formStateFlow = MutableStateFlow(FormState()),
            isEnabledFlow = MutableStateFlow(true),
            onEmailChange = { },
            onPasswordChange = { },
            signInResultFlow = MutableStateFlow(Result.NoData()),
            signIn = { }
        )
    }
}

