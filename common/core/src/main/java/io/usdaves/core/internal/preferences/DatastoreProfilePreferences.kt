package io.usdaves.core.internal.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.usdaves.core.preferences.ProfilePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

internal class DatastoreProfilePreferences(
  private val dataStore: DataStore<Preferences>,
) : ProfilePreferences {

  override val profile: Flow<ProfilePreferences.Profile> = dataStore.data.map { preferences ->
    ProfilePreferences.Profile(
      displayName = preferences[DISPLAY_NAME_KEY],
      email = preferences[EMAIL_KEY],
    )
  }

  override suspend fun setProfile(profile: ProfilePreferences.Profile) {
    dataStore.edit { preferences ->
      preferences[DISPLAY_NAME_KEY] = profile.displayName ?: "Unknown"
      preferences[EMAIL_KEY] = profile.email ?: "Unknown"
    }
  }

  private companion object {
    val DISPLAY_NAME_KEY = stringPreferencesKey("display_name_pref_key")
    val EMAIL_KEY = stringPreferencesKey("email_pref_key")
  }
}
