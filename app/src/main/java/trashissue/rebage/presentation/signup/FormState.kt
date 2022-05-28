package trashissue.rebage.presentation.signup

import androidx.annotation.StringRes

data class FormState(
    val name: String = "",
    @StringRes val nameErrorMessage: Int? = null,
    val email: String = "",
    @StringRes val emailErrorMessage: Int? = null,
    val password: String = "",
    @StringRes val passwordErrorMessage: Int? = null,
    val confirmPassword: String = "",
    @StringRes val confirmPasswordErrorMessage: Int? = null
) {
    val isNotError: Boolean
        get() = nameErrorMessage == null && emailErrorMessage == null && confirmPasswordErrorMessage == null
    val isNotBlank: Boolean
        get() = name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
}
