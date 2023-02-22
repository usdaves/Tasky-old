package io.usdaves.core.internal.repository

import io.usdaves.core.Result
import io.usdaves.core.preferences.ProfilePreferences
import io.usdaves.core.remote.ProfileApi
import io.usdaves.core.repository.ProfileRepository
import io.usdaves.core.util.resultOf
import io.usdaves.logger.Logger
import javax.inject.Inject

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

internal class ProdProfileRepository @Inject constructor(
  private val profileApi: ProfileApi,
  private val profilePreferences: ProfilePreferences,
  private val logger: Logger,
) : ProfileRepository {

  init {
    // Just to monitor lifetime of the dependencies
    logger.i("ProdProfileRepository instance created")
  }

  override suspend fun fetchUserProfileById(userId: String): Result<Unit> = resultOf {
    val profileApiResult = profileApi.fetchUserProfileById(userId)
    val profile = ProfilePreferences.Profile(
      displayName = profileApiResult.displayName,
      email = profileApiResult.email,
    )
    profilePreferences.setProfile(profile)
    logger.d("Fetch user data task completed. User(displayName=\"${profile.displayName}\", email=\"${profile.email}\"")
  }

  override suspend fun setUserProfile(
    userId: String,
    displayName: String,
    email: String,
  ): Result<Unit> = resultOf {
    profileApi.setUserProfile(userId, displayName, email)
    val profile = ProfilePreferences.Profile(
      displayName = displayName,
      email = email,
    )
    profilePreferences.setProfile(profile)
    logger.d("Set user data task completed. User(displayName=\"${profile.displayName}\", email=\"${profile.email}\"")
  }
}
