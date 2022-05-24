package trashissue.rebage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import trashissue.rebage.R
import trashissue.rebage.data.DefaultAuthRepository
import trashissue.rebage.domain.usecase.SignInUseCase
import trashissue.rebage.domain.usecase.ValidateEmailUseCase
import trashissue.rebage.domain.usecase.ValidateNameUseCase
import trashissue.rebage.domain.usecase.ValidatePasswordUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideValidateNameUseCase(): ValidateNameUseCase {
        return ValidateNameUseCase(
            errorBlankMessage = R.string.error_name_max_char,
            errorMinMessage = R.string.error_name_min_char,
            errorMaxMessage = R.string.error_name_max_char
        )
    }

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase(
            errorBlankMessage = R.string.error_password_max_char,
            errorMinMessage = R.string.error_password_min_char,
            errorMaxMessage = R.string.error_password_max_char
        )
    }

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase(
            errorIncorrectMessage = R.string.error_invalid_email
        )
    }

    @Provides
    @Singleton
    fun provideSignInUseCase(): SignInUseCase {
        return SignInUseCase(DefaultAuthRepository())
    }
}
