package io.usdaves.core.repository

import io.usdaves.core.Result

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

interface ProfileRepository {

  suspend fun fetchUserProfileById(userId: String): Result<Unit>

  suspend fun setUserProfile(userId: String, displayName: String, email: String): Result<Unit>

}
