package trashissue.rebage.domain.usecase

import androidx.annotation.StringRes
import androidx.core.util.PatternsCompat

class ValidateEmailUseCase(
    /**
     * [Int] reference to string resource.
     * You can use string resource to inject these values
     * */
    @StringRes private val errorBlankEmail: Int,
    @StringRes private val errorIncorrectMessage: Int
) {

    operator fun invoke(email: String): Int? {
        if (email.isBlank()) return errorBlankEmail

        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) return errorIncorrectMessage

        return null
    }
}
