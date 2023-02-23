package io.usdaves.core.remote

import com.google.firebase.firestore.FirebaseFirestore
import io.usdaves.logger.Logger
import kotlinx.coroutines.tasks.await

// Created by usdaves(Usmon Abdurakhmanov) on 2/18/2023

internal class FirebaseProfileApi(
  firebaseFirestore: FirebaseFirestore,
  logger: Logger,
) : ProfileApi {

  init {
    // Just to monitor lifetime of the dependencies
    logger.i("FirebaseProfileApi instance created")
  }

  private val profilesCollection = firebaseFirestore.collection("user_profiles")

  override suspend fun fetchUserProfileById(userId: String): UserProfile {
    val profileDocument = profilesCollection.document(userId).get().await()
    val profileData = profileDocument.data ?: emptyMap()
    return UserProfile(
      displayName = profileData["display_name"] as String?,
      email = profileData["email"] as String?,
    )
  }

  override suspend fun setUserProfile(userId: String, displayName: String, email: String) {
    val profileDocument = profilesCollection.document(userId)
    val profileData = mapOf(
      "display_name" to displayName,
      "email" to email,
    )
    profileDocument.set(profileData).await()
  }
}
