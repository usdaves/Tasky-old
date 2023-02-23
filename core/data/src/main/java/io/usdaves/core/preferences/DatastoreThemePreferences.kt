package io.usdaves.core.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import io.usdaves.core.preferences.ThemePreferences.Theme
import io.usdaves.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

internal class DatastoreThemePreferences(
  private val dataStore: DataStore<Preferences>,
  logger: Logger,
) : ThemePreferences {

  init {
    // Just to monitor lifetime of the dependencies, because they're not singletons
    logger.i("DatastoreThemePreferences instance created")
  }

  override val themeFlow: Flow<Theme> = dataStore.data.map { preferences ->
    val themeIndex = preferences[THEME_KEY] ?: Theme.SYSTEM.ordinal
    Theme.values()[themeIndex]
  }

  override suspend fun setTheme(theme: Theme) {
    dataStore.edit { preferences ->
      preferences[THEME_KEY] = theme.ordinal
    }
  }

  private companion object {
    val THEME_KEY = intPreferencesKey("theme_pref_key")
  }
}
