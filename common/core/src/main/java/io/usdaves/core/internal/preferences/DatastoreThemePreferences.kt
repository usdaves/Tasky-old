package io.usdaves.core.internal.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import io.usdaves.core.preferences.ThemePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

internal class DatastoreThemePreferences(
  private val dataStore: DataStore<Preferences>,
) : ThemePreferences {

  override val themeFlow: Flow<ThemePreferences.Theme> = dataStore.data.map { preferences ->
    val themeIndex = preferences[THEME_KEY] ?: ThemePreferences.Theme.SYSTEM.ordinal
    ThemePreferences.Theme.values()[themeIndex]
  }

  override suspend fun setTheme(theme: ThemePreferences.Theme) {
    dataStore.edit { preferences ->
      preferences[THEME_KEY] = theme.ordinal
    }
  }

  private companion object {
    val THEME_KEY = intPreferencesKey("theme_pref_key")
  }
}
