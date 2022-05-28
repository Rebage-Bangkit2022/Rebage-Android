package trashissue.rebage.domain.usecase

import androidx.annotation.StringRes

class ValidatePasswordUseCase(
    /**
     * [Int] reference to string resource.
     * You can use string resource to inject these values
     * */
    @StringRes private val errorBlankMessage: Int,
    @StringRes private val errorMinMessage: Int,
    @StringRes private val errorMaxMessage: Int,
    @StringRes private val errorNotMatch: Int
) {

    operator fun invoke(password: String): Int? {
        if (password.isBlank()) return errorBlankMessage

        if (password.length < 6) return errorMinMessage

        if (password.length > 50) return errorMaxMessage

        return null
    }

    operator fun invoke(password: String, confirm: String): Int? {
        if (password != confirm) return errorNotMatch
        return null
    }
}
