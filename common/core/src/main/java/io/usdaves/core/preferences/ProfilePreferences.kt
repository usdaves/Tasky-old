package io.usdaves.core.preferences

import kotlinx.coroutines.flow.Flow

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

interface ProfilePreferences {

  val profile: Flow<Profile>

  suspend fun setProfile(profile: Profile)

  data class Profile(
    val displayName: String?,
    val email: String?,
  )
}
