package trashissue.rebage.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import trashissue.rebage.data.local.DetectionLocalDataSource
import trashissue.rebage.data.local.UserLocalDataSource
import trashissue.rebage.data.local.datastore.UserPreferences
import trashissue.rebage.data.local.room.DetectionDao
import trashissue.rebage.data.local.room.RebageDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    private val Context.dataStore by preferencesDataStore(name = "app_preferences")

    @Provides
    @Singleton
    fun providePreferencesManager(
        @ApplicationContext
        context: Context
    ): UserPreferences {
        return UserPreferences(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideUserLocalDataSource(userPreferences: UserPreferences): UserLocalDataSource {
        return UserLocalDataSource(userPreferences)
    }

    @Provides
    @Singleton
    fun provideRebageDatabase(
        @ApplicationContext
        context: Context
    ): RebageDatabase {
        return Room
            .databaseBuilder(context, RebageDatabase::class.java, "rebage.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDetectionDao(rebageDatabase: RebageDatabase): DetectionDao {
        return rebageDatabase.getDetectionDao()
    }

    @Provides
    @Singleton
    fun provideDetectionLocalDataSource(detectionDao: DetectionDao): DetectionLocalDataSource {
        return DetectionLocalDataSource(detectionDao)
    }
}
