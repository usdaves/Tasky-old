package io.usdaves.core.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import io.usdaves.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

internal class DatastoreOnboardingPreferences(
  private val dataStore: DataStore<Preferences>,
  logger: Logger,
) : OnboardingPreferences {

  init {
    // Just to monitor lifetime of the dependencies, because they're not singletons
    logger.i("DatastoreOnboardingPreferences instance created")
  }

  override val isOnboardingCompleted: Flow<Boolean> = dataStore.data.map { preference ->
    preference[ONBOARD_KEY] ?: false
  }

  override suspend fun setOnboardingCompleted(isOnboardingCompleted: Boolean) {
    dataStore.edit { preferences ->
      preferences[ONBOARD_KEY] = isOnboardingCompleted
    }
  }

  private companion object {
    val ONBOARD_KEY = booleanPreferencesKey("is_onboarding_completed_pref_key")
  }
}
