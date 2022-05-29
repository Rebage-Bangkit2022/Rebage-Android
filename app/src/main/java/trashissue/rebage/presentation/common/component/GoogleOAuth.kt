package trashissue.rebage.presentation.common.component

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import trashissue.rebage.R

@Composable
fun GoogleOauth(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier.size(64.dp),
        onClick = onClick
    ) {
        Image(
            painter = painterResource(R.drawable.ic_google),
            contentDescription = stringResource(R.string.cd_google_sign_in),
            modifier = Modifier.size(32.dp),
        )
    }
}

@Composable
fun rememberGoogleAuthLauncher(
    onResult: (GoogleSignInAccount) -> Unit,
    onError: (Exception?) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            onError(null)
            return@rememberLauncherForActivityResult
        }

        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            onResult(account)
        } catch (e: ApiException) {
            onError(e)
        }
    }
}

@Composable
fun rememberGoogleSignInClient(
    clientId: String = "1011653863710-q5aidgb3k8s6pnnk3pmavkpmmpa2r9no.apps.googleusercontent.com"
): GoogleSignInClient {
    val context = LocalContext.current

    return remember {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()

        GoogleSignIn.getClient(context, gso)
    }
}
