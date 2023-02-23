package io.usdaves.core.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import io.usdaves.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

internal class DatastoreAuthPreferences(
  private val dataStore: DataStore<Preferences>,
  logger: Logger,
) : AuthPreferences {

  init {
    // Just to monitor lifetime of the dependencies, because they're not singletons
    logger.i("DatastoreAuthPreferences instance created")
  }

  override val isAuthenticated: Flow<Boolean> = dataStore.data.map { preference ->
    preference[AUTH_KEY] ?: false
  }

  override suspend fun setAuthenticated(isAuthenticated: Boolean) {
    dataStore.edit { preferences ->
      preferences[AUTH_KEY] = isAuthenticated
    }
  }

  private companion object {
    val AUTH_KEY = booleanPreferencesKey("is_authenticated_pref_key")
  }
}
