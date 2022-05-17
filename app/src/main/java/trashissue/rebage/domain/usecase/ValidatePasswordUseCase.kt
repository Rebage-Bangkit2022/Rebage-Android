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
) {

    operator fun invoke(value: String): Int? {
        if (value.isBlank()) return errorBlankMessage

        if (value.length < 6) return errorMinMessage

        if (value.length > 50) return errorMaxMessage

        return null
    }
}