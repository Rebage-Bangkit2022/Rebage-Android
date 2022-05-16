package trashissue.rebage.domain.usecase

import androidx.annotation.StringRes
import androidx.core.util.PatternsCompat

class ValidateEmailUseCase(
    /**
     * [Int] reference to string resource.
     * You can use string resource to inject these values
     * */
    @StringRes private val errorIncorrectMessage: Int
) {

    operator fun invoke(value: String): Int? {
        if (!PatternsCompat.EMAIL_ADDRESS.matcher(value).matches()) return errorIncorrectMessage

        return null
    }
}