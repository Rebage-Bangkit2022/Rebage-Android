package trashissue.rebage.presentation.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import trashissue.rebage.R
import trashissue.rebage.di.UseCaseModule
import trashissue.rebage.domain.usecase.ValidateEmailUseCase
import trashissue.rebage.presentation.common.component.OutlinedTextFieldPassword
import trashissue.rebage.presentation.common.component.TextError
import trashissue.rebage.presentation.common.component.TwoLineDivider
import trashissue.rebage.presentation.navhost.Route
import trashissue.rebage.presentation.theme.RebageTheme

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_rebage),
            contentDescription = null,
            modifier = Modifier.size(84.dp)
        )
        Text(
            text = stringResource(R.string.text_welcome),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 12.dp),
            color = MaterialTheme.colors.primary
        )

        val email by viewModel.emailValue.collectAsState()
        val password by viewModel.passwordValue.collectAsState()
        val isEnabled by viewModel.isEnabled.collectAsState()

        OutlinedTextField(
            value = email.value,
            onValueChange = viewModel::validateEmail,
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
            field = email,
            modifier = Modifier.align(Alignment.Start)
        )
        OutlinedTextFieldPassword(
            value = password.value,
            onValueChange = viewModel::validatePassword,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextError(
            field = password,
            modifier = Modifier.align(Alignment.Start)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = isEnabled,
            onClick = {
//                viewModel.signIn()
                navController.navigate(Route.Home()) {
//                    popUpTo(Route.SignIn()) { inclusive = true }
                }
            }
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
                text = stringResource(R.string.text_sign_alternative),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )
        }
        IconButton(
            modifier = Modifier.size(64.dp),
            onClick = {

            }
        ) {
            Image(
                painter = painterResource(R.drawable.ic_google),
                contentDescription = stringResource(R.string.cd_google_sign_in),
                modifier = Modifier.size(32.dp),
            )
        }
        NavigateToSignUpScreen(
            modifier = Modifier.padding(vertical = 32.dp),
            onClick = {

            }
        )
    }
}

@Composable
fun NavigateToSignUpScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Don't have an account?",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground
        )
        Text(
            text = "Sign up now",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.clickable(onClick = onClick)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreen() {
    RebageTheme {
        SignInScreen(
            navController = rememberNavController(),
            viewModel = SignInViewModel(
                validateEmailUseCase = UseCaseModule.provideValidateEmailUseCase(),
                validatePasswordUseCase = UseCaseModule.provideValidatePasswordUseCase(),
                signInUseCase = UseCaseModule.provideSignInUseCase()
            )
        )
    }
}
