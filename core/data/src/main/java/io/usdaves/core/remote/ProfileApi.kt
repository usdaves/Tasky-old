package io.usdaves.core.remote

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

internal interface ProfileApi {

  suspend fun fetchUserProfileById(userId: String): UserProfile

  suspend fun setUserProfile(userId: String, displayName: String, email: String)
}
