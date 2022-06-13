package trashissue.rebage.presentation.editprofile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import trashissue.rebage.R
import trashissue.rebage.presentation.common.component.OutlinedTextFieldPassword
import trashissue.rebage.presentation.common.component.TextError

private val SurfaceShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavHostController,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = stringResource(R.string.text_edit_profile))
                },
                navigationIcon = {
                    IconButton(onClick = navController::popBackStack) {
                        Icon(
                            Icons.Outlined.ArrowBackIos,
                            stringResource(R.string.cd_back)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val isLoading by viewModel.loading.collectAsState()

            Column {
                val user by viewModel.user.collectAsState()

                AsyncImage(
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(120.dp)
                        .clip(CircleShape),
                    model = user?.photo,
                    contentDescription = user?.name
                )
                Surface(
                    modifier = Modifier.clip(SurfaceShape),
                    tonalElevation = 4.dp
                ) {
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                    ) {
                        val nameField by viewModel.nameField.collectAsState()
                        val (name, nameError) = nameField

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            value = name,
                            onValueChange = viewModel::changeName,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            maxLines = 1,
                            singleLine = true,
                            label = {
                                Text(text = stringResource(R.string.text_name))
                            },
                            isError = nameError != null
                        )
                        TextError(textRes = nameError)
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            value = user?.email ?: "",
                            onValueChange = { },
                            enabled = false,
                            label = {
                                Text(text = stringResource(R.string.text_email))
                            }
                        )
                        TextError(textRes = null)

                        val passwordField by viewModel.passwordField.collectAsState()
                        val (password, passwordError) = passwordField

                        OutlinedTextFieldPassword(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            value = password,
                            onValueChange = viewModel::changePassword,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            maxLines = 1,
                            singleLine = true,
                            label = stringResource(R.string.text_password),
                            isError = passwordError != null
                        )
                        TextError(textRes = passwordError)

                        val confirmPasswordField by viewModel.confirmPasswordField.collectAsState()
                        val (confirmPassword, confirmPasswordError) = confirmPasswordField

                        OutlinedTextFieldPassword(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            value = confirmPassword,
                            onValueChange = viewModel::changeConfirmPassword,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            maxLines = 1,
                            singleLine = true,
                            label = stringResource(R.string.text_confirm_password),
                            isError = confirmPasswordError != null
                        )
                        TextError(textRes = confirmPasswordError)

                        val isFulfilled by viewModel.fulfilled.collectAsState()

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            onClick = viewModel::save,
                            enabled = isFulfilled && !isLoading
                        ) {
                            Text(text = stringResource(R.string.text_save))
                        }
                    }
                }
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

