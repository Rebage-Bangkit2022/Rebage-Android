package trashissue.rebage.presentation.signin

import androidx.annotation.StringRes

data class FormState(
    val email: String = "",
    @StringRes val emailErrorMessage: Int? = null,
    val password: String = "",
    @StringRes val passwordErrorMessage: Int? = null,
    val confirmPassword: String = "",
    @StringRes val confirmPasswordErrorMessage: Int? = null
) {
    val isNotError
        get() = emailErrorMessage == null && confirmPasswordErrorMessage == null

    val isNotBlank
        get() = email.isNotBlank() && password.isNotBlank()
}
