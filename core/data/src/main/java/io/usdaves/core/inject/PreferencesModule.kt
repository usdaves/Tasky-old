package io.usdaves.core.inject

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.usdaves.core.preferences.AuthPreferences
import io.usdaves.core.preferences.DatastoreAuthPreferences
import io.usdaves.core.preferences.DatastoreProfilePreferences
import io.usdaves.core.preferences.DatastoreThemePreferences
import io.usdaves.core.preferences.ProfilePreferences
import io.usdaves.core.preferences.ThemePreferences
import io.usdaves.logger.Logger


private const val DATASTORE_PREFERENCES_NAME = "io.usdaves.tasky.preferences"

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

  private val Context.dataStore by preferencesDataStore(DATASTORE_PREFERENCES_NAME)

  @Provides
  fun provideProfilePreferences(
    @ApplicationContext context: Context,
    logger: Logger,
  ): ProfilePreferences = DatastoreProfilePreferences(context.dataStore, logger)

  @Provides
  fun provideAuthPreferences(
    @ApplicationContext context: Context,
    logger: Logger,
  ): AuthPreferences = DatastoreAuthPreferences(context.dataStore, logger)

  @Provides
  fun provideThemePreferences(
    @ApplicationContext context: Context,
    logger: Logger,
  ): ThemePreferences = DatastoreThemePreferences(context.dataStore, logger)

}
