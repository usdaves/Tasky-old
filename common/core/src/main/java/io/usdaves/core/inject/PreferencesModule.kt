package io.usdaves.core.inject

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.usdaves.core.internal.preferences.DatastoreAuthPreferences
import io.usdaves.core.internal.preferences.DatastoreProfilePreferences
import io.usdaves.core.internal.preferences.DatastoreThemePreferences
import io.usdaves.core.preferences.AuthPreferences
import io.usdaves.core.preferences.ProfilePreferences
import io.usdaves.core.preferences.ThemePreferences


private const val DATASTORE_PREFERENCES_NAME = "io.usdaves.tasky.preferences"

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

  private val Context.dataStore by preferencesDataStore(DATASTORE_PREFERENCES_NAME)

  @Provides
  fun provideProfilePreferences(
    @ApplicationContext context: Context,
  ): ProfilePreferences = DatastoreProfilePreferences(context.dataStore)

  @Provides
  fun provideAuthPreferences(
    @ApplicationContext context: Context,
  ): AuthPreferences = DatastoreAuthPreferences(context.dataStore)

  @Provides
  fun provideThemePreferences(
    @ApplicationContext context: Context,
  ): ThemePreferences = DatastoreThemePreferences(context.dataStore)

}
