package trashissue.rebage.presentation.common

import androidx.annotation.StringRes

data class FieldState(
    val value: String = "",
    @StringRes val errorMessage: Int? = null
)

val FieldState.isError
    get() = errorMessage != null
