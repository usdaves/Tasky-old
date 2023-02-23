package io.usdaves.auth.remote

import com.google.firebase.auth.FirebaseAuth
import io.usdaves.core.Result
import io.usdaves.core.util.resultOf
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

// Created by usdaves(Usmon Abdurakhmanov) on 2/17/2023

internal class FirebaseAuthApi @Inject constructor(
  private val firebaseAuth: FirebaseAuth,
) : AuthApi {

  override suspend fun signIn(email: String, password: String): Result<String> = resultOf {
    val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
    authResult.user?.uid ?: error("The user ID couldn't be empty at this point")
  }

  override suspend fun signUp(email: String, password: String): Result<String> = resultOf {
    val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    authResult.user?.uid ?: error("The user ID couldn't be empty at this point")
  }
}
