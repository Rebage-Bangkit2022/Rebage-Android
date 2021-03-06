package trashissue.rebage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import trashissue.rebage.R
import trashissue.rebage.domain.repository.*
import trashissue.rebage.domain.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideValidateNameUseCase(): ValidateNameUseCase {
        return ValidateNameUseCase(
            errorBlankMessage = R.string.error_name_is_required,
            errorMinMessage = R.string.error_name_min_char,
            errorMaxMessage = R.string.error_name_max_char
        )
    }

    @Provides
    @ViewModelScoped
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase(
            errorBlankMessage = R.string.error_password_is_required,
            errorMinMessage = R.string.error_password_min_char,
            errorMaxMessage = R.string.error_password_max_char,
            errorNotMatch = R.string.error_password_not_match
        )
    }

    @Provides
    @ViewModelScoped
    fun provideValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase(
            errorBlankEmail = R.string.error_email_is_required,
            errorIncorrectMessage = R.string.error_invalid_email
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(userRepository: UserRepository): SignInUseCase {
        return SignInUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(userRepository: UserRepository): SignUpUseCase {
        return SignUpUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideEditUserUseCase(userRepository: UserRepository): EditUserUseCase {
        return EditUserUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignOutUseCase(userRepository: UserRepository): SignOutUseCase {
        return SignOutUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAuthGoogleUseCase(userRepository: UserRepository): AuthGoogleUseCase {
        return AuthGoogleUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideOnboardingUseCase(userRepository: UserRepository): OnboardingUseCase {
        return OnboardingUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDarkThemeUseCase(userRepository: UserRepository): DarkThemeUseCase {
        return DarkThemeUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDetectGarbageUseCase(
        userRepository: UserRepository,
        detectionRepository: DetectionRepository
    ): DetectGarbageUseCase {
        return DetectGarbageUseCase(userRepository, detectionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveDetectionUseCase(
        userRepository: UserRepository,
        detectionRepository: DetectionRepository
    ): SaveDetectionUseCase {
        return SaveDetectionUseCase(userRepository, detectionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDetectionUseCase(
        userRepository: UserRepository,
        detectionRepository: DetectionRepository
    ): GetDetectionUseCase {
        return GetDetectionUseCase(userRepository, detectionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDetectionsStatisticUseCase(
        userRepository: UserRepository,
        detectionRepository: DetectionRepository
    ): GetDetectionsStatisticUseCase {
        return GetDetectionsStatisticUseCase(userRepository, detectionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateDetectionUseCase(
        userRepository: UserRepository,
        detectionRepository: DetectionRepository
    ): UpdateDetectionUseCase {
        return UpdateDetectionUseCase(userRepository, detectionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteDetectionUseCase(
        userRepository: UserRepository,
        detectionRepository: DetectionRepository
    ): DeleteDetectionUseCase {
        return DeleteDetectionUseCase(userRepository, detectionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetArticlesUseCase(articleRepository: ArticleRepository): GetArticlesUseCase {
        return GetArticlesUseCase(articleRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetArticleUseCase(
        userRepository: UserRepository,
        articleRepository: ArticleRepository
    ): GetArticleUseCase {
        return GetArticleUseCase(userRepository, articleRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetFavoriteArticleUseCase(
        userRepository: UserRepository,
        articleRepository: ArticleRepository
    ): GetFavoriteArticlesUseCase {
        return GetFavoriteArticlesUseCase(userRepository, articleRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideToggleLikeArticleUseCase(
        userRepository: UserRepository,
        articleRepository: ArticleRepository
    ): ToggleLikeArticleUseCase {
        return ToggleLikeArticleUseCase(userRepository, articleRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetGarbageUseCase(garbageRepository: GarbageRepository): GetGarbageUseCase {
        return GetGarbageUseCase(garbageRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetGarbageBankUseCase(googleMapsRepository: GoogleMapsRepository): GetGarbageBankUseCase {
        return GetGarbageBankUseCase(googleMapsRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetPlaceUseCase(googleMapsRepository: GoogleMapsRepository): GetPlaceUseCase {
        return GetPlaceUseCase(googleMapsRepository)
    }
}
